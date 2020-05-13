package com.stylefeng.guns.rest.order.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.ICinemaServiceAPI;
import com.stylefeng.guns.api.user.IOrderServiceAPI;
import com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.user.order.vo.OrderQueryVo;
import com.stylefeng.guns.api.user.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import com.stylefeng.guns.rest.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: OrderServiceimpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/2-14:12
 * email 1437665365@qq.com
 */
@Component
@Service(interfaceClass = IOrderServiceAPI.class)
@Slf4j
public class OrderServiceimpl implements IOrderServiceAPI{
    //订单信息表 Mapper 接口
    @Autowired
    private MoocOrderTMapper moocOrderTMapper;
    @Autowired
    private FTPUtil ftpUtil;
    //内部调用
    @Reference(interfaceClass = ICinemaServiceAPI.class,check = false)
    private ICinemaServiceAPI cinemaServiceAPI;
    
    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        /**
         * @Description //TODO 验证售出的票是否为真
           @Author Leo
         * @Date 14:14 2020/5/2
         * @Param [fieldId, seats]
         * @return boolean
        */
        String seatPath = moocOrderTMapper.getSeatsByFieldId(fieldId);
        //读取位置图,判断seats是否为真
        log.info("seatPath={}",seatPath);
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatPath);
        //将fileStrByAddress 转换为 JSON对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();

        String[] seatArrs = seats.split(",");
        String[] idArrs = ids.split(",");
        int isTure = 0;

        for (String id : idArrs) {
            for (String seat : seatArrs) {
               if(seat.equalsIgnoreCase(id)){
                   isTure++;
               }
            }
        }
        //如果匹配上的数量与座位一致,表示全部匹配上了
        if(seatArrs.length == isTure){
            return true;
        }

        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        /**
         * @Description //TODO 已经销售的座位里,有没有这些座位
           @Author Leo
         * @Date 14:14 2020/5/2
         * @Param [fieldId, seats]
         * @return boolean
        */
        EntityWrapper entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("field_id",fieldId);
        List<MoocOrderT> list = moocOrderTMapper.selectList(entityWrapper);

        String[] seatArrs = seats.split(",");
        //有任何编号匹配上,则直接返回失败
        for (MoocOrderT moocOrderT : list) {
            String[] ids = moocOrderT.getSeatsIds().split(",");
            for (String id : ids) {
                for (String seat : seatArrs) {
                    if(id.equalsIgnoreCase(seat)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public OrderVo saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, String userId) {
        /**
         * @Description //TODO 创建订单信息
           @Author Leo
         * @Date 14:14 2020/5/2
         * @Param [fieldId, soldSeats, seatsName, userId]
         * @return com.stylefeng.guns.api.user.order.vo.OrderVo
        */
        String uuid = UUIDUtil.genUUid();
        //影片信息
        FilmInfoVo filmInfoVo = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        Integer filmId =Integer.parseInt(filmInfoVo.getFilmId()) ;
        //获取影院信息
        OrderQueryVo orderQueryVo = cinemaServiceAPI.getOrderNeeds(fieldId);
        int cinemaId = Integer.parseInt(orderQueryVo.getCinemaId());
        double filmPrice = Double.parseDouble(orderQueryVo.getFilmPrice());
        //求订单总金额
        int solds = soldSeats.split(",").length;//票数
        double totalPrice = getTotalPrice(solds, filmPrice);

        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(uuid);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setOrderUser(Integer.parseInt(userId));
        moocOrderT.setOrderTime(new Date());
        moocOrderT.setOrderStatus(0);
        moocOrderT.setOrderPrice(totalPrice);
        moocOrderT.setFilmPrice(filmPrice);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setCinemaId(cinemaId);

        Integer insert = moocOrderTMapper.insert(moocOrderT);
        if(insert > 0){
            //返回查询结果
            OrderVo orderVo = moocOrderTMapper.getOrderInfoById(uuid);
            if(orderVo == null || orderVo.getOrderId() == null){
                log.error("订单信息查询失败",uuid);
                return null;
            }else {
                return orderVo;
            }

        }else {
            log.error("订单插入失败");
            return null;
        }

    }

    private static double getTotalPrice(int solds,double filmPrice){
        BigDecimal soldsDeci = new BigDecimal(solds);
        BigDecimal filmPriceDeci = new BigDecimal(filmPrice);
        //乘法
        BigDecimal result = soldsDeci.multiply(filmPriceDeci);
        //两位小数,四舍五入
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        double totalPrice = getTotalPrice(2, 13.255);
        System.out.println(totalPrice);

    }

    @Override
    public List<OrderVo> getOrderByUserIds(Integer userId) {
        /**
         * @Description //TODO 获取当前登录人已经购买的订单
           @Author Leo
         * @Date 14:15 2020/5/2
         * @Param [userId]
         * @return java.util.List<com.stylefeng.guns.api.user.order.vo.OrderVo>
        */
        if(userId == null){
            log.error("订单查询业务失败,用户编号未传入");
            return null;
        }
        List<OrderVo> orderVoList = moocOrderTMapper.getOrdersByUserIds(userId);
        if(orderVoList == null && orderVoList.size() == 0){
            return new ArrayList<>();
        }

        return orderVoList;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        /**
         * @Description //TODO 根据Field 获取所有已经销售的座位
           @Author Leo
         * @Date 14:17 2020/5/2
         * @Param [fieldId]
         * @return java.lang.String
        */
        if(fieldId == null){
          log.error("查询已售座位错误,未传入任何场次编号");
          return "";
        }
        String seats = moocOrderTMapper.getSoldSeatsByFieldId(fieldId);

        return seats;
    }

    @Override
    public Page<OrderVo> getOrderByUserId(Integer userId, Page<OrderVo> page) {
        Page<OrderVo> result = new Page<>();
        if(userId == null){
            log.error("订单查询业务失败,用户编号未传入");
            return null;
        }
        List<OrderVo> orderVoList = moocOrderTMapper.getOrdersByUserId(userId,page);
        if(orderVoList == null && orderVoList.size() == 0){
            result.setTotal(0);
            result.setRecords(new ArrayList<>());
            return result;
        }
        //获取总数
        EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("order_user",userId);
        Integer counts = moocOrderTMapper.selectCount(entityWrapper);
        //将结果放入Page
        result.setTotal(counts);
        result.setRecords(orderVoList);
        return result;
    }
}
