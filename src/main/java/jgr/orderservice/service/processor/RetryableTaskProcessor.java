package jgr.orderservice.service.processor;

import jgr.orderservice.model.entity.RetryableTask;

import java.util.List;

public interface RetryableTaskProcessor {
    void processRetryableTasks(List<RetryableTask> retryableTasks);


}
