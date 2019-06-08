package com.example.briefing.aspect;

import com.example.briefing.annotation.UserType;
import com.example.briefing.exception.PermissionException;
import com.example.briefing.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserTypeAspect {
  private final UserService userService;

  public UserTypeAspect(UserService userService) {
    this.userService = userService;
  }

  @Pointcut("@annotation(com.example.briefing.annotation.UserType)")
  public void doVerify() {}

  @Before("doVerify() && @annotation(userType)")
  public void before(UserType userType) {
    if (userService.getSelf().getType() != userType.value().getType()) {
      throw new PermissionException("该用户没有操作权限");
    }
  }
}
