package com.kbz1121.CarStore.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (GoodsType)实体类
 *
 * @author makejava
 * @since 2022-04-11 15:00:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsType implements Serializable {
    private static final long serialVersionUID = 622485380884764355L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型，0商品类别
     */
    private Integer type;
    /**
     * 有效标识
     */
    private Integer validFlag;


}
