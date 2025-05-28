package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mainView.dto.MainProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainViewController {

    private final MainViewService mainViewService;

    //메인 화면의 상품 리스트 전달
    @GetMapping("/main-products")
    public ResponseEntity<?> getMainProducts(@RequestParam("type") String type) {
        log.info("메인 페이지 접근하여 리스트 요청: {}", type);

        try {
            MainProductDto result = mainViewService.getMainProductsByType(type);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("유효하지 않은 타입: {}", type);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "errorCode", "INVALID_ARGUMENT",
                    "message", e.getMessage()
            ));
        }
    }

}
