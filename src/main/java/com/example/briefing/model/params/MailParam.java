package com.example.briefing.model.params;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailParam {
  @NotBlank(message = "收信邮箱不能为空")
  @Email(message = "邮箱格式错误")
  private String receiver;

  private String subject;

  private String content;
}
