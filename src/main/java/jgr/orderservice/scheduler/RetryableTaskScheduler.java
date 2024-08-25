package jgr.orderservice.scheduler;

import jgr.orderservice.mapper.RetryableTaskMapper;
import jgr.orderservice.model.enums.RetryableTaskType;
import jgr.orderservice.service.DeliveryService;
import jgr.orderservice.service.NotificationService;
import jgr.orderservice.service.RetryableTaskService;
import jgr.orderservice.service.retryable.RetryableTaskProcessor;
import jgr.orderservice.service.retryable.SendCreateDeliveryRequestRetryableTaskProcessor;
import jgr.orderservice.service.retryable.SendCreateNotificationRequestRetryableTaskProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Планировщик для выполнения задач, требующих повторной обработки
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RetryableTaskScheduler {
    private final RetryableTaskService retryableTaskService;
    private final RetryableTaskMapper retryableTaskMapper;
    private final DeliveryService deliveryService;
    private final NotificationService notificationService;

    private final Map<RetryableTaskType, RetryableTaskProcessor> taskProcessors = Map.of(
            RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST, new SendCreateDeliveryRequestRetryableTaskProcessor(retryableTaskService,
                    deliveryService, retryableTaskMapper
            ),
            RetryableTaskType.SEND_CREATE_NOTIFICATION_REQUEST, new SendCreateNotificationRequestRetryableTaskProcessor(retryableTaskService,
                    notificationService, retryableTaskMapper));

    @Scheduled(fixedRate = 5000)
    public void executeRetryableTasks() {
        log.info("Starting retryable task processors");
        for (Map.Entry<RetryableTaskType, RetryableTaskProcessor> entry : taskProcessors.entrySet()) {
            var taskType = entry.getKey();
            var taskProcessor = entry.getValue();
            log.info("Processing tasks of type: {}", taskType);

            var retryableTasks = retryableTaskService.getRetryableTasksForProcessing(taskType);
            taskProcessor.processRetryableTasks(retryableTasks);
        }
        log.info("Completed all retryable task processing");
    }
}
