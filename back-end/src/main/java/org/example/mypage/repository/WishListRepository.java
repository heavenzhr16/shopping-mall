package org.example.mypage.repository;

import org.example.entity.WishListsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 찜 목록(WishList) Repository
 * 사용자별 찜한 상품 조회 및 삭제 기능 제공
 */
public interface WishListRepository extends JpaRepository<WishListsEntity, Long> {

  /**
   * 특정 사용자 ID의 찜 목록 전체 조회
   *
   * @param userId 사용자 ID
   * @return 찜한 상품 목록
   */
  List<WishListsEntity> findByUser_UserId(Long userId);

  /**
   * 사용자 ID와 상품 ID로 찜 항목 조회 (중복 여부 확인용 등)
   *
   * @param userId 사용자 ID
   * @param productId 상품 ID
   * @return 해당 사용자의 특정 상품 찜 여부
   */
  Optional<WishListsEntity> findByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
}
