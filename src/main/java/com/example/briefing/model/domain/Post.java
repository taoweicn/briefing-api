package com.example.briefing.model.domain;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class Post {
  @ApiModelProperty(hidden = true)
  private Integer id;

  @ApiModelProperty(value = "总期数")
  @NotNull(message = "总期数不能为空")
  @Positive(message = "总期数必须大于0")
  private Integer totalIssue;

  @ApiModelProperty(value = "期数")
  @NotNull(message = "期数不能为空")
  @Positive(message = "期数必须大于0")
  private Integer issue;

  @ApiModelProperty("简报内容")
  private String content;

  @ApiModelProperty(hidden = true)
  private Integer creator;

  @ApiModelProperty(hidden = true)
  private Integer modifier;

  @ApiModelProperty(hidden = true)
  private String createTime;

  @ApiModelProperty(hidden = true)
  private String modifyTime;

  @ApiModelProperty(hidden = true)
  private boolean isDeleted;
}
