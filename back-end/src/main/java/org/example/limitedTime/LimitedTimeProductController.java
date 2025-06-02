package org.example.limitedTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.limitedTime.dto.LimitedAdminRequestDto;
import org.example.limitedTime.dto.LimitedUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/timeSale")
@RequiredArgsConstructor
public class LimitedTimeProductController {

    private final LimitedTimeProductService limitedTimeProductService;

    // 일반 유저의 한정 시간 상품 조회
    @GetMapping("/products")
    public ResponseEntity<?> getLimitedTimeProducts() {
        log.info("한정 시간 상품 조회 요청");
        LimitedUserResponseDto dto = limitedTimeProductService.getLimitedTimeProductsAsMap();
        if (dto.getProducts().isEmpty()) {
            log.info("한정 시간 상품이 없습니다.");
            return ResponseEntity.noContent().build();
        }
        log.info("한정 시간 상품 조회 성공: {}", dto.getProducts().size());
        return ResponseEntity.ok(dto);
    }

    // 관리자의 한정 시간 상품 등록 - 입력받은 상품id 들로 기간상품들로 일괄 교체
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changeProducts")
    public ResponseEntity<?> registerLimitedTimeProducts(
            @RequestBody LimitedAdminRequestDto products
            ) {
        log.info("관리자 한정 시간 상품 등록 요청: {}", products);

        // 토큰에서 사용자 이름 추출
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("작업요청한 관리자: {}", username);

        limitedTimeProductService.changeProducts(products, username);

        return ResponseEntity.ok().build();
    }

    // 관리자의 한정 시간 상품 일부 추가 - 일부 상품 추가 날짜는 기존 상품과 동일하게

    // 관리자의 한정 시간 상품 일부 삭제 - 특정 상품 삭제

    // 관리자의 한정 시간 상품 전체 삭제 - 모든 상품 삭제
}
