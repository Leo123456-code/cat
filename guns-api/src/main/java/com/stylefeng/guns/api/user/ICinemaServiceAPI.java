package com.stylefeng.guns.api.user;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.cinema.vo.*;
import com.stylefeng.guns.api.user.order.vo.OrderQueryVo;

import java.util.List;

/**
 * ClassName: ICinemaServiceAPI
 * Description: TODO 影院模块接口
 * Author: Leo
 * Date: 2020/4/28-18:49
 * email 1437665365@qq.com
 */
public interface ICinemaServiceAPI {

    //根据CinemaQueryVo,查询影院列表
    Page<CinemaVo> getCinemas(CinemaQueryVo cinemaQueryVo);
    //根据条件获取品牌列表[除了99以外,其他的数字为isActive]
    List<BrandVo> getBrands(int brandId);
    //获取行政区域列表
    List<AreaVo> getAreas(int areaId);
    //获取影厅类型列表
    List<HallTypeVo> getHallTypes(int hallType);
    //根据影院编号,获取影院信息
    CinemaInfoVo getCinemaInfoById(int cinemaId);
    //获取所有电影的信息和对应的放映场次信息,根据影院编号
    List<FilmInfoVo> getFilmInfoByCinemaId(int cinemaId);

    //根据放映场次ID获取放映信息
    HallInfoVo getFilmFieldInfo(int fieldId);
    //根据放映场次查询播放的电影编号,然后根据电影编号获取对应的电影信息
    FilmInfoVo getFilmInfoByFieldId(int fieldId);
    /**
     * 该部分是订单模块需要的
     */
    OrderQueryVo getOrderNeeds(Integer fieldId);



}
