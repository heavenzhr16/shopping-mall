package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "reviewreply")
public class ReviewReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "comment_title", length = 255)
    private String commentTitle;

    @Column(name = "reply_content", length = 255)
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewsEntity review;

    // 기본 생성자
    public ReviewReplyEntity() {}

    // Getters and Setters
    public Long getReplyId() { return replyId; }
    public void setReplyId(Long replyId) { this.replyId = replyId; }

    public String getCommentTitle() { return commentTitle; }
    public void setCommentTitle(String commentTitle) { this.commentTitle = commentTitle; }

    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }

    public ReviewsEntity getReview() { return review; }
    public void setReview(ReviewsEntity review) { this.review = review; }
}