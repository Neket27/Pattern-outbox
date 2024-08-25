package jgr.orderservice.service.retryable;

import jgr.orderservice.model.entity.RetryableTask;

import java.util.List;

public interface RetryableTaskProcessor {
    void processRetryableTasks(List<RetryableTask> retryableTasks);


}
