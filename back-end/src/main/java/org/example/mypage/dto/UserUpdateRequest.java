package org.example.mypage.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

  private String username;

  private String email;

  private String password;

  private String nickname;

  @JsonProperty("phone_num")
  private String phoneNum;
}

