package org.example.figma.config;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Role;
import org.example.figma.entity.User;
import org.example.figma.entity.enums.RoleName;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.RoleRepository;
import org.example.figma.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        generateAdmin();
    }

    private void generateAdmin() throws IOException {
        Role roleAdmin = new Role(1, RoleName.ROLE_ADMIN);
        Role roleManager = new Role(2, RoleName.ROLE_MANAGER);
        Role roleUser = new Role(3, RoleName.ROLE_USER);
        roleRepository.saveAll(List.of(roleAdmin,roleManager,roleUser));

        File file =new File("photo/mafia.jpg");
        byte[] photo = Files.readAllBytes(file.toPath());
        Attachment attachment = Attachment.builder()
                .fullImage(photo)
                .build();
        attachmentRepository.save(attachment);

        User admin = User.builder()
                .firstName("Baxtiyor")
                .lastName("Sadulloyev")
                .phone("97 864 44 00")
                .email("baxti@gmail.com")
                .password(passwordEncoder.encode("root123"))
                .attachment(attachment)
                .roles(List.of(roleAdmin,roleManager))
                .build();
        userRepository.save(admin);

    }
}
