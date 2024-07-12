package org.example.figma.mappers;

import org.example.figma.entity.User;
import org.example.figma.model.dto.response.UserResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResDto toDto(User user);
}
