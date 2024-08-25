package jgr.orderservice.service;

import jakarta.transaction.Transactional;
import jgr.orderservice.mapper.OrderMapper;
import jgr.orderservice.model.dto.CreateOrderDto;
import jgr.orderservice.model.dto.OrderDto;
import jgr.orderservice.model.enums.RetryableTaskType;
import jgr.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RetryableTaskService retryableTaskService;

    @Transactional
    public OrderDto createOrder(CreateOrderDto dto) {
        var entity = orderMapper.toEntity(dto);
        var order = orderRepository.save(entity);

        retryableTaskService.createRetryableTask(order, RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST);
        retryableTaskService.createRetryableTask(order, RetryableTaskType.SEND_CREATE_NOTIFICATION_REQUEST);
        return orderMapper.toDto(order);
    }
}
