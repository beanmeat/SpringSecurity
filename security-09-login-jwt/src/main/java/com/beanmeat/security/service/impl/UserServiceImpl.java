package com.beanmeat.security.service.impl;

import com.beanmeat.security.entity.TPermission;
import com.beanmeat.security.entity.TUser;
import com.beanmeat.security.mapper.TPermissionMapper;
import com.beanmeat.security.mapper.TUserMapper;
import com.beanmeat.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tchstart
 * @data 2025-04-18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TPermissionMapper tPermissionMapper;


    /**
     * 会在SpringSecurity框架登录的时候被调用
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if(tUser == null){
            throw new UsernameNotFoundException("登录账号不存在");
        }

        // 查询该用户的权限列表
        List<TPermission> tRoleList = tPermissionMapper.selectByUserId(tUser.getId());
        tUser.setTPermissionList(tRoleList);
        return tUser;
    }
}
