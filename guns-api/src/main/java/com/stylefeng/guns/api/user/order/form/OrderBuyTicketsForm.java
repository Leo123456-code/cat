package com.stylefeng.guns.api.user.order.form;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: OrderBuyTicketsForm
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/2-13:41
 * email 1437665365@qq.com
 */
@Data
public class OrderBuyTicketsForm implements Serializable {

    private Integer fieldId;

    private String soldSeats;

    private String seatsName;

}
