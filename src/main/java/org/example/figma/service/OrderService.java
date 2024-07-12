package org.example.figma.service;

import org.example.figma.entity.Order;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface OrderService {

    ResponseEntity<Order> save(List<OrderProductReqDTO> orderProductReqDTOList);

    ResponseEntity<List<Order>> getAllOrders();
}
