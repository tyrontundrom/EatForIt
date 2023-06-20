package com.tyrontundrom.eatforit.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
class UUIDConverter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData != null ? UUID.fromString(dbData) : null;
    }
}
