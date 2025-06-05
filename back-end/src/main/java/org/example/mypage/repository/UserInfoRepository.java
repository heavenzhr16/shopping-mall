package org.example.mypage.repository;

import org.example.entity.UserInfoEntity;
import org.example.entity.UserInfoEntity.UserInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 배송지 정보 Repository
 * 복합키 기반으로 사용자별 배송지 관리
 */
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, UserInfoId> {

  /**
   * 사용자 ID로 등록된 모든 배송지 조회
   *
   * @param userId 사용자 ID
   * @return 배송지 목록
   */
  List<UserInfoEntity> findAllByUserId(Long userId);

  /**
   * 사용자 ID로 기본 배송지 조회
   *
   * @param userId 사용자 ID
   * @return 기본 배송지 (있으면 Optional로 반환)
   */
  Optional<UserInfoEntity> findByUserIdAndIsDefaultTrue(Long userId);
}
