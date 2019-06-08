package com.example.briefing.exception;

import com.example.briefing.model.enums.ResultEnum;
import com.example.briefing.model.response.Result;
import com.example.briefing.utils.ResultUtil;
import java.util.List;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
  @ExceptionHandler(NotExistException.class)
  public Result notExistException(NotExistException ex) {
    return ResultUtil.error(ResultEnum.NOT_FOUND.getCode(), ex.getMessage());
  }

  @ExceptionHandler(IncorrectCredentialsException.class)
  public Result incorrectCredentialsException(IncorrectCredentialsException ex) {
    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), ex.getMessage());
  }

  @ExceptionHandler(PermissionException.class)
  public Result permissionException(PermissionException ex) {
    return ResultUtil.error(ResultEnum.FORBIDDEN.getCode(), ex.getMessage());
  }

  @ExceptionHandler(IllegalParameterException.class)
  public Result illegalParameterException(IllegalParameterException ex) {
    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result handleValidationExceptions(MethodArgumentNotValidException ex) {
    StringBuilder errorMsg = new StringBuilder();
    List<ObjectError> errors = ex.getBindingResult().getAllErrors();
    for (ObjectError error : errors) {
      errorMsg.append(error.getDefaultMessage()).append(";");
    }
    return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), errorMsg.toString());
  }

  @ExceptionHandler(Exception.class)
  public Result unknownException(Exception ex) {
    return ResultUtil.error(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
  }
}
