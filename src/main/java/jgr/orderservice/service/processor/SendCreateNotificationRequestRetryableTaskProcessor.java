package jgr.orderservice.service.processor;

import jgr.orderservice.mapper.RetryableTaskMapper;
import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.service.NotificationService;
import jgr.orderservice.service.RetryableTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Процессор для обработки требующих повторной обработки задач, связанных с отправкой запросов на создание нотификации в сервис Уведомлений.
 * Отвечает за обработку задач, которые ранее не были успешно выполнены и требуют повторной попытки
 * для успешного создания заказов на доставку в сервисе Уведомлений.
 */
@Slf4j
@Service
public class SendCreateNotificationRequestRetryableTaskProcessor extends AbstractRetryableTaskProcessor {
    private final NotificationService notificationService;
    private final RetryableTaskMapper retryableTaskMapper;

    public SendCreateNotificationRequestRetryableTaskProcessor(RetryableTaskService retryableTaskService,
                                                               NotificationService notificationService,
                                                               RetryableTaskMapper retryableTaskMapper) {
        super(retryableTaskService);
        this.notificationService = notificationService;
        this.retryableTaskMapper = retryableTaskMapper;
    }

    @Override
    protected boolean processRetryableTask(RetryableTask retryableTask) {
        var order = retryableTaskMapper.convertJsonToOrder(retryableTask.getPayload());
        return notificationService.sendNotification(order.getId(), order.getCustomerEmail());
    }
}
