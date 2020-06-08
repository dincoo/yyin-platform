package com.yy.platform.component.starter.constants;

/**
 * 订单状态枚举类
 */
public enum OrderStatusEnum {
    /**
     * 订单待付款
     */
    WAIT_TO_PAY(0),

    /**
     * 订单待发货
     */
    WAIT_TO_DELIVERY(1),

    /**
     * 订单已发货
     */
    HAS_BEEN_DELIVERY(2),

    /**
     * 订单已完成
     */
    HAS_BEEN_DONE(3),

    /**
     * 订单已关闭
     */
    HAS_BEEN_CLOSE(4),

    /**
     * 无效订单
     */
    INVALID(9);

    private Integer status;

    OrderStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatu(){
        return status;
    }
}
