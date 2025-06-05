package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "cart")
@IdClass(CartEntity.CartId.class)
public class CartEntity {
    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_checked")
    private Boolean isChecked;

    @Column(name = "option_info")
    private String optionInfo;

    @Column(name = "price_at_add")
    private Long priceAtAdd;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    // 복합키 클래스
    public static class CartId implements Serializable {
        private Long cartId;
        private Long userId;

        public CartId() {}

        public CartId(Long cartId, Long userId) {
            this.cartId = cartId;
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartId cartId1 = (CartId) o;
            return cartId.equals(cartId1.cartId) && userId.equals(cartId1.userId);
        }

        @Override
        public int hashCode() {
            return cartId.hashCode() + userId.hashCode();
        }

        // Getters and Setters
        public Long getCartId() { return cartId; }
        public void setCartId(Long cartId) { this.cartId = cartId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    // 기본 생성자
    public CartEntity() {}

}