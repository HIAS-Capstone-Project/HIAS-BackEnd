package com.hias.model.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagingResponseModel<T> implements Serializable {
    private long totalElements;
    private int pageSize;
    private int pageNumber;
    private List<T> rows;

    private int totalPage;

    public PagingResponseModel(Page<T> page) {
        if (page == null) {
            page = new PageImpl<>(Collections.emptyList());
        }
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.rows = page.getContent();
        this.totalPage = page.getTotalPages();
    }
}
