package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.figma.dto.LoginDto;
import org.example.figma.dto.RegisterDto;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Role;
import org.example.figma.entity.User;
import org.example.figma.entity.enums.RoleName;
import org.example.figma.mappers.UserLoginMapper;
import org.example.figma.mappers.UserRegisterMapper;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.RoleRepository;
import org.example.figma.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRegisterMapper userRegisterMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserLoginMapper userLoginMapper;
    private final AuthenticationManager authenticationManager;
    private final AttachmentRepository attachmentRepository;

    public String register(RegisterDto registerDto) {
        User user = userRegisterMapper.toEntity(registerDto);
        Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER.name());
        user.setRoles(List.of(roleUser));
        setDefaultPhotoToUser(user);
        userRepository.save(user);
        return user.getEmail();
    }

    @SneakyThrows
    private void setDefaultPhotoToUser(User user) {
        File file =new File("photo/default_user.png");
        byte[] photo = Files.readAllBytes(file.toPath());
        Attachment attachment = Attachment.builder()
                .fullImage(photo)
                .build();
        attachment.compressImage();
        attachmentRepository.save(attachment);
        user.setAttachment(attachment);
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
