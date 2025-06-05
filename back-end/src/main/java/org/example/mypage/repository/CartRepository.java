package org.example.mypage.repository;

import org.example.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 장바구니 Repository
 * 기본 CRUD + 사용자 기반 조회
 */
public interface CartRepository extends JpaRepository<CartEntity, Long> {

  /**
   * 사용자 ID로 장바구니 항목 전체 조회
   *
   * @param userId 사용자 ID
   * @return 해당 사용자의 장바구니 항목 목록
   */
  List<CartEntity> findByUserId(Long userId);
}
