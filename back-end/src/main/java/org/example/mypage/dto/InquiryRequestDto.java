package org.example.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequestDto {
  private Long productId;
  private Long userId; // 요청 시 포함하거나, 컨트롤러에서 로그인 정보로 설정
  private String inquiryType;
  private String inquiryTitle;
  private String inquiryContent;
  private Boolean isSecret;
  private String name;
  private String email;
  private String password; // 비회원 문의일 경우 사용 가능
}
