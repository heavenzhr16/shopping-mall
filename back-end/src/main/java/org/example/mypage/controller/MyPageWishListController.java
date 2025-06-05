package org.example.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.mypage.dto.WishListItemDto;
import org.example.mypage.service.MyPageWishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 마이페이지 - 찜한 상품 관리 컨트롤러
 * 찜 목록 조회 및 장바구니 이동 기능 제공
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/wishlist")
public class MyPageWishListController {

  private final MyPageWishlistService wishListService;

  /**
   * 찜한 상품 목록 조회
   *
   * @param userId 사용자 ID
   * @return 찜한 상품 목록
   */
  @GetMapping
  public ResponseEntity<List<WishListItemDto>> getWishList(@RequestParam Long userId) {
    List<WishListItemDto> wishList = wishListService.getWishListByUserId(userId);
    return ResponseEntity.ok(wishList);
  }

  /**
   * 찜한 상품을 장바구니로 이동
   *
   * @param userId    사용자 ID
   * @param productId 상품 ID
   * @return 성공 메시지
   */
  @PostMapping("/move-to-cart")
  public ResponseEntity<String> moveToCart(
      @RequestParam Long userId,
      @RequestParam Long productId) {
    wishListService.moveWishToCart(userId, productId);
    return ResponseEntity.ok("장바구니로 이동 완료");
  }
}
