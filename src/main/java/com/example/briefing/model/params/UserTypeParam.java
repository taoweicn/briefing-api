package com.example.briefing.model.params;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserTypeParam {
  @NotNull(message = "类型不能为空")
  @Positive(message = "类型必须是正整数")
  private byte type;
}
