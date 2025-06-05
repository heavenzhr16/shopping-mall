package org.example.exception;

/**
 * 사용자 정의 예외 처리 클래스
 */
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  /**
   * 생성자 - 메시지는 ErrorCode의 메시지를 사용
   */
  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  /**
   * 예외 코드 반환
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
