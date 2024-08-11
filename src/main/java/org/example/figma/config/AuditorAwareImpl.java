package org.example.figma.config;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl {
    private final UserRepository userRepository;
    @Bean
    public User getAuthenticatedUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email);
    }
}
