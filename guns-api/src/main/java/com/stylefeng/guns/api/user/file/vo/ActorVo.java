package com.stylefeng.guns.api.user.file.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: ActorVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-22:32
 * email 1437665365@qq.com
 */
@Data
public class ActorVo implements Serializable {

    private String imgAddress;

    private String directorName;

    private String roleName;
}
