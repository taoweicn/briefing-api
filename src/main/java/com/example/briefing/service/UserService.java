package com.example.briefing.service;

import com.example.briefing.dao.UserMapper;
import com.example.briefing.exception.NotExistException;
import com.example.briefing.model.domain.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final HttpServletRequest request;

  public UserService(UserMapper userMapper, HttpServletRequest request) {
    this.userMapper = userMapper;
    this.request = request;
  }

  public User getSelf() {
    String id = request.getAttribute("id").toString();
    User user = getUserById(id);
    if (user == null || user.isDeleted()) {
      // isDeleted为true的情况是token仍在有效期内，但用户已被管理员删除
      throw new NotExistException("该用户", "不存在");
    }
    return user;
  }

  public User getUserById(String id) {
    return userMapper.getUserById(id);
  }

  public User getUserByStudentId(String studentId) {
    return userMapper.getUserByStudentId(studentId);
  }

  public List<User> getAllUsers() {
    return userMapper.getAllUsers();
  }

  public void addUser(User user) {
    userMapper.addUser(user);
  }

  public int deleteUser(String studentId) {
    return userMapper.deleteUser(studentId);
  }

  public int updateUser(User user) {
    return userMapper.updateUser(user);
  }

  public int authorizeUser(String studentId, byte type) {
    return userMapper.authorizeUser(studentId, type);
  }

  public int updatePassword(String studentId, String password) {
    return userMapper.updatePassword(studentId, password);
  }
}
