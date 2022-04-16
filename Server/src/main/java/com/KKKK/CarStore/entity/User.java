package com.kbz1121.CarStore.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-04-13 04:09:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 475603674643413415L;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户类型，0游客，1登录用户，2商家，3管理员
     */
    private Integer type;
    /**
     * 密码，base64简易加密
     */
    private String pass;
    /**
     * 有效表示
     */
    private Integer validFlag;
    /**
     * 审核状态，商家专用，0待审核，1过审
     */
    private Integer stat;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 商家简介明细
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人uid
     */
    private Integer updater;
    /**
     * 收货地址
     */
    private String address;

    @Transient
    private Integer store;

    @Transient
    private String token;
}
