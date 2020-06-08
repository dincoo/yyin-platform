package com.yy.platform.component.starter.constants;

/**
 * 支付类型
 */
public enum PayTypeEnum {
    /**
     * 未支付
     */
    UNPAY(0),

    /**
     * 支付宝
     */
    ALI_PAY(1),

    /**
     * 微信支付
     */
    WECHAT_PAY(2);

    private Integer type;

    PayTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getPayType(){
        return type;
    }
}
