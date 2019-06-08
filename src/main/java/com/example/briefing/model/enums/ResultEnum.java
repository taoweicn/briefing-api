package com.example.briefing.model.enums;

import lombok.Getter;

/** 预定义的响应状态 */
@Getter
public enum ResultEnum {
  /* 请求成功 */
  SUCCESS(200, "Success"),

  /* 参数不合法 */
  BAD_REQUEST(400, "Invalid Param"),

  /* 未授权 */
  UNAUTHORIZED(401, "UNAUTHORIZED"),

  /* 权限不足 */
  FORBIDDEN(403, "FORBIDDEN"),

  /* 资源不存在 */
  NOT_FOUND(404, "Not Found"),

  /* 服务器内部错误（未知错误） */
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");

  private int code;
  private String msg;

  ResultEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
