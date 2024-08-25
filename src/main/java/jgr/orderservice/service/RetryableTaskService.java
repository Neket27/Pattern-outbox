package jgr.orderservice.service;

import jakarta.transaction.Transactional;
import jgr.orderservice.mapper.RetryableTaskMapper;
import jgr.orderservice.model.entity.Order;
import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.model.enums.RetryableTaskStatus;
import jgr.orderservice.model.enums.RetryableTaskType;
import jgr.orderservice.repository.RetryableTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryableTaskService {
    private final RetryableTaskRepository retryableTaskRepository;
    private final RetryableTaskMapper retryableTaskMapper;
    @Value("${retryabletask.limit}")
    private Integer limit;
    @Value("${retryabletask.timeoutInSeconds}")
    private Integer timeoutInSeconds;

    @Transactional
    public RetryableTask createRetryableTask(Order order, RetryableTaskType type) {
        RetryableTask retryableTask = retryableTaskMapper.toRetryableTask(order, type);
        return retryableTaskRepository.save(retryableTask);
    }

    @Transactional
    public List<RetryableTask> getRetryableTasksForProcessing(RetryableTaskType type) {
        var currentTime = Instant.now();
        Pageable pageable = PageRequest.of(0, limit);
        List<RetryableTask> retryableTasks = retryableTaskRepository.findRetryableTaskForProcessing(
                type, currentTime, RetryableTaskStatus.IN_PROGRESS, pageable);

        for (RetryableTask retryableTask : retryableTasks) {
            retryableTask.setRetryTime(currentTime.plus(Duration.ofSeconds(timeoutInSeconds)));
        }
        return retryableTasks;
    }


    @Transactional
    public void markRetryableTasksAsCompleted(List<RetryableTask> retryableTasks) {
        retryableTaskRepository.updateRetryableTasks(retryableTasks, RetryableTaskStatus.SUCCESS);
    }


}
