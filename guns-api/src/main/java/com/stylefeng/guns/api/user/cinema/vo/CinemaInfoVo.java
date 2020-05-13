package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: CinemaInfoVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:29
 * email 1437665365@qq.com
 */
@Data
public class CinemaInfoVo implements Serializable {

    private String cinemaId;
    private String imgUrl;
    private String cinemaName;
    private String cinemaAdress;
    private String cinemaPhone;

}
