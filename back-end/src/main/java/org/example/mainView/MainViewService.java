package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ProductsEntity;
import org.example.mainView.dto.MainProductDto;
import org.example.mainView.dto.ProductSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainViewService {

    private final ProductsRepository productsRepository;

    public MainProductDto getMainProductsByType(String type) {
        if (!type.equals("W") && !type.equals("M") && !type.equals("K")) {
            log.warn("유효하지 않은 type: {}", type);
            return null; // 또는 CustomException 던져도 좋음
        }

        // 성별타입별 판매순 8개
        List<ProductsEntity> topWeeklyProducts = productsRepository
                .findTop8ByCategory_GenderTypeOrderBySalesCountDesc(type);

        // 신상품 8개
        List<ProductsEntity> newProducts = productsRepository.findTop8ByOrderByCreatedAtDesc();

        // 엔티티를 DTO로 매핑
        List<ProductSummaryDto> weeklyDtos = topWeeklyProducts.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());

        List<ProductSummaryDto> newDtos = newProducts.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());

        return new MainProductDto(weeklyDtos, newDtos);
    }

    private ProductSummaryDto convertToSummaryDto(ProductsEntity product) {
        return ProductSummaryDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnailImageUrl(
                        product.getProductImages() != null && !product.getProductImages().isEmpty() ?
                                product.getProductImages().get(0).getImageUrl() : null
                )
                .discountRate(product.getDiscountRate())
                .build();
    }
}

