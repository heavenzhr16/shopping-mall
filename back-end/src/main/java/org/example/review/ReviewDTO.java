package org.example.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;
    private String reviewImages;
    private Long productId;
    private Long userId;

}
