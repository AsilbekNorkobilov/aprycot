package org.example.figma.repo;

import org.example.figma.entity.Address;
import org.example.figma.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByUserId() {
        UUID id=UUID.randomUUID();
        User user= User.builder().id(id).build();
        userRepository.save(user);
        Address address=Address.builder().user(user).build();
        addressRepository.save(address);
        List<Address> temp = addressRepository.findAllByUserId(id);
        assertFalse(temp.isEmpty());
    }
}