package com.example.leetdoce.service;

import com.example.leetdoce.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuditorImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !((authentication.getPrincipal()) instanceof UserEntity principal))
        {
            return Optional.empty();
        }
        return Optional.of(principal.getUsername());
    }
}
