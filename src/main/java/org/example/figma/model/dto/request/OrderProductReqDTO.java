package org.example.figma.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.OrderProduct}
 */
@Value
@Data
@AllArgsConstructor
public class OrderProductReqDTO implements Serializable {
    Integer amount;
    UUID productId;
}