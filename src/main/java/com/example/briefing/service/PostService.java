package com.example.briefing.service;

import com.example.briefing.dao.PostMapper;
import com.example.briefing.model.domain.Post;
import com.example.briefing.model.domain.User;
import com.example.briefing.model.vo.PostVo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private final PostMapper postMapper;

  public PostService(PostMapper postMapper) {
    this.postMapper = postMapper;
  }

  public void updatePost(Post post, User user) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String now = dateFormat.format(new Date());
    post.setModifyTime(now);
    post.setModifier(user.getId());
    if (getPost(post.getTotalIssue()) != null) {
      postMapper.updatePost(post);
      return;
    }
    post.setCreateTime(now);
    post.setCreator(user.getId());
    postMapper.addPost(post);
  }

  public List<PostVo> getAllPosts() {
    return postMapper.getAllPosts();
  }

  public int deletePost(int totalIssue) {
    return postMapper.deletePost(totalIssue);
  }

  public PostVo getPost(int totalIssue) {
    return postMapper.getPost(totalIssue);
  }

  public List<Integer> getAllIssues() {
    return postMapper.getAllIssues();
  }
}
