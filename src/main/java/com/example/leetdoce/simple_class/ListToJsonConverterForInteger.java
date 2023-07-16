package com.example.leetdoce.simple_class;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Converter
public class ListToJsonConverterForInteger implements AttributeConverter<List<Integer>,String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> integerList) {
        try {
            return objectMapper.writeValueAsString(integerList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting map to JSON", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new LinkedList<>();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to map", e);
        }
    }
}
