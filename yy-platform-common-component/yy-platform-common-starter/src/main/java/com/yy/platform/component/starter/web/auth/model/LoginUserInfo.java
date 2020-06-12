package com.yy.platform.component.starter.web.auth.model;

import java.io.Serializable;
import java.util.List;

public class LoginUserInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String token;

    private String dept;

    private List<String> roles;

    private List<String> menus;

    private List<String> apiPerms;

    private List<String> dataPerms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public List<String> getApiPerms() {
        return apiPerms;
    }

    public void setApiPerms(List<String> apiPerms) {
        this.apiPerms = apiPerms;
    }

    public List<String> getDataPerms() {
        return dataPerms;
    }

    public void setDataPerms(List<String> dataPerms) {
        this.dataPerms = dataPerms;
    }

    @Override
    public String toString() {
        return "LoginUserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", dept='" + dept + '\'' +
                ", roles=" + roles +
                ", menus=" + menus +
                ", apiPerms=" + apiPerms +
                ", dataPerms=" + dataPerms +
                '}';
    }
}
