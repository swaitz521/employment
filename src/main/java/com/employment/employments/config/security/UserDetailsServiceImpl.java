package com.employment.employments.config.security;

import com.employment.employments.entity.Menu;
import com.employment.employments.entity.User;
import com.employment.employments.service.MenuService;
import com.employment.employments.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByUserName(username);
        if(user==null){
          throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<Menu> menuList = menuService.selectMenuListByUserId(String.valueOf(user.getId()));
        //获取权限编码
        List<String>collect= menuList.stream().filter(Objects::nonNull).map(Menu::getCode).filter(Objects::nonNull).collect(Collectors.toList());
        String[]strings=collect.toArray(new String[collect.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        user.setMenuList(menuList);
        return user;
    }
}
