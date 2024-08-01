package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.config.AuditorAwareImpl;
import org.example.figma.entity.Address;
import org.example.figma.entity.Order;
import org.example.figma.entity.OrderProduct;
import org.example.figma.entity.Product;
import org.example.figma.entity.enums.OrderStatus;
import org.example.figma.mappers.OrderProductMapper;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.example.figma.repo.AddressRepository;
import org.example.figma.repo.OrderProductRepository;
import org.example.figma.repo.OrderRepository;
import org.example.figma.repo.ProductRepository;
import org.example.figma.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AuditorAwareImpl auditorAware;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderProductMapper orderProductMapper;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> userOrders = orderRepository.findAllByUserId(auditorAware.getAuthenticatedUser().getId());
        return ResponseEntity.status(200).body(userOrders);
    }

    @Override
    public ResponseEntity<?> save(List<OrderProductReqDTO> orderProductReqDTOList, UUID addressId) {
        Order order=new Order();
        order.setUser(auditorAware.getAuthenticatedUser());
        Address address = addressRepository.findById(addressId).get();
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.OPEN);
        orderRepository.save(order);
        for (OrderProductReqDTO orderProductReqDTO : orderProductReqDTOList) {
            Product product = productRepository.findById(orderProductReqDTO.getProductId()).orElseThrow(()->{
                throw new RuntimeException();
            });
            OrderProduct orderProduct=OrderProduct.builder()
                    .product(product)
                    .amount(orderProductReqDTO.getAmount())
                    .order(order)
                    .build();
            orderProductRepository.save(orderProduct);
        }
        return ResponseEntity.status(200).body(order.getId().toString());
    }
}
