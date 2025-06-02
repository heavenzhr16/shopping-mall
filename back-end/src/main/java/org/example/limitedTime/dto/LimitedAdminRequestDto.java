package org.example.limitedTime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.LimitedTimeProductEntity;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class LimitedAdminRequestDto {
    List<LimitedProductRequestDto> products; // 상품id, 카테고리, 할인율
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
