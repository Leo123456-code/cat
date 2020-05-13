package com.stylefeng.guns.rest.modular.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: ResponseVO
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/25-13:56
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//保证序列化json的时候,如果是null的对象,key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseVO<M> implements Serializable {
    //返回状态
    //0-成功  1-业务异常  999-系统异常
    private int status;
    //返回信息
    private String msg;
    //返回数据实体
    private M data;

    private String imgPre;

    //分页使用
    //当前页
    private Integer nowPage;
    //总页数
    private Integer totalPage;

    //成功
    public static <M> ResponseVO success(Integer nowPage,Integer totalPage,String imgPre,M m) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        responseVO.setTotalPage(totalPage);
        responseVO.setNowPage(nowPage);
        return responseVO;
    }

    //成功
    public static <M> ResponseVO success(String imgPre,M m) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        return responseVO;
    }


    //成功
    public static <M> ResponseVO success(M m) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(m);
        return responseVO;
    }

    public static <M> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //业务异常
    public static <M> ResponseVO serviceFail(String msg) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //系统异常
    public static <M> ResponseVO appFail(String msg) {
        ResponseVO responseVO = new ResponseVO<>();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);
        return responseVO;
    }


}
