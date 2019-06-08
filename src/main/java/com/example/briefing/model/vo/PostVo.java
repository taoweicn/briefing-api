package com.example.briefing.model.vo;

import lombok.Data;

@Data
public class PostVo {
  private int totalIssue;

  private int issue;

  private String content;

  private String creator;

  private String modifier;

  private String createTime;

  private String modifyTime;
}
