package jgr.orderservice.util;

import jakarta.persistence.AttributeConverter;
import jgr.orderservice.model.enums.RetryableTaskStatus;

public class RetryableTaskTypeConverter implements AttributeConverter<RetryableTaskStatus, String> {

    @Override
    public String convertToDatabaseColumn(RetryableTaskStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public RetryableTaskStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RetryableTaskStatus.fromValue(dbData);
    }
}