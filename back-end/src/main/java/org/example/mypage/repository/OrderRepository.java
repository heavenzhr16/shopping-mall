package org.example.mypage.repository;

import org.example.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 주문 Repository
 * 사용자별 주문 목록 조회 기능 제공
 */
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

  /**
   * 특정 사용자 ID로 주문 목록 조회
   *
   * @param userId 사용자 ID
   * @return 해당 사용자의 주문 리스트
   */
  List<OrdersEntity> findByUserUserId(Long userId);
}
