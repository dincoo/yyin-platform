package com.yy.platform.component.starter.web.auth.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {
    private Long userId;
    private String userName;
}
