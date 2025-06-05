package org.example.dto;

public class ReviewDto {
    private String reviewTitle;
    private String reviewContent;
    private Integer rating;

    public ReviewDto() {
    }

    public ReviewDto(String reviewTitle, String reviewContent, Integer rating) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public Integer getRating() {
        return rating;
    }
}
