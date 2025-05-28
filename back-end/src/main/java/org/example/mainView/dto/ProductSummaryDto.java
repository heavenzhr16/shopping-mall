package org.example.mainView.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDto {

    private Long productId;
    private String name;
    private Long price;
    private String thumbnailImageUrl;
    private String productName;
    private int reviewCount;
    private Double discountRate;
    private String genderType;

}
