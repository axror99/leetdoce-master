package com.example.leetdoce.simple_class;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapToJsonConverterForInteger implements AttributeConverter<Map<Integer,String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Integer, String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<Integer, String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new HashMap<>();
        }

        try {
            return objectMapper.readValue(dbData, new TypeReference<HashMap<Integer, String>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to map", e);
        }
    }
}
