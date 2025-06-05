package org.example.exception;

/**
 * 전역 예외 코드 정의
 */
public enum ErrorCode {

  // ✅ 공통 에러
  INTERNAL_ERROR(500, "INTERNAL_ERROR", "알 수 없는 오류가 발생했습니다."),

  // ✅ 사용자 관련 에러
  USER_NOT_FOUND(404, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),

  // ✅ 배송지 관련 에러
  ADDRESS_NOT_FOUND(404, "ADDRESS_NOT_FOUND", "해당 배송지를 찾을 수 없습니다."),
  ADDRESS_DELETE_REJECTED(400, "ADDRESS_DELETE_REJECTED", "배송지는 최소 1개 이상 등록되어 있어야 합니다."),

  // ✅ 상품 관련 에러
  PRODUCT_NOT_FOUND(404, "PRODUCT_NOT_FOUND", "해당 상품을 찾을 수 없습니다."),

  // ✅ 찜하기(Wishlist) 관련 에러
  WISHLIST_NOT_FOUND(404, "WISHLIST_NOT_FOUND", "찜한 상품을 찾을 수 없습니다."),

  // ✅ 문의(Inquiry) 관련 에러
  INQUIRY_NOT_FOUND(404, "INQUIRY_NOT_FOUND", "해당 문의를 찾을 수 없습니다."),
  INQUIRY_ACCESS_DENIED(403, "INQUIRY_ACCESS_DENIED", "본인의 문의만 조회할 수 있습니다.");

  // --------------------------
  // 필드 및 생성자
  // --------------------------

  private final int status;
  private final String code;
  private final String message;

  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  // --------------------------
  // Getter 메서드
  // --------------------------

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
