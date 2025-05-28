package org.example.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiryreply")
public class InquiryReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "reply_title", nullable = false)
    private String replyTitle;

    @Column(name = "reply_content", nullable = false)
    private String replyContent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private InquiryEntity inquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    // 기본 생성자
    public InquiryReplyEntity() {}

    // Getters and Setters
    public Long getReplyId() { return replyId; }
    public void setReplyId(Long replyId) { this.replyId = replyId; }

    public String getReplyTitle() { return replyTitle; }
    public void setReplyTitle(String replyTitle) { this.replyTitle = replyTitle; }

    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public InquiryEntity getInquiry() { return inquiry; }
    public void setInquiry(InquiryEntity inquiry) { this.inquiry = inquiry; }

    public UsersEntity getUser() { return user; }
    public void setUser(UsersEntity user) { this.user = user; }
}