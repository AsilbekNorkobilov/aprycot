package org.example.figma.mappers;


import org.example.figma.entity.Category;
import org.example.figma.model.dto.response.CategoryResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResDto toDto(Category category);

}
