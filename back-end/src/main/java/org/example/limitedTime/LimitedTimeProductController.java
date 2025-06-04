package org.example.limitedTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.limitedTime.dto.LimitedAdminRequestDto;
import org.example.limitedTime.dto.LimitedUserResponseDto;
import org.example.limitedTime.dto.UpdateProductDto;
import org.example.mainView.dto.MainProductDto;
import org.example.mainView.dto.ProductSummaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    // 상품 등록을 위해서, 전체 상품을 조회
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts() {
        log.info("전체 상품 조회 요청");
        List<ProductSummaryDto> productSummaryDto = limitedTimeProductService.getAllProducts();
        return ResponseEntity.ok(productSummaryDto);
    }


    // 관리자의 한정 시간 상품 등록 - 입력받은 상품id 들로 기간상품들로 일괄 교체
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changeProducts")
    public ResponseEntity<?> registerLimitedTimeProducts(
            @RequestBody LimitedAdminRequestDto products
            ) {
        log.info("관리자 한정 시간 상품 등록 요청: {}", products);

        // 토큰에서 사용자 이름 추출
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        limitedTimeProductService.changeProducts(products, username);

        return ResponseEntity.ok().build();
    }

    // 관리자의 한정 시간 상품들 더해서 추가 - 일부 상품 추가 날짜는 기존 상품과 동일하게
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProducts")
    public ResponseEntity<?> addLimitedTimeProducts(
            @RequestBody LimitedAdminRequestDto products
            ) {
        log.info("관리자 한정 시간 상품 일부 추가 요청: {}", products);

        // 토큰에서 사용자 이름 추출
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        limitedTimeProductService.addProducts(products, username);

        return ResponseEntity.ok().build();
    }

    // 관리자의 한정 시간 상품 일부 삭제 - 특정 상품 삭제
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteLimitedTimeProduct(
            @RequestParam List<Long> productId
    ) {
        log.info("관리자 한정 시간 상품 삭제 요청: {}", productId);
        limitedTimeProductService.deleteLimitedTimeProduct(productId);

        return ResponseEntity.ok().build();
    }

    // 관리자의 한정 시간 상품 전체 삭제 - 모든 상품 삭제
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAllProducts")
    public ResponseEntity<?> deleteAllLimitedTimeProducts() {
        log.info("관리자 한정 시간 상품 전체 삭제 요청");
        limitedTimeProductService.deleteAllLimitedTimeProducts();
        return ResponseEntity.ok().build();
    }

    // 일부 상품의 할인율, 카테고리 변경 변경
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProducts")
    public ResponseEntity<?> updateLimitedTimeProducts(
            @RequestBody UpdateProductDto dto
            ) {
        log.info("관리자 한정 시간 상품 일부 업데이트 요청: {}", dto);

        // 토큰에서 사용자 이름 추출
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        limitedTimeProductService.updateLimitedTimeProducts(dto, username);

        return ResponseEntity.ok().build();
    }

}
