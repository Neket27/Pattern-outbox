package jgr.orderservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jgr.orderservice.model.enums.RetryableTaskStatus;
import jgr.orderservice.model.enums.RetryableTaskType;
import jgr.orderservice.util.RetryableTaskStatusConverter;
import jgr.orderservice.util.RetryableTaskTypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;

/**
 * Задача, требующая повторного выполнения
 */
@Entity
@Getter
@Setter
public class RetryableTask extends BaseEntity {
    /**
     * Тело задачи
     */
    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;
    /**
     * Тип задачи
     */
    @Convert(converter = RetryableTaskTypeConverter.class)
    private RetryableTaskType type;
    /**
     * Тип задачи
     */
    @Convert(converter = RetryableTaskStatusConverter.class)
    private RetryableTaskStatus status;
    /**
     * Время повторного выполнения
     */
    private Instant retryTime;
}
