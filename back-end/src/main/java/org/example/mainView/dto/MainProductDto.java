package org.example.mainView.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainProductDto {
    private List<ProductSummaryDto> weeklyProducts;
    private List<ProductSummaryDto> newProducts;
}
