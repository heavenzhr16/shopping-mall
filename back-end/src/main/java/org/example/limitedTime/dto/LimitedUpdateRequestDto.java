package org.example.limitedTime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUpdateRequestDto {

    private Long productId; // 상품 ID
    private String category; // 카테고리
    private Double discountRate; // 할인율
    private String startAt; // 시작 시간 (ISO 8601 형식)
    private String endAt; // 종료 시간 (ISO 8601 형식)
}
