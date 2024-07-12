package org.example.figma.model.dto.request;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.OrderProduct}
 */
@Value
public class OrderProductReqDTO implements Serializable {
    Integer amount;
    UUID productId;
}