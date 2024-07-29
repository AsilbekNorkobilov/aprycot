package org.example.figma.service;

import org.example.figma.entity.enums.OrderStatus;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.example.figma.model.dto.response.OrderResDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


public interface OrderService {

    ResponseEntity<UUID> save(List<OrderProductReqDTO> orderProductReqDTOList, UUID addressReqDTO);

    ResponseEntity<List<OrderResDto>> getAllOrders();

    String changeStatus(UUID id, OrderStatus orderStatus);
}
