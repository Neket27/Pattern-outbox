package jgr.orderservice.util;

import jakarta.persistence.AttributeConverter;
import jgr.orderservice.model.enums.RetryableTaskStatus;
import jgr.orderservice.model.enums.RetryableTaskType;

public class RetryableTaskTypeConverter implements AttributeConverter<RetryableTaskType, String> {

    @Override
    public String convertToDatabaseColumn(RetryableTaskType status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public RetryableTaskType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RetryableTaskType.fromValue(dbData);
    }
}