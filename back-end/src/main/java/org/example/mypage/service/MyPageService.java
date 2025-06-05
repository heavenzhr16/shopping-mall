package org.example.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.OrderDetailEntity;
import org.example.entity.OrdersEntity;
import org.example.entity.ProductsEntity;
import org.example.entity.UsersEntity;
import org.example.exception.CustomException;
import org.example.exception.ErrorCode;
import org.example.mypage.dto.OrderItemResponse;
import org.example.mypage.dto.OrderResponse;
import org.example.mypage.dto.UserInfoResponse;
import org.example.mypage.repository.OrderDetailRepository;
import org.example.mypage.repository.OrderRepository;
import org.example.mypage.repository.ProductRepository;
import org.example.mypage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 마이페이지 - 회원 정보, 주문 정보 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class MyPageService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  /**
   * 사용자 기본 정보 조회
   */
  public UserInfoResponse getUserInfo(Long userId) {
    return userRepository.findById(userId)
        .map(user -> new UserInfoResponse(
            user.getUsername(),
            user.getEmail(),
            user.getNickname()
        ))
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  /**
   * 사용자 주문 내역 조회
   */
  public List<OrderResponse> getUserOrders(Long userId) {
    List<OrdersEntity> orders = orderRepository.findByUserUserId(userId);

    return orders.stream().map(order -> {
      List<OrderDetailEntity> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());

      List<OrderItemResponse> items = orderDetails.stream().map(detail -> {
        ProductsEntity product = productRepository.findById(detail.getProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return new OrderItemResponse(
            product.getName(),
            detail.getProductCnt(),
            detail.getPrice()
        );
      }).collect(Collectors.toList());

      return new OrderResponse(
          order.getOrderId(),
          order.getOrderedAt(),
          order.getStatus(),
          items
      );
    }).collect(Collectors.toList());
  }

  /**
   * 주문 취소 처리
   */
  @Transactional
  public void cancelOrder(Long orderId) {
    OrdersEntity order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_ERROR));

    order.setStatus("CANCELLED");
    orderRepository.save(order);
  }
}
