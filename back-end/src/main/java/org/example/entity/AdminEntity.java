package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "birthdate", length = 30, nullable = false)
    private String birthdate;

    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;

    @Column(name = "phone_num", nullable = false)
    private Long phoneNum;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "lastlogin_at", nullable = false)
    private LocalDateTime lastLoginAt;

    @Column(name = "approved_by", length = 30, nullable = false)
    private String approvedBy;

    // 기본 생성자
    public AdminEntity() {}

}