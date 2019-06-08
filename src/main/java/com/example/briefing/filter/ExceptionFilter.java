package com.example.briefing.filter;

import com.example.briefing.exception.AuthenticationException;
import com.example.briefing.model.enums.ResultEnum;
import com.example.briefing.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 捕获异常的filter，需第一个执行 Order注解是从小到大执行，默认是Integer.MAX_VALUE，即最低优先级 */
@Component
@WebFilter(urlPatterns = "/*")
@Order(1)
public class ExceptionFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (AuthenticationException ex) {
      HttpServletResponse servletResponse = (HttpServletResponse) response;
      servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      servletResponse.setContentType("application/json");
      servletResponse.setCharacterEncoding("UTF-8");
      ObjectMapper mapper = new ObjectMapper();
      String result =
          mapper.writeValueAsString(
              ResultUtil.error(ResultEnum.UNAUTHORIZED.getCode(), ex.getMessage()));
      servletResponse.getWriter().write(result);
    }
  }
}
