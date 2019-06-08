package com.example.briefing.service;

import com.example.briefing.model.domain.Post;
import com.example.briefing.model.domain.User;
import com.example.briefing.model.vo.PostVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
  @Autowired private PostService postService;

  @Test
  public void getPost() {
    PostVo postVo = postService.getPost(1);
    Assert.assertEquals(postVo.getTotalIssue(), 1);
  }

  @Test
  @Transactional // 使用这条注解可自动回滚数据
  public void updatePost() {
    Post post = new Post();
    User user = new User();
    user.setUsername("tester");
    post.setTotalIssue(999);
    post.setIssue(999);
    post.setContent("{\"hello\": \"world\"}");
    postService.updatePost(post, user);
  }
}
