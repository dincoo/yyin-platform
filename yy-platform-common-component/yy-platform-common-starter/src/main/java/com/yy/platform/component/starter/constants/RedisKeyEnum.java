package com.yy.platform.component.starter.constants;

/**
 * @Auther: zhongdh
 * @Date: 2019/12/30 17:42
 * @Description: 商城用redis的key枚举定义
 */
public enum RedisKeyEnum {

    GOODS_SKU_STOCK_KEY("ME:MALL:GOODS:%s:SKU:%s:STOCK_AMOUNT","商品SKU库存KEY定义"),

    GOODS_SKU_STOCK_INIT_LOCK_KEY("ME:MALL:GOODS:%s:SKU:%s:STOCK_INIT_LOCK","商品SKU库存初始化锁KEY定义"),

    GOODS_SKU_STOCK_DEDUCTION_LOCK_KEY("ME:MALL:GOODS:%s:SKU:%s:STOCK_DEDUCTION_LOCK","商品SKU库存扣减锁KEY定义")
    ;

    /**
     * key值
     */
    private String key;

    /**
     * key描述
     */
    private String desc;

    RedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey(){
        return key;
    }

    public String getDesc(){
        return desc;
    }
}
