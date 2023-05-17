package br.com.caiolobo.blogapplication.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonSerializer;

public class PageWrapperSerializer extends JsonSerializer<PageWrapper<?>> {
    @Override
    public void serialize(PageWrapper<?> pageWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("content", pageWrapper.getContent());
        jsonGenerator.writeNumberField("totalElements", pageWrapper.getTotalElements());
        // Adicione outros campos da página, se necessário

        jsonGenerator.writeEndObject();
    }
}





