package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UsersEntity {
    // Getters and Setters
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

}