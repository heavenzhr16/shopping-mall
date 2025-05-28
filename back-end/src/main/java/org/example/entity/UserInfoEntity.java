package org.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@IdClass(UserInfoEntity.UserInfoId.class)
public class UserInfoEntity {
    @Id
    @Column(name = "address_id")
    private Long addressId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recipient_name", length = 50, nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone", length = 20, nullable = false)
    private String recipientPhone;

    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @Column(name = "address_line1", length = 255, nullable = false)
    private String addressLine1;

    @Column(name = "address_line2", length = 255, nullable = false)
    private String addressLine2;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "address_alias", length = 20)
    private String addressAlias;

    @Column(name = "delivery_message", length = 255)
    private String deliveryMessage;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UsersEntity user;

    // 복합키 클래스
    public static class UserInfoId implements Serializable {
        private Long addressId;
        private Long userId;

        public UserInfoId() {}

        public UserInfoId(Long addressId, Long userId) {
            this.addressId = addressId;
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserInfoId that = (UserInfoId) o;
            return addressId.equals(that.addressId) && userId.equals(that.userId);
        }

        @Override
        public int hashCode() {
            return addressId.hashCode() + userId.hashCode();
        }

        // Getters and Setters
        public Long getAddressId() { return addressId; }
        public void setAddressId(Long addressId) { this.addressId = addressId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    // 기본 생성자
    public UserInfoEntity() {}

    // Getters and Setters
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }

    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public Boolean getIsDefault() { return isDefault; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }

    public String getAddressAlias() { return addressAlias; }
    public void setAddressAlias(String addressAlias) { this.addressAlias = addressAlias; }

    public String getDeliveryMessage() { return deliveryMessage; }
    public void setDeliveryMessage(String deliveryMessage) { this.deliveryMessage = deliveryMessage; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UsersEntity getUser() { return user; }
    public void setUser(UsersEntity user) { this.user = user; }
}