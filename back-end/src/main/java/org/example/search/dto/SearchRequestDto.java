package org.example.search.dto;

import java.util.List;

public class SearchRequestDto {
    private String keyword; // 사용자가 입력한 검색어
    private Long minPrice; // 최소 가격
    private Long maxPrice; // 최대 가격
    private Long categoryId; // 카테고리 아이디
    private List<Long> colorIds; //색상 아이디 리스트

    public String getKeyword(){
        return keyword;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    public Long getMinPrice(){
        return minPrice;
    }

    public void setMinPrice(Long minPrice){
        this.minPrice = minPrice;
    }

    public Long getMaxPrice(){
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice){
        this.maxPrice = maxPrice;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public List<Long> getColorIds(){
        return colorIds;
    }

    public void setColorIds(List<Long> colorIds){
        this.colorIds = colorIds;
    }

}
