package org.example.figma.repo;

import org.example.figma.entity.Role;
import org.example.figma.entity.enums.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByRoleName() {
        Role role=new Role(1,RoleName.ROLE_ADMIN);
        roleRepository.save(role);
        Role found = roleRepository.findByRoleName(RoleName.ROLE_ADMIN.name());
        Assertions.assertEquals(1,found.getId());
    }
}