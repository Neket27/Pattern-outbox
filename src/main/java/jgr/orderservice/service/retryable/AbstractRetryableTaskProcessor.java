package jgr.orderservice.service.retryable;

import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.service.RetryableTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRetryableTaskProcessor implements RetryableTaskProcessor {
    private final RetryableTaskService retryableTaskService;

    @Override
    public void processRetryableTasks(List<RetryableTask> retryableTasks) {
        log.info("Starting to process {} retryable tasks", retryableTasks.size());

        List<RetryableTask> successRetryableTasks = new ArrayList<>();
        for (RetryableTask retryableTask : retryableTasks) {
            log.debug("Processing retryable task with ID: {}", retryableTask.getId());
            boolean isSuccess = processRetryableTask(retryableTask);
            if (isSuccess) {
                log.debug("Task with ID: {} processed successfully", retryableTask.getId());
                successRetryableTasks.add(retryableTask);
            } else {
                log.warn("Task with ID: {} failed to process", retryableTask.getId());
            }
        }

        if (!successRetryableTasks.isEmpty()) {
            retryableTaskService.markRetryableTasksAsCompleted(successRetryableTasks);
            log.info("Marked {} tasks as completed", successRetryableTasks.size());
        } else {
            log.info("No tasks were marked as completed");
        }
    }

    protected abstract boolean processRetryableTask(RetryableTask retryableTask);
}
