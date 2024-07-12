package org.example.figma.config;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.repo.UserRepository;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<User> {


    private final UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String email = authentication.getName();
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
