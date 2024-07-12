package org.example.figma.mappers;

import org.example.figma.entity.OrderProduct;
import org.example.figma.model.dto.request.OrderProductDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderProductMapper {
    @Mapping(source = "productId", target = "product.id")
    OrderProduct toEntity(OrderProductDTO orderProductDTO);

    @Mapping(source = "product.id", target = "productId")
    OrderProductDTO toDto(OrderProduct orderProduct);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productId", target = "product.id")
    OrderProduct partialUpdate(OrderProductDTO orderProductDTO, @MappingTarget OrderProduct orderProduct);
}