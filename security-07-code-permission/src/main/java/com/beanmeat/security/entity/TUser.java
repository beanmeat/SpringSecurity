package com.beanmeat.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * t_user
 * @author 
 */
@Data
public class TUser implements UserDetails,Serializable {
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    @JsonIgnore // 该字段不返回，该字段不参与json转换
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;

    /**
     * 创建时间
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;

    /**
     * 用户的角色List
     */
    @JsonIgnore
    private List<TRole> tRoleList;

    /**
     * 用户的权限代码List
     */
    @JsonIgnore
    private List<TPermission> tPermissionList;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (TPermission permission : this.tPermissionList) {
            // 放入权限（权限标识符，权限code，权限代码）
            authorities.add(new SimpleGrantedAuthority(permission.getCode()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPwd;
    }

    @Override
    public String getUsername() {
        return loginAct;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNoExpired == 1;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}