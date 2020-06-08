package com.yy.platform.component.starter.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseModel<T> implements Serializable{
    @TableId(value="id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Long updateBy;
    /**
     *
     */
    @TableField(value="enable_")
    private Integer enable;

}
