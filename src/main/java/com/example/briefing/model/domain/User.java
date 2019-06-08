package com.example.briefing.model.domain;

import com.example.briefing.model.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
  @ApiModelProperty(hidden = true)
  private Integer id;

  @ApiModelProperty("学号")
  @NotBlank(message = "学号不能为空")
  @Size(max = 255, message = "学号字符长度不能超过 {max}")
  private String studentId;

  @ApiModelProperty("用户名")
  @NotBlank(message = "用户名不能为空")
  @Size(max = 255, message = "用户名字符长度不能超过 {max}")
  private String username;

  @ApiModelProperty("部门")
  @NotBlank(message = "部门不能为空")
  @Size(max = 255, message = "部门字符长度不能超过 {max}")
  private String department;

  private byte type = UserTypeEnum.USER.getType();

  @ApiModelProperty("密码")
  @NotBlank(message = "密码不能为空")
  @Size(min = 6, message = "密码最少为 {min} 位")
  private String password;

  @ApiModelProperty(hidden = true)
  private boolean isDeleted;
}
