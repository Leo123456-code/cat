package com.stylefeng.guns.api.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserInfoModel
 * Description: TODO userInfo对象
 * Author: Leo
 * Date: 2020/4/25-13:07
 * email 1437665365@qq.com
 */
@Data
public class UserInfoModel implements Serializable {
    private Integer uuid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private int sex;
    private String birthday;
    private String lifeState;
    private String biography;
    private String address;
    private String headAddress;
    private long beginTime;
    private long updateTime;

}
