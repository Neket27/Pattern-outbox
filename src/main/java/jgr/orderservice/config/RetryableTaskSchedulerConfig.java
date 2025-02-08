package jgr.orderservice.config;

import jgr.orderservice.service.processor.AbstractRetryableTaskProcessor;
import jgr.orderservice.service.processor.RetryableTaskProcessor;
import jgr.orderservice.service.processor.SendCreateDeliveryRequestRetryableTaskProcessor;
import jgr.orderservice.service.processor.SendCreateNotificationRequestRetryableTaskProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RetryableTaskSchedulerConfig {

    private final List<AbstractRetryableTaskProcessor> retryableTaskProcessors;

    private final SendCreateNotificationRequestRetryableTaskProcessor sendCreateNotificationRequestRetryableTaskProcessor;
    private final SendCreateDeliveryRequestRetryableTaskProcessor sendCreateDeliveryRequestRetryableTaskProcessor;

    @Bean
    public List<AbstractRetryableTaskProcessor> retryableTaskProcessorList() {
        retryableTaskProcessors.addAll(
                List.of(sendCreateNotificationRequestRetryableTaskProcessor,
                        sendCreateDeliveryRequestRetryableTaskProcessor));
        return retryableTaskProcessors;
    }
}
