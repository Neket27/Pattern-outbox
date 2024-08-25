package jgr.orderservice.client;

import jgr.orderservice.model.dto.CreateDeliveryDto;
import jgr.orderservice.model.dto.DeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Интеграция с сервисом Доставки по REST
 */
@FeignClient(name = "delivery-service", url = "${integration.deliveryService.url}")
public interface DeliveryServiceClient {
    @PostMapping("/deliveries")
    DeliveryDto createDelivery(@RequestBody CreateDeliveryDto deliveryRequest);
}
