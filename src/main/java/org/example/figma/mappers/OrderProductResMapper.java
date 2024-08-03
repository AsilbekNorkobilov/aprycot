package org.example.figma.mappers;

import org.example.figma.entity.OrderProduct;
import org.example.figma.model.dto.response.OrderProductDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderProductResMapper {
    OrderProduct toEntity(OrderProductDto orderProductDto);

    OrderProductDto toDto(OrderProduct orderProduct);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderProduct partialUpdate(OrderProductDto orderProductDto, @MappingTarget OrderProduct orderProduct);
}