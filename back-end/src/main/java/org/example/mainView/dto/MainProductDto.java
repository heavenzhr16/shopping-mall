package org.example.mainView.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainProductDto {
    private Map<String, List<ProductSummaryDto>> weeklyProducts; // W, M, K 각 8개
    private List<ProductSummaryDto> newProducts; // 공통 8개
}
