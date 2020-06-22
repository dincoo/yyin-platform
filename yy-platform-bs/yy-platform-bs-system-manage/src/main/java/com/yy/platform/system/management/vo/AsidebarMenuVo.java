package com.yy.platform.system.management.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AsidebarMenuVo implements Serializable {
    private String id;
    private String name;
    private String url;
    private String indexNum;
    private String iconClass;
    private String parentId;
    private List<ChildMenuVo> children = new ArrayList<>(0);

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public List<ChildMenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildMenuVo> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
