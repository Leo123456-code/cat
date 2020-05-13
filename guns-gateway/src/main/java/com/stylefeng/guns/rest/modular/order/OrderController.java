package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.IOrderServiceAPI;
import com.stylefeng.guns.api.user.order.form.OrderBuyTicketsForm;
import com.stylefeng.guns.api.user.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: OrderController
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/2-13:05
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Reference(interfaceClass = IOrderServiceAPI.class,check = false)
    private IOrderServiceAPI orderServiceAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";
    //用户下单购票接口
    @RequestMapping(value = "/buyTickets",method = RequestMethod.POST)
    public ResponseVO buyTickets(OrderBuyTicketsForm form){
        //验证售出的票是否为真
        boolean isTure = orderServiceAPI.isTrueSeats(form.getFieldId() + "", form.getSoldSeats());

        //已经销售的座位里,有没有这些座位
        boolean isNotSold = orderServiceAPI.isNotSoldSeats(form.getFieldId() + "", form.getSoldSeats());
        //验证
        if(isTure && isNotSold){
            //创建订单信息
            String userId = CurrentUser.getCurrentUser();
            OrderVo orderVo = orderServiceAPI.saveOrderInfo(form.getFieldId(),
                    form.getSoldSeats(), form.getSeatsName(), userId);

            if(orderVo == null){
                return ResponseVO.serviceFail("购票业务异常");
            }else {
                log.info("用户下单成功");
                return ResponseVO.success(orderVo);
            }
        }else {
            return ResponseVO.serviceFail("购票业务异常");
        }


    }

    //获取用户订单信息接口
    @RequestMapping(value = "/getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo
    (@RequestParam(name = "nowPage",required = false,defaultValue = "1") Integer nowPage,
     @RequestParam(name = "pageSize",required = false,defaultValue = "5") Integer pageSize){
        //获取当前登录人的信息
        String userId = CurrentUser.getCurrentUser();
        //获取当前登录人员的订单信息
        Page<OrderVo> page = new Page<>(nowPage, pageSize);
        if(userId != null && userId.trim().length() > 0){
            Page<OrderVo> orderList = orderServiceAPI.getOrderByUserId(Integer.parseInt(userId), page);
            return ResponseVO.success(nowPage,(int)orderList.getPages(),IMG_PRE,orderList.getRecords());
        }


        return ResponseVO.serviceFail("用户未登录");
    }
}
