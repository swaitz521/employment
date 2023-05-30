package com.employment.employments.controller;

import com.employment.employments.config.redis.RedisService;
import com.employment.employments.entity.Menu;
import com.employment.employments.entity.User;
import com.employment.employments.util.JwtUtils;
import com.employment.employments.util.MenuTree;
import com.employment.employments.util.Result;
import com.employment.employments.vo.RouterVo;
import com.employment.employments.vo.TokenVo;
import com.employment.employments.vo.UserInfo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class SysUserController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisService redisService;
    @PostMapping("refreshToken")
    public Result refreshToken(HttpServletRequest request){
        //从请求头中获取token
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            //如果请求头中没有token，则从参数中获取token
            token=request.getParameter("token");
        }
        //从security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取身份信息
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        //重新生成token
        String reToken="";
        //验证原来的token是否合法
        if(jwtUtils.validateToken(token,userDetails)){
            //刷新token
            reToken=jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间，交给前端做判断
        Long expireTime = Jwts.parser().setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldToken="token_"+token;
        redisService.del(oldToken);
        String newToken="token_"+reToken;
        TokenVo tokenVo=new TokenVo(expireTime,newToken);
        return Result.success(tokenVo).msg("token刷新成功");
    }
      @GetMapping("getInfo")
    public Result getInfo(){
        //从security上下文中获取用户信息
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if(authentication==null){
              return Result.error().msg("用户信息查询失败");
          }
          //获取用户信息
          com.employment.employments.entity.User user = (com.employment.employments.entity.User) authentication.getPrincipal();
          List<Menu> menuList = user.getMenuList();
          //获取角色权限编码字段
          Object[]roles=menuList.stream().filter(Objects::nonNull).map(Menu::getCode).toArray();
          UserInfo userInfo=new UserInfo(String.valueOf(user.getId()),user.getUsername(),user.getAvater(),null,roles);
          return Result.success(userInfo).msg("用户信息查询成功");
    }

    @GetMapping("menuList")

    public Result menuList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        com.employment.employments.entity.User user = (User) authentication.getPrincipal();
        //获取相应的权限
        List<Menu> menuList = user.getMenuList();
        //筛选目录和菜单
        List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("F")).collect(Collectors.toList());
        //生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);
        return Result.success(routerVoList).msg("菜单数据获取成功");
    }
}
