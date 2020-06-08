package com.yy.platform.component.starter.constants;

/**
 * @Auther: zhongdh
 * @Date: 2019/12/26 11:46
 * @Description: 商品相关公共常量定义
 */
public interface GoodsCommonConstants {

    /**
     * 商品库存扣减操作
     */
    String GOODS_SKU_DEDUCTION_OPT = "1";

    /**
     * 商品库存还原操作
     */
    String GOODS_SKU_REVERT_OPT = "2";

    /**
     * 商品库存未扣减
     */
    int GOODS_SKU_DEDUCTION_STATUS_INIT = 0;

    /**
     * 商品库存扣减失败
     */
    int GOODS_SKU_DEDUCTION_STATUS_FAIL = -1;

    /**
     * 商品库存扣减成功
     */
    int GOODS_SKU_DEDUCTION_STATUS_SUCCESS = 1;

    /**
     * 商品库存未初始化
     */
    int GOODS_SKU_STOCK_UNINIT = -3;

    /**
     * 商品库存不足
     */
    int GOODS_SKU_STOCK_SHORTAGE = -2;

    /**
     * 商品库存不限制
     */
    int GOODS_SKU_STOCK_UNLIMIT = -1;

    /**
     * 商品日志信息输出固定模板
     */
    String GOODS_SKU_LOG_MESSAGE_TEMP = "商品:【%s】,SKU:【%s】,%s";

    /**
     * 扣减数据商品库存主题名称
     */
    String GOODS_SKU_DEDUCTION_DB_TOPIC = "GOODS_SKU_DEDUCTION_TOPIC";


}

