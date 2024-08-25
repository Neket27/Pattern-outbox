package jgr.orderservice.mapper;

import jgr.orderservice.model.dto.CreateOrderDto;
import jgr.orderservice.model.dto.OrderDto;
import jgr.orderservice.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(CreateOrderDto orderDto);

    OrderDto toDto(Order order);
}
