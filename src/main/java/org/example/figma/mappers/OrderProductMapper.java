package org.example.figma.mappers;

import org.example.figma.entity.OrderProduct;
import org.example.figma.model.dto.request.OrderProductReqDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderProductMapper {
    @Mapping(source = "productId", target = "product.id")
    OrderProduct toEntity(OrderProductReqDTO orderProductReqDTO);

    @Mapping(source = "product.id", target = "productId")
    OrderProductReqDTO toDto(OrderProduct orderProduct);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productId", target = "product.id")
    OrderProduct partialUpdate(OrderProductReqDTO orderProductReqDTO, @MappingTarget OrderProduct orderProduct);
}