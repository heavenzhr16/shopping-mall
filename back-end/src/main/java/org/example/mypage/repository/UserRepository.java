package org.example.mypage.repository;

import org.example.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 Repository
 * 기본 CRUD 및 사용자 관련 쿼리 기능 제공
 */
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
  // 필요 시 사용자 ID, 이메일, 로그인 기반 조회 메서드 추가 가능
}
