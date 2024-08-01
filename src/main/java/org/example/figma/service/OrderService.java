package org.example.figma.service;

import org.example.figma.entity.Order;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


public interface OrderService {

    ResponseEntity<?> save(List<OrderProductReqDTO> orderProductReqDTOList, UUID addressReqDTO);

    ResponseEntity<List<Order>> getAllOrders();
}
