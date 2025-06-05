package org.example.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewRequest {
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;
    private List<String> reviewImages;
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
