package org.example.mypage.repository;

import org.example.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 주문 상세 정보 Repository
 * 주문 ID로 주문 상세 항목 조회 기능 제공
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

  /**
   * 특정 주문 ID에 해당하는 모든 주문 상세 항목 조회
   *
   * @param orderId 주문 ID
   * @return 주문 상세 항목 리스트
   */
  List<OrderDetailEntity> findByOrderId(Long orderId);
}
