package com.kbz1121.CarStore.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (UserLogin)实体类
 *
 * @author makejava
 * @since 2022-04-11 15:00:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin implements Serializable {
    private static final long serialVersionUID = 875473880896768112L;
    /**
     * token
     */
    private String token;
    /**
     * 用户id
     */
    private Integer userFk;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 用户类型
     */
    private Integer type;


}
