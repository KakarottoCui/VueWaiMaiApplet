package com.kbz1121.CarStore.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * (Orders)实体类
 *
 * @author makejava
 * @since 2022-04-13 06:54:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 655347405316571099L;
    /**
     * 订单号
     */
    private Integer id;
    /**
     * 买家id
     */
    private Integer userFk;
    /**
     * 店铺id
     */
    private Integer storeFk;
    /**
     * 状态，0未支付，1已支付，2已发货，3已结束
     */
    private Integer stat;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货人电话
     */
    private String phone;
    /**
     * 收货人姓名
     */
    private String userName;
    /**
     * 订单总价
     */
    private Float priceAll;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间，订单结束时间
     */
    private Date updateTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 备注
     */
    private String memo;

    @Transient
    private Integer type;

    @Transient
    private List<Goods> goods;

}
