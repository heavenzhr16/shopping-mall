package org.example.service;

import org.example.dto.ProductDetailDto;
import org.example.dto.ProductOptionDto;
import org.example.dto.ReviewDto;
import org.example.entity.ProductImagesEntity;
import org.example.entity.ProductsEntity;
import org.example.repository.ProductRepository;
import org.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public ProductDetailDto getProductDetail(Long productId) {
        try {
            System.out.println(" 1. productId로 상품 조회 시작: " + productId);
            ProductsEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
            System.out.println(" 2. 상품 조회 완료: " + product.getName());

            // 이미지
            List<String> imageUrls = product.getProductImages() != null ?
                    product.getProductImages().stream()
                            .map(ProductImagesEntity::getImageUrl)
                            .collect(Collectors.toList()) : List.of();
            System.out.println(" 3. 이미지 수: " + imageUrls.size());

            // 리뷰 (전체 리뷰 포함 — 페이징은 별도 컨트롤러에서 처리)
            List<ReviewDto> reviews = product.getReviews() != null ?
                    product.getReviews().stream()
                            .map(r -> new ReviewDto(
                                    r.getReviewTitle(),
                                    r.getReviewContent(),
                                    r.getRating()
                            ))
                            .collect(Collectors.toList()) : List.of();
            System.out.println(" 4. 리뷰 수: " + reviews.size());

            // 옵션
            List<ProductOptionDto> options = product.getProductOptions() != null ?
                    product.getProductOptions().stream()
                            .map(o -> new ProductOptionDto(
                                    o.getColor(),
                                    o.getSize(),
                                    o.getStock()
                            ))
                            .collect(Collectors.toList()) : List.of();
            System.out.println(" 5. 옵션 수: " + options.size());

            String categoryName = product.getCategory() != null ? product.getCategory().getName() : "N/A";
            System.out.println(" 6. 카테고리 이름: " + categoryName);

            return new ProductDetailDto(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    categoryName,
                    imageUrls,
                    reviews,
                    options
            );

        } catch (Exception e) {
            System.out.println(" 예외 발생: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("상품 상세 조회 중 오류 발생: " + e.getMessage());
        }
    }

    // 리뷰 페이징용 메서드 (별도 컨트롤러에서 사용)
    public Page<ReviewDto> getReviewsByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findByProduct_ProductId(productId, pageable)
                .map(r -> new ReviewDto(
                        r.getReviewTitle(),
                        r.getReviewContent(),
                        r.getRating()
                ));
    }
}