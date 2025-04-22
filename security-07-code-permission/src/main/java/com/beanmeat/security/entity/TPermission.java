package com.beanmeat.security.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * t_permission
 * @author 
 */
@Data
public class TPermission implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String url;

    private String type;

    private Integer parentId;

    private Integer orderNo;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单对应要渲染的Vue组件名称
     */
    private String component;

    private static final long serialVersionUID = 1L;
}