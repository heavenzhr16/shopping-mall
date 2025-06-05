package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "products")
public class ProductsEntity {

    // Getters and Setters
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

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

    @Column(name = "sales_count", nullable = false)
    private Long salesCount = 0L; // 기본값 설정

    @Column(name = "likes")
    private Long likes = 0L; // 기본값 설정

    @Column(name = "views")
    private Long views = 0L;

    @Column(name = "discount_rate", nullable = false)
    private Double discountRate = 0.0; // 기본값 0.0 (할인 없음)

    @ManyToMany
    @JoinTable(
            name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<ColorEntity> colors;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOptionsEntity> productOptions;

    // 기본 생성자
    public ProductsEntity() {}

}