package org.example.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.mypage.dto.UserInfoResponse;
import org.example.mypage.dto.OrderResponse;
import org.example.mypage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 마이페이지 - 사용자 정보 및 주문 관련 컨트롤러
 * 사용자 정보 조회, 주문 조회, 주문 취소 기능 제공
 */
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

  private final MyPageService myPageService;

  /**
   * 사용자 정보 조회
   *
   * @param userId 사용자 ID
   * @return 사용자 정보 (이름, 이메일 등)
   */
  @GetMapping("/info/{userId}")
  public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long userId) {
    return ResponseEntity.ok(myPageService.getUserInfo(userId));
  }

  /**
   * 사용자 주문 내역 조회
   *
   * @param userId 사용자 ID
   * @return 주문 목록
   */
  @GetMapping("/orders/{userId}")
  public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
    return ResponseEntity.ok(myPageService.getUserOrders(userId));
  }

  /**
   * 주문 취소 요청
   *
   * @param orderId 주문 ID
   * @return 성공 메시지
   */
  @PatchMapping("/orders/{orderId}/cancel")
  public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
    myPageService.cancelOrder(orderId);
    return ResponseEntity.ok("주문 취소 요청이 완료되었습니다.");
  }
}
