package com.kbz1121.CarStore.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (PicItem)实体类
 *
 * @author makejava
 * @since 2022-04-11 15:00:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicItem implements Serializable {
    private static final long serialVersionUID = -23031972196873951L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品id
     */
    private Integer goodsFk;
    /**
     * 类型，0小图，1详情图
     */
    private Integer type;
    /**
     * 有效标识
     */
    private Integer validFlag;


}
