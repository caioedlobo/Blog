package br.com.caiolobo.blogapplication.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@JsonSerialize(using = PageWrapperSerializer.class)
@JsonView(View.Base.class)
public class PageWrapper<T> {
    private final List<T> content;
    private final Pageable pageable;
    private final long totalElements;

    public PageWrapper(List<T> content, Pageable pageable, long totalElements) {
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
    }


    public List<T> getContent() {
        return content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
