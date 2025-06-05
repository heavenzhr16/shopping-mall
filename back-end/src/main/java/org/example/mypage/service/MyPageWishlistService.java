package org.example.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.CartEntity;
import org.example.entity.ProductsEntity;
import org.example.entity.WishListsEntity;
import org.example.entity.ProductImagesEntity;
import org.example.exception.CustomException;
import org.example.exception.ErrorCode;
import org.example.mypage.dto.WishListItemDto;
import org.example.mypage.repository.CartRepository;
import org.example.mypage.repository.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 마이페이지 - 위시리스트 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class MyPageWishlistService {

  private final WishListRepository wishListRepository;
  private final CartRepository cartRepository;

  /**
   * 사용자 ID로 찜한 상품 목록 조회
   *
   * @param userId 사용자 ID
   * @return WishListItemDto 리스트
   */
  public List<WishListItemDto> getWishListByUserId(Long userId) {
    List<WishListsEntity> wishLists = wishListRepository.findByUser_UserId(userId);

    return wishLists.stream()
        .map(wish -> {
          ProductsEntity product = wish.getProduct();

          String thumbnailUrl = product.getProductImages().stream()
              .filter(img -> Boolean.TRUE.equals(img.getIsMain()))
              .map(ProductImagesEntity::getImageUrl)
              .findFirst()
              .orElse(null); // 대표 이미지가 없을 수도 있음

          return new WishListItemDto(
              product.getProductId(),
              product.getName(),
              product.getPrice(),
              thumbnailUrl
          );
        })
        .collect(Collectors.toList());
  }

  /**
   * 찜한 상품을 장바구니로 이동
   *
   * @param userId    사용자 ID
   * @param productId 상품 ID
   */
  @Transactional
  public void moveWishToCart(Long userId, Long productId) {
    WishListsEntity wish = wishListRepository.findByUser_UserIdAndProduct_ProductId(userId, productId)
        .orElseThrow(() -> new CustomException(ErrorCode.WISHLIST_NOT_FOUND));

    CartEntity cart = new CartEntity();
    cart.setCartId(System.currentTimeMillis()); // 중복되지 않는 ID로 사용 (UUID도 가능)
    cart.setUserId(userId);
    cart.setUser(wish.getUser());
    cart.setProduct(wish.getProduct());
    cart.setQuantity(1L);
    cart.setCreatedAt(LocalDateTime.now());
    cart.setIsChecked(false);
    cart.setIsActive(true);
    cart.setPriceAtAdd(wish.getProduct().getPrice());
    cart.setOptionInfo(null); // 옵션 정보가 없으면 null

    cartRepository.save(cart);
    wishListRepository.delete(wish);
  }
}
