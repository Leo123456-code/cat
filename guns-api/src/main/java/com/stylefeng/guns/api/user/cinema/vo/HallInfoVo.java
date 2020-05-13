package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: HallInfoVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:40
 * email 1437665365@qq.com
 */
@Data
public class HallInfoVo implements Serializable {

    private String hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    //已售座位必须关联订单才能查询
    private String soldSeats;



}
