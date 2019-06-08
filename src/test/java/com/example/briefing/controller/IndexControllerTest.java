package com.example.briefing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTest {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext wac;

  @Before
  public void setupMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void login() throws Exception {
    Map params = new HashMap<String, String>(2);
    params.put("studentId", "test");
    params.put("password", "password");
    ObjectMapper mapper = new ObjectMapper();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/login")
                .content(mapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("code").value(400))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void sendMail() throws Exception {
    Map<String, String> params = new HashMap<>(3);
    params.put("subject", "test");
    params.put("receiver", "tester@mail.com");
    params.put("content", "This is a test email.");
    ObjectMapper mapper = new ObjectMapper();
    mockMvc
        .perform(
            // 直接测试controller会绕过Filter，因此无需设置token
            MockMvcRequestBuilders.post("/mail")
                .content(mapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
        .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Success"))
        .andDo(MockMvcResultHandlers.print());
  }
}
