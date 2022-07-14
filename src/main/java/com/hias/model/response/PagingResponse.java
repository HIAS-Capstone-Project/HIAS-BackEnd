package com.hias.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse {
    private List<?> listItems;
    private int pageIndex;
    private int totalPages;
    private Long totalItems;
}
