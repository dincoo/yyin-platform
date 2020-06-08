package com.yy.platform.component.starter.constants;

/**
 * 订单类型枚举
 */
public enum OrderTypeEnum {

    /**
     * 正常订单
     */
    NORMAL(0),

    /**
     * 其它订单
     */
    OTHER(1);

    private Integer orderType;

    OrderTypeEnum(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderType(){
        return orderType;
    }
}
