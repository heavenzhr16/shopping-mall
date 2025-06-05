package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "reviewreply")
public class ReviewReplyEntity {
    // Getters and Setters
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

}