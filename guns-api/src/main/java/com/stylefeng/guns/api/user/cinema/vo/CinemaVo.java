package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * ClassName: CinemaVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:20
 * email 1437665365@qq.com
 */
@Data
public class CinemaVo implements Serializable {

    private String uuid;
    private String cinemaName;
    private String address;
    private String minimumPrice;



}
