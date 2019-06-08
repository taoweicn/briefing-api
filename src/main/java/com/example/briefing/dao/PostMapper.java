package com.example.briefing.dao;

import com.example.briefing.model.domain.Post;
import com.example.briefing.model.vo.PostVo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper {
  /**
   * 增加一期简报
   *
   * @param post 简报信息
   * @return 操作结果
   */
  @Insert(
      "INSERT INTO post(total_issue, issue, content, creator, modifier, create_time, modify_time) VALUES(#{totalIssue}, #{issue}, #{content}, #{creator}, #{modifier}, #{createTime}, #{modifyTime})")
  int addPost(Post post);

  /**
   * 更新一期简报
   *
   * @param post 简报信息
   * @return 操作结果
   */
  @Update(
      "UPDATE post SET issue = #{issue}, content = #{content}, modifier = #{modifier}, modify_time = #{modifyTime} WHERE total_issue = #{totalIssue} AND is_deleted = FALSE")
  int updatePost(Post post);

  /**
   * 获取所有简报
   *
   * @return 所有简报列表
   */
  @Select(
      "SELECT total_issue, issue, content, u1.username AS creator, u2.username AS modifier, create_time, modify_time FROM post, user u1, user u2 WHERE post.is_deleted = FALSE AND u1.id = post.creator AND u2.id = post.modifier ORDER BY total_issue")
  List<PostVo> getAllPosts();

  /**
   * 删除某一期简报(假删除）
   *
   * @param totalIssue 总期数
   * @return 操作结果
   */
  @Delete(
      "UPDATE post SET is_deleted = TRUE WHERE total_issue = #{totalIssue} AND is_deleted = FALSE")
  int deletePost(int totalIssue);

  /**
   * 获取某一期简报内容
   *
   * @param totalIssue 总期数
   * @return 简报内容
   */
  @Select(
      "SELECT total_issue, issue, content, u1.username AS creator, u2.username AS modifier, create_time, modify_time FROM post, user u1, user u2 WHERE total_issue = #{totalIssue} AND post.is_deleted = FALSE AND u1.id = post.creator AND u2.id = post.modifier")
  PostVo getPost(int totalIssue);

  /**
   * 获取所有总期数
   *
   * @return 总期数列表
   */
  @Select("SELECT total_issue FROM post WHERE is_deleted = FALSE")
  List<Integer> getAllIssues();
}
