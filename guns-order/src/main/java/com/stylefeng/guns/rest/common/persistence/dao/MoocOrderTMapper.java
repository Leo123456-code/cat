package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author Leo
 * @since 2020-05-02
 */
@Mapper
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    //
    String getSeatsByFieldId(@Param("fieldId") String fieldId);
    //
    OrderVo getOrderInfoById(@Param("orderId") String orderId);
    //按用户id查找
    List<OrderVo> getOrdersByUserId(@Param("userId") Integer userId, Page<OrderVo> page);
    //按用户id查找
    List<OrderVo> getOrdersByUserIds(@Param("userId") Integer userId);
    //
    String getSoldSeatsByFieldId(@Param("fieldId")Integer fieldId);

}
