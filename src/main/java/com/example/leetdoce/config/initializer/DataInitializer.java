package com.example.leetdoce.config.initializer;

import com.example.leetdoce.entity.RoleEntity;
import com.example.leetdoce.entity.UserEntity;
import com.example.leetdoce.repository.RoleRepository;
import com.example.leetdoce.repository.UserRepository;
import com.example.leetdoce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {


//        RoleEntity user = new RoleEntity();
//        user.setRole("USER");
//        roleRepository.save(roleService.getUserRole());
        roleService.saveUser_And_Admin();
//        RoleEntity roleEntity =new RoleEntity();
//        roleEntity.setRole("SUPER_ADMIN");

        UserEntity admin = new UserEntity();
        admin.setName("Axror");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setEmail("ahror060799@gmail.com");
        RoleEntity adminRole = roleService.getAdminRole();
        RoleEntity userRole = roleService.getUserRole();
//        admin.setRoleEntities(List.of(adminRole,userRole));
        admin.setJoinTime(LocalDate.now());
        if (userRepository.findByEmail("ahror060799@gmail.com").isPresent()) {
            System.out.println("xaxa");
        }else {
            UserEntity save = userRepository.save(admin);
            save.setRoleEntities(List.of(adminRole,userRole));
            userRepository.save(save);
        }
    }
}

