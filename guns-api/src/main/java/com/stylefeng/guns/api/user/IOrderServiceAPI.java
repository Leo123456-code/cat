package com.stylefeng.guns.api.user;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.order.vo.OrderVo;

import java.util.List;

/**
 * ClassName: IOrderServiceAPI
 * Description: TODO 订单模块接口
 * Author: Leo
 * Date: 2020/5/2-13:06
 * email 1437665365@qq.com
 */
public interface IOrderServiceAPI {
    //验证售出的票是否为真
    boolean isTrueSeats(String fieldId,String seats);
    //已经销售的座位里,有没有这些座位
    boolean isNotSoldSeats(String fieldId,String seats);
    //创建订单信息
    OrderVo saveOrderInfo(Integer fieldId,String soldSeats,String seatsName,String userId);
    //获取当前登录人已经购买的订单
    List<OrderVo> getOrderByUserIds(Integer userId);
    //根据Field 获取所有已经销售的座位
    String getSoldSeatsByFieldId(Integer fieldId);
    //使用当前登录人获取已经购买的订单
    Page<OrderVo> getOrderByUserId(Integer userId,Page<OrderVo> page);

}
