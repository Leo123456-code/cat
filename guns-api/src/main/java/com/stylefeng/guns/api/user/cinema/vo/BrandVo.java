package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: BrandVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:23
 * email 1437665365@qq.com
 */
@Data
public class BrandVo implements Serializable {

    private String brandId;
    private String brandName;
    private boolean isActive;


}
