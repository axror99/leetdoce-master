package com.example.leetdoce.service;

import com.example.leetdoce.config.JwtService;
import com.example.leetdoce.convertor.to.ConvertRequestToUser;
import com.example.leetdoce.dto.request.UserLogin;
import com.example.leetdoce.dto.request.UserRegister;
import com.example.leetdoce.dto.request.UserUpdate;
import com.example.leetdoce.dto.response.ReturnUserWithToken;
import com.example.leetdoce.dto.response.UserRatingResponse;
import com.example.leetdoce.entity.UserEntity;
import com.example.leetdoce.entity.UserQuestionSource;
import com.example.leetdoce.exception.UserAlreadyExistException;
import com.example.leetdoce.exception.NotFoundException;
import com.example.leetdoce.repository.UserRepository;
import com.example.leetdoce.simple_class.LevelQuestion;
import com.example.leetdoce.simple_class.QuestionState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MediaService mediaService;

    public ReturnUserWithToken registerUser(UserRegister userRegister) {
        Optional<UserDetails> userByUsername = userRepository.findByEmail(userRegister.getEmail());
        if (userByUsername.isPresent()) {
            throw new UserAlreadyExistException(MessageFormat.format("email = {0} already exist in database", userRegister.getEmail()));
        }
        UserEntity userEntity = ConvertRequestToUser.getInstance().fromUserRegister(userRegister);
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userEntity.setRoleEntities(Collections.singletonList(roleService.getUserRole()));
        UserEntity savedUser = userRepository.save(userEntity);
        String token = jwtService.generateToken(userEntity);
        ReturnUserWithToken returnUserWithToken = ConvertRequestToUser.getInstance().fromUserEntity(savedUser);
        returnUserWithToken.setToken(token);
        return returnUserWithToken;
    }

    public ReturnUserWithToken login(UserLogin userLogin) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(),
                        userLogin.getPassword()
                )
        );
        UserEntity passedUser = (UserEntity) authenticate.getPrincipal();
        String token = jwtService.generateToken(passedUser);
        ReturnUserWithToken user = ConvertRequestToUser.getInstance().fromUserEntity(passedUser);
        user.setToken(token);
        return user;
    }

    public ReturnUserWithToken getOneUserInfo(String token) {
        String email = jwtService.extractEmail(token.substring(7));
        UserEntity userEntity = (UserEntity) userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(MessageFormat.format("email={0} user not found with token in database", email)));
        ReturnUserWithToken user = ConvertRequestToUser.getInstance().fromUserEntity(userEntity);
        String newToken = jwtService.generateToken(userEntity);
        user.setToken(newToken);
        return user;
    }

    public void updateUser(int id, UserUpdate userUpdate) {
        UserEntity dbUser = userRepository.findById(id).orElseThrow(()->new NotFoundException(MessageFormat.format("id = {0} user was not found in DB", id)));
        if (dbUser.getId() == 0) {
            throw new NotFoundException(MessageFormat.format("id = {0} user was not found in DB", id));
        }
        if (userUpdate.getName() != null && !userUpdate.getName().equals("")) {
            dbUser.setName(userUpdate.getName());
        }
        if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals("")) {
            dbUser.setEmail(userUpdate.getEmail());
        }
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().equals("")) {
            dbUser.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        if (userUpdate.getPicture() != null) {
            String picName = mediaService.savePicture(userUpdate.getPicture());
            dbUser.setPicture(picName);
        }
        userRepository.save(dbUser);
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("id = {0} user was not found in DB", id)));
    }

    public void saveUser(UserEntity currentUser) {
        userRepository.save(currentUser);
    }

    public List<UserRatingResponse> getUsersRating(int page, int count) {
        Pageable pageable;
        if (page==1){
            pageable = PageRequest.of(0, count);
        }else {
           int index = (page-1)*count;
            pageable = PageRequest.of(index,count);
        }
        List<UserEntity> allUsers = userRepository.findAll();

        List<UserRatingResponse> ratingList = new LinkedList<>();
        for (UserEntity user : allUsers) {
            boolean hasAdminRole = user.getRoleEntities().stream()
                    .anyMatch(role -> role.getRole().equals("ADMIN"));
            if (!hasAdminRole) {
                int countHard = 0;
                int countMedium = 0;
                int countEasy = 0;
                for (UserQuestionSource source : user.getUserQuestionSourceList()) {
                    if (source.getPositionQuestion() == QuestionState.SUCCESS.getState()) {
                        if (source.getLevel().equals(LevelQuestion.HARD.getLevel())) {
                            countHard++;
                        } else if (source.getLevel().equals(LevelQuestion.MEDIUM.getLevel())) {
                            countMedium++;
                        } else if (source.getLevel().equals(LevelQuestion.EASY.getLevel())) {
                            countEasy++;
                        }
                    }
                }
                UserRatingResponse userRate = UserRatingResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .picture(user.getPicture())
                        .hard(countHard)
                        .medium(countMedium)
                        .easy(countEasy)
                        .build();
                ratingList.add(userRate);
            }
        }
        Collections.sort(ratingList, new Comparator<UserRatingResponse>() {
            @Override
            public int compare(UserRatingResponse r1, UserRatingResponse r2) {
                int hards1 = r1.getHard();
                int hards2 = r2.getHard();

                if (hards1 == hards2) {
                    int mediums1 = r1.getMedium();
                    int mediums2 = r2.getMedium();

                    if (mediums1 == mediums2) {
                        int easys1 = r1.getEasy();
                        int easys2 = r2.getEasy();

                        return Integer.compare(easys2, easys1);
                    }

                    return Integer.compare(mediums2, mediums1);
                }

                return Integer.compare(hards2, hards1);
            }
        });

        Page<UserRatingResponse> ratingPage = new PageImpl<>(ratingList, pageable, ratingList.size());
        return ratingPage.getContent();
    }
}
