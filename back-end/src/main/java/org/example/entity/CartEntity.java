package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    // Getters and Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsChecked() { return isChecked; }
    public void setIsChecked(Boolean isChecked) { this.isChecked = isChecked; }

    public String getOptionInfo() { return optionInfo; }
    public void setOptionInfo(String optionInfo) { this.optionInfo = optionInfo; }

    public Long getPriceAtAdd() { return priceAtAdd; }
    public void setPriceAtAdd(Long priceAtAdd) { this.priceAtAdd = priceAtAdd; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public UsersEntity getUser() { return user; }
    public void setUser(UsersEntity user) { this.user = user; }

    public ProductsEntity getProduct() { return product; }
    public void setProduct(ProductsEntity product) { this.product = product; }
}