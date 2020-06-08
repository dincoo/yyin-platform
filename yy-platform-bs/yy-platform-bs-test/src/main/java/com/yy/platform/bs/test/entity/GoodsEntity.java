package com.yy.platform.bs.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yy.platform.component.starter.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author dincoo
 * @date 2020-03-26 20:51:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("jlx_goods")
public class GoodsEntity extends BaseModel<GoodsEntity> {
    private static final long serialVersionUID=1L;

    /**
     * 分类
     */
    private Integer categoryId;
    /**
     * 商品编码
     */
    private String goodsSn;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 品牌
     */
    private Integer brandId;
    /**
     * 商品数量
     */
    private Integer goodsNumber;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 商品简述
     */
    private String goodsBrief;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 是否销售
     */
    private Integer isOnSale;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 属性分类
     */
    private Integer attributeCategory;
    /**
     * 专柜价格
     */
    private BigDecimal counterPrice;
    /**
     * 附加价格
     */
    private BigDecimal extraPrice;
    /**
     * 是否新品
     */
    private Integer isNew;
    /**
     * 商品单位
     */
    private String goodsUnit;
    /**
     * 商品主图
     */
    private String primaryPicUrl;
    /**
     * 商品列表图
     */
    private String listPicUrl;
    /**
     * 零售价格
     */
    private BigDecimal retailPrice;
    /**
     * 销售量
     */
    private Integer sellVolume;
    /**
     * 主sku　product_id
     */
    private Integer primaryProductId;
    /**
     * 单位价格，单价
     */
    private BigDecimal unitPrice;
    /**
     * 促销描述
     */
    private String promotionDesc;
    /**
     * 促销标签
     */
    private String promotionTag;
    /**
     * APP专享价
     */
    private BigDecimal appExclusivePrice;
    /**
     * 是否是APP专属
     */
    private Integer isAppExclusive;
    /**
     *
     */
    private Integer isLimited;
    /**
     * 是否热门
     */
    private Integer isHot;
    /**
     * 市场价格
     */
    private BigDecimal marketPrice;

    /**
     *
     */
    private Long createUserDeptId;
    /**
     *
     */
    private Integer themeId;
}
