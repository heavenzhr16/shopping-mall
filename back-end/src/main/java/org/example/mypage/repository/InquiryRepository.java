package org.example.mypage.repository;

import org.example.entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 사용자 문의 Repository
 * 사용자별 문의 목록 조회 기능 제공
 */
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {

  /**
   * 특정 사용자 ID의 문의 목록을 최신순으로 조회
   *
   * @param userId 사용자 ID
   * @return 문의 리스트
   */
  List<InquiryEntity> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
