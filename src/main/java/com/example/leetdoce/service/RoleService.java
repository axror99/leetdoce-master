package com.example.leetdoce.service;

import com.example.leetdoce.entity.RoleEntity;
import com.example.leetdoce.exception.RoleNotFoundException;
import com.example.leetdoce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getUserRole(){
        return roleRepository.findByRole("USER").orElseThrow(()->new RoleNotFoundException(" Not found USER role in database"));
    }
    public RoleEntity getAdminRole(){
        return roleRepository.findByRole("ADMIN").orElseThrow(()->new RoleNotFoundException(" Not found ADMIN role in database"));
    }
    public void saveUser_And_Admin(){
        try {
            getUserRole();
        }catch (RoleNotFoundException e){
            RoleEntity user =new RoleEntity();
            user.setRole("USER");
            roleRepository.save(user);
        }
        try {
            getAdminRole();
        }catch (RoleNotFoundException e){
            RoleEntity admin =new RoleEntity();
            admin.setRole("ADMIN");
            roleRepository.save(admin);
        }
    }
}
