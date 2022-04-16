package com.kbz1121.CarStore.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * (GoodsItem)实体类
 *
 * @author makejava
 * @since 2022-04-12 18:41:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsItem implements Serializable {
    private static final long serialVersionUID = -83951731573368398L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderFk;
    /**
     * 商品id
     */
    private Integer goodsFk;
    /**
     * 店铺id
     */
    private Integer storeFk;
    /**
     * 购买单价
     */
    private Float price;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 类型，0订单明细，1购物车
     */
    private Integer type;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 用户id
     */
    private Integer userFk;
    /**
     * 数量
     */
    private Integer num;

    @Transient
    private Integer pid;

    @Transient
    private Integer changeType;
}
