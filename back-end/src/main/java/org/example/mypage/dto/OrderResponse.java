package org.example.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
  private Long orderId;
  private LocalDateTime orderedAt;
  private String status;
  private List<OrderItemResponse> items;
}
