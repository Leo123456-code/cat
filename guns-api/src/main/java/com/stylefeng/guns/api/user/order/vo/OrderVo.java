package com.stylefeng.guns.api.user.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: OrderVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/2-14:03
 * email 1437665365@qq.com
 */
@Data
public class OrderVo implements Serializable {

    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderTimestamp;
    private String orderStatus;


}
