package org.example.mainView.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ColorEntity;
import org.example.entity.LimitedTimeProductEntity;
import org.example.entity.ProductsEntity;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDto {

    private Long productId;
    private String name;
    private Long price;
    private String thumbnailImageUrl;
    private Long reviewCount;
    private Double discountRate;
    private List<String> colors;
    private String genderType;

    public ProductSummaryDto(ProductsEntity entity, long reviewCount) {
        this.productId = entity.getProductId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.discountRate = entity.getDiscountRate();
        this.reviewCount = reviewCount;
        this.colors = entity.getColors().stream()
                .map(ColorEntity::getName)
                .collect(Collectors.toList());
        this.genderType = entity.getCategory().getGenderType();

        // 썸네일 URL 추출 (대표 이미지 1개만)
        if (entity.getProductImages() != null && !entity.getProductImages().isEmpty()) {
            this.thumbnailImageUrl = entity.getProductImages().get(0).getImageUrl(); // 이미지 URL 필드명은 실제 이름에 따라 수정
        } else {
            this.thumbnailImageUrl = null;
        }
    }

    public ProductSummaryDto(ProductsEntity entity, long reviewCount, double discountRate) {
        this.productId = entity.getProductId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.discountRate = discountRate;
        this.reviewCount = reviewCount;
        this.colors = entity.getColors().stream()
                .map(ColorEntity::getName)
                .collect(Collectors.toList());
        this.genderType = entity.getCategory().getGenderType();

        // 썸네일 URL 추출 (대표 이미지 1개만)
        if (entity.getProductImages() != null && !entity.getProductImages().isEmpty()) {
            this.thumbnailImageUrl = entity.getProductImages().get(0).getImageUrl(); // 이미지 URL 필드명은 실제 이름에 따라 수정
        } else {
            this.thumbnailImageUrl = null;
        }
    }

}
