package org.example.figma.mappers;

import org.example.figma.dto.RegisterDto;
import org.example.figma.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRegisterMapper {
    User toEntity(RegisterDto registerDto);

    RegisterDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterDto registerDto, @MappingTarget User user);
}