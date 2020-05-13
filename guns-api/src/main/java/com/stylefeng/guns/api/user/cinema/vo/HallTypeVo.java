package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: HalltypeVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:27
 * email 1437665365@qq.com
 */
@Data
public class HallTypeVo implements Serializable {

    private String halltypeId;
    private String halltypeName;
    private boolean isActive;

}
