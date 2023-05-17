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
        jsonGenerator.writeObjectField("totalElements", pageWrapper.getTotalElements());

        // Serializar o Pageable manualmente
        jsonGenerator.writeObjectFieldStart("pageable");
        jsonGenerator.writeBooleanField("paged", pageWrapper.getPageable().isPaged());
        jsonGenerator.writeNumberField("pageNumber", pageWrapper.getPageable().getPageNumber());
        jsonGenerator.writeNumberField("pageSize", pageWrapper.getPageable().getPageSize());
        // Outros campos do Pageable, se necessário
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}






