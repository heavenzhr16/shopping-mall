package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ProductsEntity;
import org.example.mainView.dto.MainProductDto;
import org.example.mainView.dto.ProductSummaryDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainViewService {

    private final ProductsRepository productsRepository;

    public MainProductDto getMainProductsForAllTypes() {
        Map<String, List<ProductSummaryDto>> weeklyProductsMap = new HashMap<>();

        // W, M, K 각 성별에 대해 주간 베스트 상품 8개씩
        for (String gender : List.of("W", "M", "K")) {
            List<String> targetGenders;
            if (gender.equals("W")) {
                targetGenders = List.of("W", "U");
            } else if (gender.equals("M")) {
                targetGenders = List.of("M", "U");
            } else {
                targetGenders = List.of("K"); // 아동은 단독
            }

            List<ProductsEntity> weekly = productsRepository
                    .findTop8ByCategory_GenderTypeInOrderBySalesCountDesc(targetGenders);

            List<ProductSummaryDto> weeklyDtos = weekly.stream()
                    .map(this::convertToSummaryDto)
                    .collect(Collectors.toList());

            weeklyProductsMap.put(gender, weeklyDtos);
        }

        // 신상품 8개는 공통
        List<ProductsEntity> newProducts = productsRepository.findTop8ByOrderByCreatedAtDesc();
        List<ProductSummaryDto> newDtos = newProducts.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());

        return new MainProductDto(weeklyProductsMap, newDtos);
    }

    private ProductSummaryDto convertToSummaryDto(ProductsEntity product) {
        return ProductSummaryDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnailImageUrl(
                        product.getProductImages() != null && !product.getProductImages().isEmpty() ?
                                product.getProductImages().get(0).getImageUrl() : "back-end/src/main/resources/static/images/test.jpg"
                )
                .discountRate(product.getDiscountRate())
                .build();
    }
}

