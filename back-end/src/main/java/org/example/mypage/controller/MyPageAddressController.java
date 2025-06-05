package org.example.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.mypage.dto.AddressRequestDto;
import org.example.mypage.service.MyPageAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 마이페이지 - 배송지 관리 컨트롤러
 * 사용자의 배송지 추가, 수정, 삭제를 담당
 */
@RestController
@RequestMapping("/api/mypage/user")
@RequiredArgsConstructor
public class MyPageAddressController {

  private final MyPageAddressService myPageAddressService;

  /**
   * 배송지 추가
   *
   * @param userId 사용자 ID
   * @param dto    배송지 정보
   * @return 성공 메시지
   */
  @PostMapping("/{userId}/address")
  public ResponseEntity<String> addAddress(
      @PathVariable Long userId,
      @RequestBody AddressRequestDto dto) {
    myPageAddressService.addAddress(userId, dto);
    return ResponseEntity.ok("배송지가 성공적으로 추가되었습니다.");
  }

  /**
   * 배송지 수정
   *
   * @param userId    사용자 ID
   * @param addressId 배송지 ID
   * @param dto       수정할 배송지 정보
   * @return 성공 메시지
   */
  @PatchMapping("/{userId}/address/{addressId}")
  public ResponseEntity<String> updateAddress(
      @PathVariable Long userId,
      @PathVariable Long addressId,
      @RequestBody AddressRequestDto dto) {
    myPageAddressService.updateAddress(userId, addressId, dto);
    return ResponseEntity.ok("배송지가 성공적으로 수정되었습니다.");
  }

  /**
   * 배송지 삭제
   *
   * @param userId    사용자 ID
   * @param addressId 배송지 ID
   * @return 성공 메시지
   */
  @DeleteMapping("/{userId}/address/{addressId}")
  public ResponseEntity<String> deleteAddress(
      @PathVariable Long userId,
      @PathVariable Long addressId) {
    myPageAddressService.deleteAddress(userId, addressId);
    return ResponseEntity.ok("배송지가 성공적으로 삭제되었습니다.");
  }
}
