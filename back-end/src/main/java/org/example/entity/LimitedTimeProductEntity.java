package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "limited_time_products")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class LimitedTimeProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "limited_id")
    private Long limitedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminEntity admin;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "limited_category", length = 100)
    private String limitedCategory;

    @Column(name = "special_discount_rate")
    private Double specialDiscountRate; // optional, 상품 discount_rate와 다르게 설정 가능

    // 기본 생성자
    public LimitedTimeProductEntity() {}
}