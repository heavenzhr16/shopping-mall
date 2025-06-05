package org.example.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {
  private String productName;
  private Long quantity;
  private Long price;
}
