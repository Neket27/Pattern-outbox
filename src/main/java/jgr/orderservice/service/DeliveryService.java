package jgr.orderservice.service;

import jgr.orderservice.client.DeliveryServiceClient;
import jgr.orderservice.model.dto.CreateDeliveryDto;
import jgr.orderservice.model.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryServiceClient deliveryServiceClient;

    public Boolean processDelivery(UUID orderId, String deliveryAddress) {
        CreateDeliveryDto deliveryRequest = buildRequest(orderId, deliveryAddress);
        boolean result = false;
        try {
            DeliveryDto response = deliveryServiceClient.createDelivery(deliveryRequest);

            if ("success".equals(response.getStatus())) {
                log.info("Delivery successfully created with ID: {}", response.getDeliveryId());
                result = true;
            } else {
                log.error("Failed to create delivery. Status: {}", response.getStatus());
            }
        } catch (Exception e) {
            log.error("Error occurred while creating delivery", e);
        }
        return result;
    }

    private CreateDeliveryDto buildRequest(UUID orderId, String deliveryAddress) {
        CreateDeliveryDto createDeliveryDto = new CreateDeliveryDto();
        createDeliveryDto.setOrderId(orderId);
        createDeliveryDto.setDeliveryAddress(deliveryAddress);
        return createDeliveryDto;
    }
}
