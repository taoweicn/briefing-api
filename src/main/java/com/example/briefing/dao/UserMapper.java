package com.example.briefing.dao;

import com.example.briefing.model.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
  /**
   * 根据id获取用户信息
   *
   * @param id 用户id
   * @return 用户信息
   */
  @Select("SELECT * FROM user WHERE id = #{id}")
  User getUserById(String id);

  /**
   * 根据学号获取用户信息
   *
   * @param studentId 学号
   * @return 用户信息
   */
  @Select("SELECT * FROM user WHERE student_id = #{studentId} AND is_deleted = FALSE")
  User getUserByStudentId(String studentId);

  /**
   * 获取所有用户
   *
   * @return 所有用户列表
   */
  @Select(
      "SELECT student_id, username, department, type FROM user WHERE is_deleted = FALSE ORDER BY type DESC")
  List<User> getAllUsers();

  /**
   * 添加用户
   *
   * @param user 用户信息
   * @return 操作结果
   */
  @Insert(
      "INSERT INTO user(student_id, username, department, password) VALUES(#{studentId}, #{username}, #{department}, MD5(#{password}))")
  int addUser(User user);

  /**
   * 根据学号删除用户
   *
   * @param studentId 学号
   * @return 操作结果
   */
  @Delete(
      "UPDATE user SET is_deleted = TRUE WHERE student_id = #{studentId} AND is_deleted = FALSE")
  int deleteUser(String studentId);

  /**
   * 更新用户信息
   *
   * @param user 用户的新信息
   * @return 操作结果
   */
  @Update(
      "UPDATE user SET username = #{username}, department = #{department} WHERE student_id = #{studentId} AND is_deleted = FALSE")
  int updateUser(User user);

  /**
   * 设置用户权限
   *
   * @param studentId 学号
   * @param type 用户权限，1 代表普通用户，2 代表管理员
   * @return 操作结果
   */
  @Update("UPDATE user SET type = #{type} WHERE student_id = #{studentId} AND is_deleted = FALSE")
  int authorizeUser(
      @Param("studentId") String studentId, @Param("type") byte type); // 多个参数时需要@Param注解

  /**
   * 更改用户密码
   *
   * @param studentId 学号
   * @param password 用户密码
   * @return 操作结果
   */
  @Update(
      "UPDATE user SET password = MD5(#{password}) WHERE student_id = #{studentId} AND is_deleted = FALSE")
  int updatePassword(@Param("studentId") String studentId, @Param("password") String password);
}
