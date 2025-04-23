package com.beanmeat.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * t_role
 * @author 
 */
@Data
public class TRole implements Serializable {
    private Integer id;

    private String role;

    private String roleName;

    private static final long serialVersionUID = 1L;
}