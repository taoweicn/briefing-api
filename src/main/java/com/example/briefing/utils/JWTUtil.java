package com.example.briefing.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JWTUtil {
  private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

  public static String generateToken(String jwtSecret, String fieldName, String payload) {
    Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    return JWT.create().withClaim(fieldName, payload).withExpiresAt(expireDate).sign(algorithm);
  }

  public static String parseToken(String jwtSecret, String token, String fieldName) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return jwt.getClaim(fieldName).asString();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }
}
