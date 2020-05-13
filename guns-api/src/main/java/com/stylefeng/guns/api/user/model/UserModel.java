package com.stylefeng.guns.api.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserModel
 * Description: TODO 注册表单
 * Author: Leo
 * Date: 2020/4/25-13:01
 * email 1437665365@qq.com
 */
@Data
public class UserModel implements Serializable {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}
