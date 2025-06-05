package org.example.dto;

public class ProductOptionDto {
    private String color;
    private String size;
    private Integer stock;

    public ProductOptionDto() {
    }

    public ProductOptionDto(String color, String size, Integer stock) {
        this.color = color;
        this.size = size;
        this.stock = stock;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public Integer getStock() {
        return stock;
    }
}
