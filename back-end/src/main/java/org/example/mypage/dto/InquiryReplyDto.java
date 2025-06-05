package org.example.mypage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryReplyDto {
  private String replyTitle;
  private String replyContent;
  private LocalDateTime createdAt;
}
