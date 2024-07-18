package org.example.figma.repo;

import org.example.figma.entity.Order;
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
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByUserId() {
        UUID id=UUID.randomUUID();
        User user=User.builder().id(id).build();
        userRepository.save(user);
        Order order=Order.builder().user(user).build();
        orderRepository.save(order);
        List<Order> allByUserId = orderRepository.findAllByUserId(id);
        Assertions.assertEquals(id,allByUserId.get(0).getUser().getId());
    }
}