package com.example.briefing.utils;

import com.example.briefing.model.enums.ResultEnum;
import com.example.briefing.model.response.Result;

public class ResultUtil {

  public static Result success() {
    return success(null);
  }

  @SuppressWarnings("unchecked")
  public static <T> Result success(T data) {
    Result result = new Result();
    result.setCode(ResultEnum.SUCCESS.getCode());
    result.setMsg(ResultEnum.SUCCESS.getMsg());
    result.setData(data);
    return result;
  }

  public static Result error(int code, String errorMsg) {
    Result result = new Result();
    result.setCode(code);
    result.setMsg(errorMsg);
    return result;
  }
}
