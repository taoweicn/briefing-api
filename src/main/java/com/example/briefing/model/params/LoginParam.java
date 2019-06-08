package com.example.briefing.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginParam {
  @NotBlank(message = "学号不能为空")
  @Size(max = 255, message = "学号字符长度不能超过 {max}")
  private String studentId;

  @NotBlank(message = "密码不能为空")
  @Size(min = 6, message = "密码长度最少为 {min} 位")
  private String password;
}
