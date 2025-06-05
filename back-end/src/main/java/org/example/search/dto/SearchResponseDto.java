package org.example.search.dto;

public class SearchResponseDto {
    private String productName; //상품 이름
    private int price; //상품 가격

    //생성자
    public SearchResponseDto(String productName, int price){
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }





}
