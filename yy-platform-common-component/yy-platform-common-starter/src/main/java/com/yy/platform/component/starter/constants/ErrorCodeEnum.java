package com.yy.platform.component.starter.constants;

/**
 * @Auther: zhongdh
 * @Date: 2019/12/24 14:25
 * @Description: 异常码定义枚举
 */
public enum ErrorCodeEnum {

    SUCCESS("00000", "成功"),
    // 00001-00199为商品模块异常定义范围
    GOODS_SKU_ADVANCE_DEDUCTION_FAIL("00001","商品库存预扣减失败"),
    GOODS_SKU_REVERT_FAIL("00002", "商品库存还原失败"),
    GOODS_SKU_STOCK_INIT_FAIL("00003", "商品库存初始化失败"),
    GOODS_SKU_DO_NOT_EXIST("00004", "商品不存在"),
    GOODS_SKU_STOCK_INIT_LOCK_EXIST("00005", "获取商品库存缓存初始化锁失败"),
    GOODS_SKU_STOCK_DEDUCTION_LOCK_EXIST("00006", "获取数据库商品库存扣减锁失败"),
    GOODS_SKU_DEDUCTION_FAIL("00007","商品库存扣减失败"),
    // 00200-00399为订单模块异常定义范围
    ORDER_CREATE_FAIL("00200","订单创建失败"),
    ORDER_SAVE_FAIL("00201","订单基础信息保存失败"),
    ORDER_ITEM_SAVE_FAIL("00202","订单明细项保存失败"),
    ORDER_ADD_SHOPPINGCART_FAIL("00203", "商品添加购物车失败"),
    ORDER_ADD_RECEIVER_ADDRESS_QUERY_FAIL("00204", "收货地址查询失败"),
    ORDER_ADD_RECEIVER_ADDRESS_FAIL("00205", "添加收货地址失败"),
    // 后面的以此类推定义异常码
    AUTH_TOKEN_EMPTY_FAIL("00900", "token为空"),
    AUTH_TOKEN_ERROR("00901", "token无效")
    ;

    /**
     * 错误编码
     */
    private String code;

    /**
     * 错误码描述
     */
    private String desc;

    ErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }

}
