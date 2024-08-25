package jgr.orderservice.mapper;

import jgr.orderservice.model.dto.CreateOrderDto;
import jgr.orderservice.model.dto.OrderDto;
import jgr.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, Instant.class})
public interface OrderMapper {
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "version", constant = "0")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Order toEntity(CreateOrderDto orderDto);

    @Mapping(source = "id", target = "uuid")
    OrderDto toDto(Order order);
}
