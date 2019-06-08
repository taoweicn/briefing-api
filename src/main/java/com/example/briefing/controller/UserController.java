package com.example.briefing.controller;

import com.example.briefing.annotation.UserType;
import com.example.briefing.exception.IllegalParameterException;
import com.example.briefing.exception.NotExistException;
import com.example.briefing.model.domain.User;
import com.example.briefing.model.enums.ResultEnum;
import com.example.briefing.model.enums.UserTypeEnum;
import com.example.briefing.model.params.PasswordParam;
import com.example.briefing.model.params.UserTypeParam;
import com.example.briefing.model.response.Result;
import com.example.briefing.model.vo.UserVo;
import com.example.briefing.service.UserService;
import com.example.briefing.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api("用户操作")
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ApiOperation("获取所有用户")
  @GetMapping
  @ResponseBody
  @UserType(UserTypeEnum.ADMIN)
  public Result getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserVo> userVos = users.stream().map(this::convertToVo).collect(Collectors.toList());
    return ResultUtil.success(userVos);
  }

  @ApiOperation("根据学号获取用户信息")
  @GetMapping("/{studentId}")
  @ResponseBody
  public Result getUserByStudentId(
      @ApiParam(value = "学号", required = true) @PathVariable String studentId) {
    UserVo userVo = convertToVo(userService.getUserByStudentId(studentId));
    if (userVo == null) {
      throw new NotExistException("用户" + studentId, "不存在");
    }
    return ResultUtil.success(userVo);
  }

  @ApiOperation("添加用户")
  @PostMapping
  @ResponseBody
  @UserType(UserTypeEnum.ADMIN)
  public Result addUser(@ApiParam("用户信息") @RequestBody @Valid User user) {
    // 假删除的原因，所以不能使用DuplicateKeyException来判断
    if (userService.getUserByStudentId(user.getStudentId()) != null) {
      return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "用户" + user.getId() + " 已存在");
    }
    userService.addUser(user);
    return ResultUtil.success();
  }

  @ApiOperation("删除用户")
  @DeleteMapping("/{studentId}")
  @ResponseBody
  @UserType(UserTypeEnum.ADMIN)
  public Result deleteUser(
      @ApiParam(value = "学号", required = true) @PathVariable String studentId) {
    int res = userService.deleteUser(studentId);
    if (res == 0) {
      throw new NotExistException("用户" + studentId, "不存在");
    }
    return ResultUtil.success();
  }

  @ApiOperation("修改用户信息")
  @PutMapping("/{studentId}")
  @ResponseBody
  @UserType(UserTypeEnum.ADMIN)
  public Result updateUser(
      @ApiParam(value = "学号", required = true) @PathVariable String studentId,
      @ApiParam(value = "用户信息", required = true) @RequestBody @Valid User user) {
    user.setStudentId(studentId);
    int res = userService.updateUser(user);
    if (res == 0) {
      throw new NotExistException("用户" + studentId, "不存在");
    }
    return ResultUtil.success();
  }

  @ApiOperation("修改用户角色")
  @PatchMapping("/{studentId}/type")
  @ResponseBody
  @UserType(UserTypeEnum.ADMIN)
  public Result authorizeUser(
      @ApiParam(value = "学号", required = true) @PathVariable String studentId,
      @ApiParam(value = "用户类型", required = true) @RequestBody @Valid UserTypeParam param) {
    int res = userService.authorizeUser(studentId, param.getType());
    if (res == 0) {
      throw new NotExistException("用户" + studentId, "不存在");
    }
    return ResultUtil.success();
  }

  @ApiOperation("修改密码")
  @PatchMapping("/password")
  @ResponseBody
  public Result updatePassword(
      @ApiParam(value = "新密码", required = true) @RequestBody @Valid PasswordParam param) {
    // 只允许自己更改自己的密码
    String studentId = userService.getSelf().getStudentId();
    String password = param.getPassword();
    final int passwordMinLength = 6;
    if (password == null || password.length() < passwordMinLength) {
      throw new IllegalParameterException("参数不合法");
    }
    userService.updatePassword(studentId, password);
    return ResultUtil.success();
  }

  @ApiIgnore
  private UserVo convertToVo(User user) {
    if (user == null) {
      return null;
    }
    UserVo userVo = new UserVo();
    BeanUtils.copyProperties(user, userVo);
    return userVo;
  }
}
