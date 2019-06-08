package com.example.briefing.controller;

import com.example.briefing.exception.IllegalParameterException;
import com.example.briefing.exception.IncorrectCredentialsException;
import com.example.briefing.model.domain.User;
import com.example.briefing.model.enums.ResultEnum;
import com.example.briefing.model.params.LoginParam;
import com.example.briefing.model.params.MailParam;
import com.example.briefing.model.response.Result;
import com.example.briefing.service.UserService;
import com.example.briefing.utils.JWTUtil;
import com.example.briefing.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IndexController {
  private final UserService userService;
  private final JavaMailSender mailSender;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${spring.mail.username}")
  private String sender;

  @Value("${file.uploadFolder}")
  private String uploadFolder;

  public IndexController(UserService userService, JavaMailSender mailSender) {
    this.userService = userService;
    this.mailSender = mailSender;
  }

  @ApiOperation("登录")
  @PostMapping("/login")
  public Result login(@RequestBody @Valid LoginParam params) {
    String studentId = params.getStudentId();
    String password = params.getPassword();
    User user = userService.getUserByStudentId(studentId);
    String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
    if (user == null || !user.getPassword().equals(md5Password)) {
      throw new IncorrectCredentialsException("用户名或密码错误");
    }
    Map<String, String> resultMap = new HashMap<>(1);
    // 使用自增id存入token可以保护用户的学号信息
    String token = JWTUtil.generateToken(jwtSecret, "id", user.getId().toString());
    resultMap.put("token", token);
    return ResultUtil.success(resultMap);
  }

  @ApiOperation("发送邮件")
  @PostMapping("/mail")
  public Result sendMail(@RequestBody @Valid MailParam params) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(sender);
      helper.setTo(params.getReceiver());
      helper.setSubject(params.getSubject());
      helper.setText(params.getContent(), true);
      mailSender.send(message);
    } catch (MessagingException ex) {
      return ResultUtil.error(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }
    return ResultUtil.success();
  }

  @ApiOperation("上传文件")
  @PostMapping("/file")
  public Result uploadFile(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalParameterException("文件不能为空");
    }
    String filename = file.getOriginalFilename();
    String suffix = filename.substring(filename.lastIndexOf("."));
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_");
    String newFilename = df.format(new Date()) + System.currentTimeMillis() + suffix;
    try {
      Path path = Paths.get(uploadFolder);
      if (Files.notExists(path)) {
        Files.createDirectories(path);
      }
      Files.write(path.resolve(newFilename), file.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
      return ResultUtil.error(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
    Map<String, String> result = new HashMap<>(1);
    result.put("url", contextPath + "/resources/" + newFilename);
    return ResultUtil.success(result);
  }
}
