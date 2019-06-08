package com.example.briefing.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordParam {
  @NotBlank(message = "新密码不能为空")
  @Size(min = 6, message = "密码长度最少为 {min} 位")
  private String password;
}
