package com.example.briefing.filter;

import com.example.briefing.exception.AuthenticationException;
import com.example.briefing.utils.JWTUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** token校验Filter */
@Component
@WebFilter(urlPatterns = "/*")
@Order(2)
public class JWTFilter implements Filter {
  private static final String AUTH_HEADER = "Authorization";

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("#{'${jwt.excluded-url-patterns}'.split(',')}")
  private List<String> excludedUrlPatterns;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse servletResponse = (HttpServletResponse) response;
    if ("OPTIONS".equals(servletRequest.getMethod())) {
      servletResponse.setStatus(HttpServletResponse.SC_OK);
    } else if (excludedUrlPatterns.stream()
        .noneMatch(s -> servletRequest.getServletPath().matches(s.trim()))) {
      String authHeader = servletRequest.getHeader(AUTH_HEADER);
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new AuthenticationException("Missing or invalid Authorization header");
      }
      String userId = JWTUtil.parseToken(jwtSecret, authHeader.substring(7), "id");
      if (userId == null) {
        throw new AuthenticationException("Invalid token");
      } else {
        // id放到request里以备后用
        servletRequest.setAttribute("id", userId);
      }
    }
    chain.doFilter(request, response);
  }
}
