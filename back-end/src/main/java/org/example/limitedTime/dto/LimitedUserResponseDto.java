package org.example.limitedTime.dto;


import lombok.*;
import org.example.mainView.dto.ProductSummaryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUserResponseDto {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Map<String, List<ProductSummaryDto>> products;  //카테고리 : 상품리스트
}
