package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Column(name = "review_images", nullable = true)
    private String reviewImages;
    // DB에는 String으로 저장(영속화 대상 제외)
    @Transient
    private List<String> reviewImageList = new ArrayList<>(); // null 방지용 초기화, notnull 이면 그냥 선언만

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


    // 엔티티 로딩 후 문자열 -> 리스트 변환
    @PostLoad
    private void loadImageList(){
        if (reviewImages != null && !this.reviewImages.isBlank()) {
            this.reviewImageList = Arrays.asList(this.reviewImages.split(","));
        } else {
            this.reviewImageList = new ArrayList<>();
        }
    }

    // 저장 전 리스트 -> 문자열 변환
    @PrePersist
    @PreUpdate
    private void saveImageList(){
        if (this.reviewImageList != null && !this.reviewImageList.isEmpty()){
            this.reviewImages = String.join(",", this.reviewImageList);
        } else {
            this.reviewImages = null;
        }
    }

}