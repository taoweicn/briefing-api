package com.example.briefing.controller;

import com.example.briefing.exception.NotExistException;
import com.example.briefing.model.domain.Post;
import com.example.briefing.model.domain.User;
import com.example.briefing.model.response.Result;
import com.example.briefing.model.vo.PostVo;
import com.example.briefing.service.PostService;
import com.example.briefing.service.UserService;
import com.example.briefing.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("简报操作")
@RestController
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;
  private final UserService userService;

  public PostController(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  @ApiOperation("新增或更新简报")
  @PostMapping
  @ResponseBody
  public Result updatePost(@ApiParam("简报信息") @RequestBody @Valid Post post) {
    User user = userService.getSelf();
    postService.updatePost(post, user);
    return ResultUtil.success();
  }

  @ApiOperation("获取所有简报列表")
  @GetMapping
  @ResponseBody
  public Result getAllPosts() {
    return ResultUtil.success(postService.getAllPosts());
  }

  @ApiOperation("删除简报")
  @DeleteMapping("/{totalIssue}")
  @ResponseBody
  public Result deletePost(@ApiParam(value = "总期数", required = true) @PathVariable int totalIssue) {
    int res = postService.deletePost(totalIssue);
    if (res == 0) {
      throw new NotExistException("第" + totalIssue + "期简报", "不存在");
    }
    return ResultUtil.success();
  }

  @ApiOperation("获取某一期简报内容")
  @GetMapping("/{totalIssue}")
  @ResponseBody
  public Result getPost(@ApiParam(value = "总期数", required = true) @PathVariable int totalIssue) {
    PostVo postVo = postService.getPost(totalIssue);
    if (postVo == null) {
      throw new NotExistException("第" + totalIssue + "期简报", "不存在");
    }
    return ResultUtil.success(postVo);
  }

  @ApiOperation("获取所有简报的总期数列表")
  @GetMapping("/issues")
  @ResponseBody
  public Result getAllIssues() {
    return ResultUtil.success(postService.getAllIssues());
  }
}
