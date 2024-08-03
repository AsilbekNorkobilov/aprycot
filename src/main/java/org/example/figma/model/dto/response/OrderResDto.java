package org.example.figma.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.example.figma.entity.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.Order}
 */
@Value
@Builder
@AllArgsConstructor
public class OrderResDto implements Serializable {
    UUID id;
    OrderStatus orderStatus;
    LocalDateTime orderTime;
    AddressDto addressDto;
    List<OrderProductDto> productDtoList;

}