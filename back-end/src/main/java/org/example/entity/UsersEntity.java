package org.example.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;

    @Column(name = "phone_num", nullable = false)
    private Long phoneNum;

    @Column(name = "login_type", length = 30, nullable = false)
    private String loginType;

    @Column(name = "user_status", length = 20, nullable = false)
    private String userStatus = "ACTIVE";

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(name = "lastlogin_at")
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrdersEntity> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<InquiryEntity> inquiries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewsEntity> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WishListsEntity> wishLists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserInfoEntity> userInfos;

    // 기본 생성자
    public UsersEntity() {}

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public Long getPhoneNum() { return phoneNum; }
    public void setPhoneNum(Long phoneNum) { this.phoneNum = phoneNum; }

    public String getLoginType() { return loginType; }
    public void setLoginType(String loginType) { this.loginType = loginType; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }

    public List<OrdersEntity> getOrders() { return orders; }
    public void setOrders(List<OrdersEntity> orders) { this.orders = orders; }

    public List<CartEntity> getCarts() { return carts; }
    public void setCarts(List<CartEntity> carts) { this.carts = carts; }

    public List<InquiryEntity> getInquiries() { return inquiries; }
    public void setInquiries(List<InquiryEntity> inquiries) { this.inquiries = inquiries; }

    public List<ReviewsEntity> getReviews() { return reviews; }
    public void setReviews(List<ReviewsEntity> reviews) { this.reviews = reviews; }

    public List<WishListsEntity> getWishLists() { return wishLists; }
    public void setWishLists(List<WishListsEntity> wishLists) { this.wishLists = wishLists; }

    public List<UserInfoEntity> getUserInfos() { return userInfos; }
    public void setUserInfos(List<UserInfoEntity> userInfos) { this.userInfos = userInfos; }
}