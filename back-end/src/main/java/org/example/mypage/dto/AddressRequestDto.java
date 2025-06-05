package org.example.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {
  private Long addressId; // 수정 or 삭제 시 필요
  private String recipientName;
  private String recipientPhone;
  private String postcode;
  private String addressLine1;
  private String addressLine2;
  private String addressAlias;
  private String deliveryMessage;
  private Boolean isDefault;
}
