package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "user_info")
@IdClass(UserInfoEntity.UserInfoId.class)
public class UserInfoEntity {
    // Getters and Setters
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

}