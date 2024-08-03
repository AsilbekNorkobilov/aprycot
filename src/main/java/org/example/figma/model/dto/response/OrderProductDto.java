package org.example.figma.model.dto.response;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.OrderProduct}
 */
@Value
public class OrderProductDto implements Serializable {
    Integer amount;
    String productName;
    Double productPrice;
    Integer productCalorie;
}