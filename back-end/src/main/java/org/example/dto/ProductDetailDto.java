package org.example.dto;

import java.util.List;

public class ProductDetailDto {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private String categoryName;
    private List<String> imageUrls;
    private List<ReviewDto> reviews;
    private List<ProductOptionDto> options;

    public ProductDetailDto() {
    }

    public ProductDetailDto(Long productId, String name, String description, Long price,
                            String categoryName, List<String> imageUrls,
                            List<ReviewDto> reviews, List<ProductOptionDto> options) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.imageUrls = imageUrls;
        this.reviews = reviews;
        this.options = options;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public List<ProductOptionDto> getOptions() {
        return options;
    }
}
