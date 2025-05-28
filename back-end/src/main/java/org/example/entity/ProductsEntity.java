package org.example.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "img_url", length = 255)
    private String imgUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "Field", length = 255)
    private String field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoriesEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImagesEntity> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<InquiryEntity> inquiries;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ReviewsEntity> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<WishListsEntity> wishLists;

    // 기본 생성자
    public ProductsEntity() {}

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public CategoriesEntity getCategory() { return category; }
    public void setCategory(CategoriesEntity category) { this.category = category; }

    public List<ProductImagesEntity> getProductImages() { return productImages; }
    public void setProductImages(List<ProductImagesEntity> productImages) { this.productImages = productImages; }

    public List<CartEntity> getCarts() { return carts; }
    public void setCarts(List<CartEntity> carts) { this.carts = carts; }

    public List<InquiryEntity> getInquiries() { return inquiries; }
    public void setInquiries(List<InquiryEntity> inquiries) { this.inquiries = inquiries; }

    public List<ReviewsEntity> getReviews() { return reviews; }
    public void setReviews(List<ReviewsEntity> reviews) { this.reviews = reviews; }

    public List<WishListsEntity> getWishLists() { return wishLists; }
    public void setWishLists(List<WishListsEntity> wishLists) { this.wishLists = wishLists; }
}