package org.example.figma.service;

import org.example.figma.entity.Order;
import org.example.figma.model.dto.request.OrderProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface OrderService {

    ResponseEntity<Order> save(List<OrderProductDTO> orderProductDTOList);

    ResponseEntity<List<Order>> getAllOrders();
}
