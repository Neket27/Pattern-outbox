package jgr.orderservice.service;

import jgr.orderservice.client.NotificationServiceClient;
import jgr.orderservice.model.dto.CreateNotificationDto;
import jgr.orderservice.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationServiceClient notificationServiceClient;

    public Boolean sendNotification(UUID orderId, String userEmail) {
        boolean result = false;

        CreateNotificationDto notificationRequest = buildRequest(orderId, userEmail);
        try {
            NotificationDto response = notificationServiceClient.createNotification(notificationRequest);

            if ("success".equals(response.getStatus())) {
                log.info("Notification successfully created with ID: {}", response.getNotificationId());
                result = true;
            } else {
                log.error("Failed to create notification. Status: {}", response.getStatus());
            }
        } catch (Exception e) {
            log.error("Error occurred while creating notification", e);
        }
        return result;
    }

    private CreateNotificationDto buildRequest(UUID orderId, String userEmail){
        CreateNotificationDto notificationRequest = new CreateNotificationDto();
        notificationRequest.setOrderId(orderId);
        notificationRequest.setUserEmail(userEmail);
        return notificationRequest;
    }
}
