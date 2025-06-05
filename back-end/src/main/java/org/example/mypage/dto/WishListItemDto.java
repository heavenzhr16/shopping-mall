package org.example.mypage.dto;

public class WishListItemDto {
  private Long productId;
  private String name;
  private Long price;
  private String thumbnailUrl;

  public WishListItemDto(Long productId, String name, Long price, String thumbnailUrl) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.thumbnailUrl = thumbnailUrl;
  }

  public Long getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public Long getPrice() {
    return price;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }
}
