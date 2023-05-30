package com.employment.employments.config.Filter;

import com.employment.employments.config.exception.CustomerAuthenticationException;
import com.employment.employments.config.redis.RedisConfig;
import com.employment.employments.config.redis.RedisService;
import com.employment.employments.config.security.LoginFailureHandler;
import com.employment.employments.util.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;



public class CheckFilter extends OncePerRequestFilter {
   @Value("${request.login.url}")
   private String loginUrl;
   @Value("/api/user/register")
   private String registerUrl;
   @Value("/api/user/saveUserRole")
   private String roleUrl;
   @Value("/imserver/**")
   private String socket;
   @Autowired
   private LoginFailureHandler loginFailureHandler;
   @Autowired
   private RedisConfig redisConfig;
   @Autowired
   private RedisService redisService;
   @Autowired
   private JwtUtils jwtUtils;
   @Autowired
   private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //如果不是登录请求，则验证token信息
            String requestURI = request.getRequestURI();
            String registerURI = request.getRequestURI();
            String socketURI=request.getRequestURI();
            if(!loginUrl.equals(requestURI) && !registerUrl.equals(registerURI)&&!socket.equals(socketURI)){
                this.checkToken(request);
            }
        }catch (AuthenticationException e){
            loginFailureHandler.onAuthenticationFailure(request,response,e);
        }
        //是就放行
        doFilter(request,response,filterChain);
    }

    private void checkToken(HttpServletRequest request) {
        //从请求头中获取token信息
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            //如果请求头中无token信息，从参数中获取token信息
            token= request.getParameter("token");
        }
        if(Objects.isNull(token)){
            throw new CustomerAuthenticationException("token不存在");
        }
//        从redis中获取token信息
        String tokenKey="token_"+token;
        String s = redisService.get(tokenKey);
        //如果redis中没有token，验证失败
        if(Objects.isNull(s)){
            throw new CustomerAuthenticationException("token已过期");
        }
        //验证redis中token是否一致
        if(!token.equals(s)){
            throw new CustomerAuthenticationException("token验证失败");
        }
        //从token中解析用户信息
        String usernameFromToken = jwtUtils.getUsernameFromToken(token);
        if(Objects.isNull(usernameFromToken)){
            throw new CustomerAuthenticationException("token解析失败");
        }
        //获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        if(userDetails==null){
            throw new CustomerAuthenticationException("token解析失败");
        }
        //创建身份验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //放到security的上下文中
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
