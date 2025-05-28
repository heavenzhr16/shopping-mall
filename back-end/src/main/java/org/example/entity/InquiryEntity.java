package org.example.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inquiry")
public class InquiryEntity {
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

    // Getters and Setters
    public Long getInquiryId() { return inquiryId; }
    public void setInquiryId(Long inquiryId) { this.inquiryId = inquiryId; }

    public String getInquiryType() { return inquiryType; }
    public void setInquiryType(String inquiryType) { this.inquiryType = inquiryType; }

    public String getInquiryTitle() { return inquiryTitle; }
    public void setInquiryTitle(String inquiryTitle) { this.inquiryTitle = inquiryTitle; }

    public String getInquiryContent() { return inquiryContent; }
    public void setInquiryContent(String inquiryContent) { this.inquiryContent = inquiryContent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsSecret() { return isSecret; }
    public void setIsSecret(Boolean isSecret) { this.isSecret = isSecret; }

    public Boolean getIsResponded() { return isResponded; }
    public void setIsResponded(Boolean isResponded) { this.isResponded = isResponded; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UsersEntity getUser() { return user; }
    public void setUser(UsersEntity user) { this.user = user; }

    public ProductsEntity getProduct() { return product; }
    public void setProduct(ProductsEntity product) { this.product = product; }

    public List<InquiryReplyEntity> getInquiryReplies() { return inquiryReplies; }
    public void setInquiryReplies(List<InquiryReplyEntity> inquiryReplies) { this.inquiryReplies = inquiryReplies; }
}