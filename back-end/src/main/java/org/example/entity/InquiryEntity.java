package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "inquiry")
public class InquiryEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @Column(name = "inquiry_type", length = 20, nullable = false)
    private String inquiryType;

    @Column(name = "inquiry_title", length = 255, nullable = false)
    private String inquiryTitle;

    @Column(name = "inquiry_content", length = 255, nullable = false)
    private String inquiryContent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_secret", nullable = false)
    private Boolean isSecret;

    @Column(name = "is_responded", nullable = false)
    private Boolean isResponded;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private List<InquiryReplyEntity> inquiryReplies;

    // 기본 생성자
    public InquiryEntity() {}

}