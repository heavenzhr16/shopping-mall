package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reviews")
public class ReviewsEntity {
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

    // Getters and Setters
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

    public String getReviewTitle() { return reviewTitle; }
    public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }

    public String getReviewContent() { return reviewContent; }
    public void setReviewContent(String reviewContent) { this.reviewContent = reviewContent; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewImages() { return reviewImages; }
    public void setReviewImages(String reviewImages) { this.reviewImages = reviewImages; }

    public ProductsEntity getProduct() { return product; }
    public void setProduct(ProductsEntity product) { this.product = product; }

    public UsersEntity getUser() { return user; }
    public void setUser(UsersEntity user) { this.user = user; }

    public List<ReviewReplyEntity> getReviewReplies() { return reviewReplies; }
    public void setReviewReplies(List<ReviewReplyEntity> reviewReplies) { this.reviewReplies = reviewReplies; }
}