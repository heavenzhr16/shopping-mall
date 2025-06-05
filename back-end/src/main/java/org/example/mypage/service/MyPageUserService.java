package org.example.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UsersEntity;
import org.example.exception.CustomException;
import org.example.exception.ErrorCode;
import org.example.mypage.dto.UserUpdateRequest;
import org.example.mypage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 마이페이지 - 회원 정보 수정 서비스
 */
@Service
@RequiredArgsConstructor
public class MyPageUserService {

  private final UserRepository userRepository;

  /**
   * 사용자 정보 수정
   *
   * @param userId  수정할 사용자 ID
   * @param request 수정 요청 DTO
   */
  @Transactional
  public void updateUserInfo(Long userId, UserUpdateRequest request) {
    UsersEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setNickname(request.getNickname());
    user.setPhoneNum(request.getPhoneNum());
  }
}
