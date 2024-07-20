package org.example.figma.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReqDto {
    private String name;
    private Double price;
    private Integer calorie;
    private String categoryName;
}
