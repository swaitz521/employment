package com.employment.employments.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.employment.employments.config.exception.CustomerAuthenticationException;
import com.employment.employments.util.Result;
import com.qiniu.util.Json;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
         response.setContentType("application/json;charset=UTF-8");
         String msg=null;
         Integer code=500;
         if(exception instanceof AccountExpiredException){
             msg="账号过期，请重新登录";
         }else if(exception instanceof BadCredentialsException){
             msg="用户名或密码错误,登录失败！";
         }else if(exception instanceof CredentialsExpiredException){
             msg="密码过期,登录失败！";
         }else if(exception instanceof DisabledException){
             msg="账户被禁用,登录失败！";
         }else if(exception instanceof LockedException){
             msg="账户被锁,登录失败！";
         }else if(exception instanceof InternalAuthenticationServiceException){
             msg="账户不存在,登录失败！";
         }else if(exception instanceof CustomerAuthenticationException){
             msg=exception.getMessage();
             code=600;
         }
        String result= JSON.toJSONString(Result.error().code(code).msg(msg), SerializerFeature.DisableCircularReferenceDetect);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
    }

