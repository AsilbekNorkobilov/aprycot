package org.example.figma.mappers;

import org.example.figma.entity.Product;
import org.example.figma.model.dto.response.ProductResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResDto toDto(Product product);
}
