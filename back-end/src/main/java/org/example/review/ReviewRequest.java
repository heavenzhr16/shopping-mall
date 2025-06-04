package org.example.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long reviewId; // 수정, 삭제 시 사용, 생성 시 null
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;
    private String reviewImages;
    private Long productId;
    private Long userId;

    /*
    * public ReviewDto toDto() {
        ReviewDto dto = new ReviewDto();
        dto.setReviewId(this.reviewId);
        dto.setReviewTitle(this.reviewTitle);
        dto.setReviewContent(this.reviewContent);
        dto.setRating(this.rating);
        dto.setReviewImages(this.reviewImages);
        dto.setProductId(this.productId);
        dto.setUserId(this.userId);
        return dto;
    }
    * */
}
