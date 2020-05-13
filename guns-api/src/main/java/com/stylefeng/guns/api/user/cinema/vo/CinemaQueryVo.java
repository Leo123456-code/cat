package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: CinemaQueryVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-18:51
 * email 1437665365@qq.com
 */
@Data
public class CinemaQueryVo implements Serializable {
    //影院编号
    private Integer brandId=99;
    //影厅类型
    private Integer hallType=99;
    //行政区编号
    private Integer districtId=99;
    //每页条数
    private Integer pageSize=12;
    //当前页数
    private Integer nowPage=1;

}
