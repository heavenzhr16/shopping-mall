package org.example.mainView.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponseDto {

    private int currentPage;
    private int totalPages;
    private List<ProductSummaryDto> products;

}
