package org.example.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.mypage.dto.UserUpdateRequest;
import org.example.mypage.service.MyPageUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 마이페이지 - 사용자 정보 수정 컨트롤러
 */
@RestController
@RequestMapping("/api/mypage/user")
@RequiredArgsConstructor
public class MyPageUserController {

  private final MyPageUserService myPageUserService;

  /**
   * 회원 정보 수정
   *
   * @param userId  사용자 ID
   * @param request 수정 요청 DTO
   * @return 성공 메시지
   */
  @PatchMapping("/{userId}/update")
  public ResponseEntity<String> updateUserInfo(
      @PathVariable Long userId,
      @RequestBody UserUpdateRequest request) {

    myPageUserService.updateUserInfo(userId, request);
    return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
  }
}
