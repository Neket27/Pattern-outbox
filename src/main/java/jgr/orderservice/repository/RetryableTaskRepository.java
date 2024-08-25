package jgr.orderservice.repository;

import jakarta.persistence.LockModeType;
import jgr.orderservice.model.entity.RetryableTask;
import jgr.orderservice.model.enums.RetryableTaskStatus;
import jgr.orderservice.model.enums.RetryableTaskType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RetryableTaskRepository extends CrudRepository<RetryableTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r from RetryableTask r where r.type= :type " +
            "AND r.retryTime<= :retryTime " +
            "AND r.status= :status " +
            "order by r.retryTime asc")
    List<RetryableTask> findRetryableTaskForProcessing(RetryableTaskType type, Instant retryTime,
                                                       RetryableTaskStatus status, Pageable pageable);

    @Query("UPDATE RetryableTask r SET r.status= :status where r in :retryableTasks")
    void updateRetryableTasks(List<RetryableTask> retryableTasks, RetryableTaskStatus status);
}
