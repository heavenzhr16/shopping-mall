package org.example.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchFilterRequestDto {
    private String keyword;
    private Long minPrice;
    private Long maxPrice;
    private Long categoryId;
    private List<Long> colorIds;
}
