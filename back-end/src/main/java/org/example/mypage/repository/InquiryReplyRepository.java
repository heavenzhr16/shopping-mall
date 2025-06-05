package org.example.mypage.repository;

import org.example.entity.InquiryReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 문의 답변 Repository
 * 특정 문의에 대한 답변 목록 조회
 */
public interface InquiryReplyRepository extends JpaRepository<InquiryReplyEntity, Long> {

  /**
   * 특정 문의 ID에 해당하는 답변 목록을 생성일 기준 오름차순으로 조회
   *
   * @param inquiryId 문의 ID
   * @return 해당 문의에 대한 답변 리스트
   */
  List<InquiryReplyEntity> findByInquiry_InquiryIdOrderByCreatedAtAsc(Long inquiryId);
}
