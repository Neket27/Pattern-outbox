package jgr.orderservice.scheduler;

import jgr.orderservice.service.RetryableTaskService;
import jgr.orderservice.service.processor.AbstractRetryableTaskProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Планировщик для выполнения задач, требующих повторной обработки
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RetryableTaskScheduler {
    private final RetryableTaskService retryableTaskService;
    private final List<AbstractRetryableTaskProcessor> retryableTaskProcessors;


    @Scheduled(fixedRate = 5000)
    public void executeRetryableTasks() {
        log.info("Starting retryable task processors");
        for (AbstractRetryableTaskProcessor taskProcessor : retryableTaskProcessors) {
            var taskType = taskProcessor.getRetryableTaskType();
            log.info("Processing tasks of type: {}", taskType);

            var retryableTasks = retryableTaskService.getRetryableTasksForProcessing(taskType);

            if (retryableTasks.isEmpty()) {
                log.info("No retryable tasks found for type: {}", taskType);
                continue;
            }
            taskProcessor.processRetryableTasks(retryableTasks);
        }
        log.info("Completed all retryable task processing");
    }
}
