package com.yy.platform.component.starter.web.auth.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class ISysUser implements Serializable {
    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名称
     */
    private String username;


}
