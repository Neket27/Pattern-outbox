package jgr.orderservice.client;

import jgr.orderservice.model.dto.CreateNotificationDto;
import jgr.orderservice.model.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Интеграция с сервисом Нотификации по REST
 */
@FeignClient(name = "notification-service", url = "${integration.notificationService.url}")
public interface NotificationServiceClient {
    @PostMapping("/notifications")
    NotificationDto createNotification(@RequestBody CreateNotificationDto dto);
}
