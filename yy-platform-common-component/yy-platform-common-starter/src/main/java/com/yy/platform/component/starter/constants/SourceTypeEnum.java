package com.yy.platform.component.starter.constants;

/**
 * 订单来源枚举
 */
public enum SourceTypeEnum {

    /**
     * 订单来源于PC端
     */
    FROM_PC(0),
    /**
     * 订单来源于APP端
     */
    FROM_APP(1);

    private Integer sourceType;

    SourceTypeEnum(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getSourceType(){
        return sourceType;
    }
}
