package com.yy.platform.system.management.vo;

import java.util.List;

public class UserMenuVo {

    private List<AsidebarMenuVo> asider;//目录，菜单

    private List<AsidebarMenuVo> module;//模块

    public List<AsidebarMenuVo> getAsider() {
        return asider;
    }

    public void setAsider(List<AsidebarMenuVo> asider) {
        this.asider = asider;
    }

    public List<AsidebarMenuVo> getModule() {
        return module;
    }

    public void setModule(List<AsidebarMenuVo> module) {
        this.module = module;
    }
}
