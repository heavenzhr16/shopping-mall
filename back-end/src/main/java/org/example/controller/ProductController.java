package org.example.controller;

import org.example.dto.ProductDetailDto;
import org.example.dto.ReviewDto;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        try {
            ProductDetailDto dto = productService.getProductDetail(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }

    // 상품 리뷰 페이징 조회 (예: /api/products/1/reviews?page=0&size=5)
    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getProductReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try {
            Page<ReviewDto> reviewPage = productService.getReviewsByProductId(id, PageRequest.of(page, size));
            return ResponseEntity.ok(reviewPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("리뷰 조회 중 오류 발생: " + e.getMessage());
        }
    }
}
