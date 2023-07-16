package com.example.leetdoce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @JsonIgnore
    @NotBlank
    private String password;
    @Email
    @Column(unique = true)
    private String email;
    private LocalDate joinTime;
    private String picture;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RoleEntity> roleEntities;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserQuestionSource> userQuestionSourceList;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (roleEntities !=null && roleEntities.size()!=0 ){
            roleEntities.forEach((roleEntity)-> authorityList.add(new SimpleGrantedAuthority("ROLE_"+ roleEntity.getRole())));
        }else {
            authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
