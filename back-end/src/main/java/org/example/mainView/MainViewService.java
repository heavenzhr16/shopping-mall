package org.example.mainView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ProductsEntity;
import org.example.mainView.dto.MainProductDto;
import org.example.mainView.dto.ProductListResponseDto;
import org.example.mainView.dto.ProductRequestDto;
import org.example.mainView.dto.ProductSummaryDto;
import org.springframework.stereotype.Service;
import org.example.entity.ColorEntity;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainViewService {

    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ReviewsRepository reviewsRepository;

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

    public ProductListResponseDto getProductList(ProductRequestDto dto, int page) {
        final int pageSize = 18;
        final int offset = (page - 1) * pageSize;

        // 1. 사용할 카테고리 ID 조회
        Optional<Long> optionalCategoryId = Optional.empty();

        if (dto.getDepth3Category() != null && !dto.getDepth3Category().isEmpty()) {
            optionalCategoryId = categoriesRepository.findCategoryIdByGenderTypeAndNameAndDepth(
                    dto.getType(),
                    dto.getDepth3Category(),
                    3L
            );
        }

        if (optionalCategoryId.isEmpty()) {
            optionalCategoryId = categoriesRepository.findCategoryIdByGenderTypeAndNameAndDepth(
                    dto.getType(),
                    dto.getDepth2Category(),
                    2L
            );
        }

        if (optionalCategoryId.isEmpty()) {
            log.info("카테고리를 찾을 수 없습니다. type={}, depth2={}, depth3={}",
                    dto.getType(), dto.getDepth2Category(), dto.getDepth3Category());

            ProductListResponseDto emptyDto = new ProductListResponseDto();
            emptyDto.setCurrentPage(page);
            emptyDto.setTotalPages(0);
            emptyDto.setProducts(Collections.emptyList());
            return emptyDto;
        }

        Long categoryId = optionalCategoryId.get();

        // 2. 2차 필터링 시작
        List<ProductsEntity> filtered = productsRepository.findByCategory_CategoryId(categoryId);

        // 가격 필터
        if (dto.getPriceMin() > 0 || dto.getPriceMax() > 0) {
            long min = dto.getPriceMin();
            long max = dto.getPriceMax() > 0 ? dto.getPriceMax() : Long.MAX_VALUE;
            filtered = filtered.stream()
                    .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                    .collect(Collectors.toList());
        }
        log.info("가격 필터 후 결과 개수: {}", filtered.size());

        // 상세 카테고리 필터
        if (dto.getDepth3Category() != null && !dto.getDepth3Category().isEmpty()) {
            filtered = filtered.stream()
                    .filter(p -> p.getCategory().getName().equals(dto.getDepth3Category()))
                    .collect(Collectors.toList());
        }
        log.info("상세 카테고리 필터 후 결과 개수: {}", filtered.size());


        for (ProductsEntity p : filtered) {
            log.info("상품 ID={} 색상 목록={}", p.getColors(), p.getColors().stream()
                    .map(ColorEntity::getName).collect(Collectors.toList()));
        }

        // 색상 필터
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            filtered = filtered.stream()
                    .filter(product -> {
                        List<String> productColors = product.getColors().stream()
                                .map(ColorEntity::getName)
                                .collect(Collectors.toList());
                        return productColors.stream().anyMatch(dto.getColors()::contains);
                    })
                    .collect(Collectors.toList());
        }
        log.info("색상 필터 후 결과 개수: {}", filtered.size());

        // 정렬 기준 적용
        Comparator<ProductsEntity> comparator = getComparator(dto.getSortBy());
        if (comparator != null) {
            filtered = filtered.stream().sorted(comparator).collect(Collectors.toList());
        }

        int totalSize = filtered.size();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        // 페이징 + 리뷰 수 맵 조회
        List<ProductsEntity> pagedProducts = filtered.stream()
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<Long> productIds = pagedProducts.stream()
                .map(ProductsEntity::getProductId)
                .collect(Collectors.toList());

        Map<Long, Long> reviewCounts = reviewsRepository.countReviewsByProductIds(productIds);

        List<ProductSummaryDto> result = pagedProducts.stream()
                .map(product -> {
                    long reviewCount = reviewCounts.getOrDefault(product.getProductId(), 0L);
                    return new ProductSummaryDto(product, reviewCount);
                })
                .collect(Collectors.toList());


        ProductListResponseDto resultDto = new ProductListResponseDto();
        resultDto.setCurrentPage(page);
        resultDto.setTotalPages(totalPages);
        resultDto.setProducts(result);

        return resultDto;
    }

    private Comparator<ProductsEntity> getComparator(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "sales"; // 기본값
        }

        switch (sortBy) {
            case "sales":
                return Comparator.comparing(ProductsEntity::getSalesCount).reversed();
            case "name":
                return Comparator.comparing(ProductsEntity::getName, String.CASE_INSENSITIVE_ORDER);
            case "price":
                return Comparator.comparing(ProductsEntity::getPrice); // 오름차순
            case "priceDesc":
                return Comparator.comparing(ProductsEntity::getPrice).reversed(); // 내림차순
            case "new":
                return Comparator.comparing(ProductsEntity::getCreatedAt).reversed();
            case "views":
                return Comparator.comparing(ProductsEntity::getViews).reversed();
            case "likes":
                return Comparator.comparing(ProductsEntity::getLikes).reversed();
            case "reviews":
                return Comparator.comparing(p -> p.getReviews().size(), Comparator.reverseOrder());
            default:
                return Comparator.comparing(ProductsEntity::getSalesCount).reversed(); // 예외 대비 기본값
        }
    }

}

