package com.kbz1121.CarStore.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (Pic)实体类
 *
 * @author makejava
 * @since 2022-04-12 08:42:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pic implements Serializable {
    private static final long serialVersionUID = -26936802537730056L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 图片地址
     */
    private String path;
    /**
     * 图片类型,0商品小图，1商品明细图，2其他图
     */
    private Integer type;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 商品id
     */
    private Integer goodsFk;


}
