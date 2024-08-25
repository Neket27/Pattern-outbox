package jgr.orderservice.controller;

import jgr.orderservice.model.dto.CreateOrderDto;
import jgr.orderservice.model.dto.OrderDto;
import jgr.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public OrderDto createOrder(@RequestBody CreateOrderDto dto){
        return orderService.createOrder(dto);
    }
}
