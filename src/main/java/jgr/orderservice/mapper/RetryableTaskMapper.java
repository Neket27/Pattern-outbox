package jgr.orderservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jgr.orderservice.model.entity.Order;
import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.model.enums.RetryableTaskType;
import org.aspectj.weaver.ast.Or;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RetryableTaskMapper {
    @Mapping(source = "order", target = "payload", qualifiedByName = "convertObjectToJson")
    RetryableTask toRetryableTask(Order order, RetryableTaskType type);

    @Named("convertObjectToJson")
    default String convertObjectToJson(Order order){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Order to JSON", e);
        }
    }

    @Named("convertJsonToOrder")
    default Order convertJsonToOrder(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to Order", e);
        }
    }
}
