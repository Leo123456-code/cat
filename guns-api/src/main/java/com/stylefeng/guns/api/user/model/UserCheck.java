package com.stylefeng.guns.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: UserCheck
 * Description: TODO 验证用户表单
 * Author: Leo
 * Date: 2020/4/25-22:01
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCheck implements Serializable {

    private String username;

}
