package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: FilmField
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:35
 * email 1437665365@qq.com
 */
@Data
public class FilmFieldVo implements Serializable {

    private String fieldId;
    private String beginTime;
    private String endTime;
    private String language;
    private String hallName;
    private String price;

}
