package com.example.briefing.model.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
  /* 普通用户 */
  USER((byte) 1),

  /* 管理员 */
  ADMIN((byte) 2);

  private byte type;

  UserTypeEnum(byte type) {
    this.type = type;
  }
}
