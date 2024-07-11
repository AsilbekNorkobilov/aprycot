package org.example.figma.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrendingOrderDto {
    private UUID id;
    private String name;
    private Integer calories;
    private Long ordersCount;
    private Double price;
    private byte[] photo;

    public String getBase64Photo() {
        return Base64.getEncoder().encodeToString(this.photo);
    }
}
