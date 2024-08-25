package jgr.orderservice.service.processor;

import jgr.orderservice.mapper.RetryableTaskMapper;
import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.service.DeliveryService;
import jgr.orderservice.service.RetryableTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Процессор для обработки требующих повторной обработки задач, связанных с отправкой запросов на создание доставки в сервис Доставки.
 * Отвечает за обработку задач, которые ранее не были успешно выполнены и требуют повторной попытки
 * для успешного создания заказов на доставку в сервисе Доставки.
 */
@Slf4j
@Service
public class SendCreateDeliveryRequestRetryableTaskProcessor extends AbstractRetryableTaskProcessor {
    private final DeliveryService deliveryService;
    private final RetryableTaskMapper retryableTaskMapper;

    public SendCreateDeliveryRequestRetryableTaskProcessor(RetryableTaskService retryableTaskService,
                                                           DeliveryService deliveryService,
                                                           RetryableTaskMapper retryableTaskMapper) {
        super(retryableTaskService);
        this.deliveryService = deliveryService;
        this.retryableTaskMapper = retryableTaskMapper;
    }

    @Override
    protected boolean processRetryableTask(RetryableTask retryableTask) {
        var order = retryableTaskMapper.convertJsonToOrder(retryableTask.getPayload());
        return deliveryService.processDelivery(order.getId(), order.getDeliveryAddress());
    }
}
