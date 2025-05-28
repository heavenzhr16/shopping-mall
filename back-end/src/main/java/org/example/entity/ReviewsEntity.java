package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "reviews")
public class ReviewsEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_title", length = 255, nullable = false)
    private String reviewTitle;

    @Column(name = "review_content", length = 255, nullable = false)
    private String reviewContent;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review_images")
    private String reviewImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewReplyEntity> reviewReplies;

    // 기본 생성자
    public ReviewsEntity() {}

}