package org.example.figma.repo;

import org.example.figma.entity.Role;
import org.example.figma.entity.User;
import org.example.figma.entity.enums.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findAllManagers() {
        Role role=new Role(1, RoleName.ROLE_MANAGER);
        roleRepository.save(role);
        User user=User.builder().roles(new ArrayList<>(List.of(role))).build();
        userRepository.save(user);
        List<User> allManagers = userRepository.findAllManagers();
        Assertions.assertFalse(allManagers.isEmpty());
    }

    @Test
    void findByEmail() {
        User user=User.builder().email("a").build();
        userRepository.save(user);
        User byEmail = userRepository.findByEmail(user.getEmail());
        Assertions.assertEquals(user.getEmail(),byEmail.getEmail());
    }
}