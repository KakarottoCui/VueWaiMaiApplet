package com.kbz1121.CarStore.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (Store)实体类
 *
 * @author makejava
 * @since 2022-04-11 15:00:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store implements Serializable {
    private static final long serialVersionUID = -38029756850918336L;
    /**
     * 店铺id
     */
    private Integer sid;
    /**
     * 店主id
     */
    private Integer userFk;
    /**
     * 店铺名
     */
    private String name;
    /**
     * 审核状态
     */
    private Integer stat;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 创建时间
     */
    private Date createTime;


}
