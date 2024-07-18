package org.example.figma.service.impl;

import org.example.figma.config.AuditorAwareImpl;
import org.example.figma.entity.Order;
import org.example.figma.entity.OrderProduct;
import org.example.figma.mappers.OrderProductMapper;
import org.example.figma.model.dto.request.AddressReqDTO;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.example.figma.repo.AddressRepository;
import org.example.figma.repo.OrderProductRepository;
import org.example.figma.repo.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private AuditorAwareImpl auditorAware;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private OrderProductMapper orderProductMapper;
    private OrderProductRepository orderProductRepository;
    private OrderServiceImpl orderService;

    @BeforeEach
    public void before(){
        auditorAware= new AuditorAwareImpl();
        addressRepository=Mockito.mock(AddressRepository.class);
        orderRepository=Mockito.mock(OrderRepository.class);
        orderProductMapper=Mockito.mock(OrderProductMapper.class);
        orderProductRepository=Mockito.mock(OrderProductRepository.class);
        orderService=new OrderServiceImpl(auditorAware,addressRepository,orderRepository,orderProductMapper,orderProductRepository);
    }

    @Test
    void getAllOrders() {
        UUID uuid = UUID.randomUUID();
        List<Order> orders=new ArrayList<>(List.of(Order.builder().id(uuid).build()));
        Mockito.when(orderRepository.findAllByUserId(UUID.randomUUID())).thenReturn(new ArrayList<>(List.of(Order.builder().id(uuid).build())));
        ResponseEntity<List<Order>> temp = orderService.getAllOrders();
        List<Order> body = temp.getBody();
        Assertions.assertEquals(orders.get(0).getId(),body.get(0).getId());
    }

    @Test
    void save() {
        UUID id=UUID.randomUUID();
        UUID orderId=UUID.randomUUID();
        Order order=Order.builder().id(orderId).build();
        OrderProductReqDTO req=new OrderProductReqDTO(10,id);
        OrderProduct orderProduct=new OrderProduct();
        List<OrderProductReqDTO> list=new ArrayList<>(List.of(req));
        Mockito.when(orderRepository.save(order)).thenReturn(Order.builder().id(orderId).build());
        ResponseEntity<Order> save = orderService.save(list, new AddressReqDTO(UUID.randomUUID()));
        Order body = save.getBody();
        Assertions.assertEquals(orderId,body.getId());
    }
}