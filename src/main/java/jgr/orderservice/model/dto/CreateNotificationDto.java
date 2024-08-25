package jgr.orderservice.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateNotificationDto {
    private UUID orderId;
    private String userEmail;
}
