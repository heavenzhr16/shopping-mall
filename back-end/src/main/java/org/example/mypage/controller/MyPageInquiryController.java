package org.example.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.example.mypage.dto.InquiryRequestDto;
import org.example.mypage.dto.InquiryResponseDto;
import org.example.mypage.service.MyPageInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 마이페이지 - 사용자 문의 관리 컨트롤러
 * 문의 등록, 목록 조회, 상세 조회 기능 제공
 */
@RestController
@RequestMapping("/api/mypage/user/{userId}/inquiry")
@RequiredArgsConstructor
public class MyPageInquiryController {

  private final MyPageInquiryService inquiryService;

  /**
   * 사용자 문의 등록
   *
   * @param userId  사용자 ID (경로 변수)
   * @param request 문의 내용 DTO
   * @return 성공 메시지
   */
  @PostMapping
  public ResponseEntity<String> createInquiry(
      @PathVariable Long userId,
      @RequestBody InquiryRequestDto request) {

    request.setUserId(userId); // DTO에 userId 세팅
    inquiryService.createInquiry(request);
    return ResponseEntity.ok("문의가 성공적으로 등록되었습니다.");
  }

  /**
   * 사용자 문의 목록 조회
   *
   * @param userId 사용자 ID
   * @return 본인이 등록한 문의 목록
   */
  @GetMapping
  public ResponseEntity<List<InquiryResponseDto>> getMyInquiries(@PathVariable Long userId) {
    List<InquiryResponseDto> inquiries = inquiryService.getMyInquiries(userId);
    return ResponseEntity.ok(inquiries);
  }

  /**
   * 특정 문의 상세 조회
   * 본인의 문의만 조회 가능 (내부에서 권한 검사)
   *
   * @param userId    사용자 ID
   * @param inquiryId 문의 ID
   * @return 문의 상세 내용
   */
  @GetMapping("/{inquiryId}")
  public ResponseEntity<InquiryResponseDto> getInquiryDetail(
      @PathVariable Long userId,
      @PathVariable Long inquiryId) {
    InquiryResponseDto dto = inquiryService.getInquiryDetail(userId, inquiryId);
    return ResponseEntity.ok(dto);
  }
}
