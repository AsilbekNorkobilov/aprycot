package org.example.figma.service;

import lombok.RequiredArgsConstructor;
import org.example.figma.dto.LoginDto;
import org.example.figma.dto.RegisterDto;
import org.example.figma.entity.Role;
import org.example.figma.entity.User;
import org.example.figma.entity.enums.RoleName;
import org.example.figma.mappers.UserLoginMapper;
import org.example.figma.mappers.UserRegisterMapper;
import org.example.figma.repo.RoleRepository;
import org.example.figma.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRegisterMapper userRegisterMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserLoginMapper userLoginMapper;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterDto registerDto) {
        User user = userRegisterMapper.toEntity(registerDto);
        Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER.name());
        user.setRoles(List.of(roleUser));
        userRepository.save(user);
        return user.getEmail();
    }

    public String login(LoginDto loginDto) {
        User user = userLoginMapper.toEntity(loginDto);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password", e);
        }
        return user.getEmail();
    }
}
