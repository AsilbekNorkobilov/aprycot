package org.example.figma.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.Product}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDto {
    UUID id;
    String name;
    boolean isArchived;
    Double price;
    Integer calorie;
    String base64Photo;
    String categoryName;
}