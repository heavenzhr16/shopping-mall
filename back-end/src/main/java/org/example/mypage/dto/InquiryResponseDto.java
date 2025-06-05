package org.example.mypage.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InquiryResponseDto {
  private Long inquiryId;
  private String inquiryType;
  private String inquiryTitle;
  private String inquiryContent;
  private Boolean isSecret;
  private Boolean isResponded;
  private LocalDateTime createdAt;

  private String productName; // 필요 시
  private List<InquiryReplyDto> replies;
}
