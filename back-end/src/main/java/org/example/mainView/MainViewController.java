package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mainView.dto.MainProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

}
