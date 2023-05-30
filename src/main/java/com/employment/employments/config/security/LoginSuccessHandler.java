package com.employment.employments.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.User;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.LoginResult;
import com.employment.employments.util.ResultCode;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisService redisService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        //获取用户信息
        User user=(User) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        long expireTime = Jwts.parser().setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        LoginResult loginResult=new LoginResult(ResultCode.SUCCESS,token,String.valueOf(user.getId()),expireTime);
        ServletOutputStream outputStream = response.getOutputStream();
        String result = JSON.toJSONString(loginResult, SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        String tokenKey="token_"+token;
        redisService.set(tokenKey,token,jwtUtils.getExpiration()/1000);
    }
}
