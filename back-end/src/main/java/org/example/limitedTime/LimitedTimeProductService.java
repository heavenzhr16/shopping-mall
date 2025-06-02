package org.example.limitedTime;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.AdminEntity;
import org.example.entity.LimitedTimeProductEntity;
import org.example.entity.ProductsEntity;
import org.example.limitedTime.dto.LimitedAdminRequestDto;
import org.example.limitedTime.dto.LimitedProductRequestDto;
import org.example.limitedTime.dto.LimitedUserResponseDto;
import org.example.mainView.ProductsRepository;
import org.example.mainView.ReviewsRepository;
import org.example.mainView.dto.ProductSummaryDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitedTimeProductService {

    private final LimitedTimeProductRepository limitedTimeProductRepository;
    private final ReviewsRepository reviewsRepository;
    private final ProductsRepository productsRepository;
    private final AdminRepository adminRepository;

    public LimitedUserResponseDto getLimitedTimeProductsAsMap() {
        List<String> limitedCategories = limitedTimeProductRepository.findAllCategoryNames();

        Map<String, List<ProductSummaryDto>> responseMap = new HashMap<>();

        LocalDateTime startAt = null;
        LocalDateTime endAt = null;

        for (String limitedCategory : limitedCategories) {
            // 해당 카테고리에 속하는 한정 시간 상품 조회
            List<LimitedTimeProductEntity> limitedEntities =
                    limitedTimeProductRepository.findByLimitedCategory(limitedCategory);

            if (limitedEntities.isEmpty()) continue; // 상품이 없으면 skip

            // 첫 엔티티 기준으로 시작/종료 일시 설정 (모든 상품이 같다고 가정)
            if (startAt == null && endAt == null) {
                startAt = limitedEntities.get(0).getStartAt();
                endAt = limitedEntities.get(0).getEndAt();
            }

            // 상품 리스트 추출
            List<ProductsEntity> products = limitedEntities.stream()
                    .map(LimitedTimeProductEntity::getProduct)
                    .collect(Collectors.toList());

            // ID 추출
            List<Long> productIds = products.stream()
                    .map(ProductsEntity::getProductId)
                    .collect(Collectors.toList());

            // 리뷰 수 일괄 조회
            Map<Long, Long> reviewCounts = reviewsRepository.countReviewsByProductIds(productIds);

            // 상품 ID → 할인율 매핑
            Map<Long, Double> discountRateMap = limitedEntities.stream()
                    .collect(Collectors.toMap(
                            e -> e.getProduct().getProductId(),
                            LimitedTimeProductEntity::getSpecialDiscountRate
                    ));

            // DTO 변환
            List<ProductSummaryDto> productDtos = products.stream()
                    .map(product -> {
                        long reviewCount = reviewCounts.getOrDefault(product.getProductId(), 0L);
                        double discountRate = discountRateMap.getOrDefault(product.getProductId(), 0.0);
                        return new ProductSummaryDto(product, reviewCount, discountRate);
                    })
                    .collect(Collectors.toList());

            // Map에 카테고리와 상품 리스트 추가
            responseMap.put(limitedCategory, productDtos);

        }

        return LimitedUserResponseDto.builder()
                .startAt(startAt)
                .endAt(endAt)
                .products(responseMap)
                .build();
    }

    @Transactional
    public void changeProducts(LimitedAdminRequestDto dto, String username) {
        // 기존 한정 상품 전체 삭제
        limitedTimeProductRepository.deleteAll();

        List<LimitedProductRequestDto> productList = dto.getProducts();
        LocalDateTime startAt = dto.getStartAt();
        LocalDateTime endAt = dto.getEndAt();

        AdminEntity admin = adminRepository.findByUsername(username);

        for (LimitedProductRequestDto productDto : productList) {
            Long productId = productDto.getProductId();
            Double discountRate = productDto.getDiscountRate();

            ProductsEntity product = productsRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. ID: " + productId));

            LimitedTimeProductEntity limitedEntity = LimitedTimeProductEntity.builder()
                    .product(product)
                    .specialDiscountRate(discountRate)
                    .startAt(startAt)
                    .endAt(endAt)
                    .limitedCategory(productDto.getCategory()) // 요청 DTO에서 직접 가져옴
                    .admin(admin)
                    .build();

            limitedTimeProductRepository.save(limitedEntity);
        }
        log.info("총 {}개의 한정 상품이 등록되었습니다 by {}", productList.size(), username);
    }
}
