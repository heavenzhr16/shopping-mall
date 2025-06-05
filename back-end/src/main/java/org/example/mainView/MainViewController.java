package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mainView.dto.MainProductDto;
import org.example.mainView.dto.ProductListResponseDto;
import org.example.mainView.dto.ProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainViewController {

    private final MainViewService mainViewService;

    //메인 화면의 상품 리스트 전달 - 위클리, 신상품
    @GetMapping("/main-products")
    public ResponseEntity<?> getMainProducts() {
        log.info("메인 페이지 전체 상품 리스트 요청");
        MainProductDto result = mainViewService.getMainProductsForAllTypes();
        return ResponseEntity.ok(result);
    }

    // 상단바의 최상위 카테고리에서 상품 리스트 요청
    @PostMapping("/product-list")
    public ResponseEntity<?> getProductList(
            @Valid @RequestBody ProductRequestDto dto,
            @RequestParam(defaultValue = "1") int page
    ) {
        log.info("상품 리스트 요청: {}, page: {}", dto, page);
        ProductListResponseDto response = mainViewService.getProductList(dto, page);
        return ResponseEntity.ok(response);
    }

}
