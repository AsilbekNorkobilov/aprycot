package org.example.figma.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.OrderProduct}
 */
@Value
@Data
@AllArgsConstructor
@Builder
public class OrderProductDto implements Serializable {
    Integer amount;
    String productName;
    Double productPrice;
    Integer productCalorie;
}