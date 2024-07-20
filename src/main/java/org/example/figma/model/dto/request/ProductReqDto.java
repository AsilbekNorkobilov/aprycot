package org.example.figma.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDto {
    private String name;
    private Double price;
    private Integer calorie;
    private String categoryName;
}
