package com.stylefeng.guns.rest.modular.cinema.serivceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.ICinemaServiceAPI;
import com.stylefeng.guns.api.user.cinema.vo.*;
import com.stylefeng.guns.api.user.order.vo.OrderQueryVo;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CinemaServiceimpl
 * Description: TODO 影院模块接口实现
 * Author: Leo
 * Date: 2020/4/28-20:20
 * email 1437665365@qq.com
 */
@Component
@Service(interfaceClass = ICinemaServiceAPI.class)
public class CinemaServiceimpl implements ICinemaServiceAPI {
    //放映场次表 Mapper 接口
    @Autowired
    private MoocFieldTMapper moocFieldTMapper;
    //影院信息表 Mapper 接口
    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;
    //品牌信息表 Mapper 接口
    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;
    //地域信息表 Mapper 接口
    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;
    //地域信息表 Mapper 接口
    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;

    @Override
    public Page<CinemaVo> getCinemas(CinemaQueryVo cinemaQueryVo) {
        /**
         * @Description //TODO 查询影院列表
           @Author Leo
         * @Date 20:23 2020/4/28
         * @Param [cinemaQueryVo]
         * @return com.baomidou.mybatisplus.plugins.Page<com.stylefeng.guns.api.user.cinema.vo.CinemaVo>
        */
        //业务实体集合
        List<CinemaVo> cinemas = new ArrayList<>();
        //当前页,每页显示多少条
        Page<MoocCinemaT> page = new Page<>
                (cinemaQueryVo.getNowPage(),cinemaQueryVo.getPageSize());
        //影院信息表 MoocCinemaT
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        if(cinemaQueryVo.getBrandId() != 99){
           entityWrapper.eq("brand_id",cinemaQueryVo.getBrandId()) ;
        }
        if(cinemaQueryVo.getDistrictId() != 99){
           entityWrapper.eq("area_id",cinemaQueryVo.getDistrictId()) ;
        }
        //影厅类型
        if(cinemaQueryVo.getHallType() != 99){
           entityWrapper.eq("hall_ids","%#"+cinemaQueryVo.getHallType()+"#%") ;
        }
        //判断是否传入查询条件 --->brandId,districtId,hallType 是否 ==99
        //将数据实体转换为业务实体
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);
        for (MoocCinemaT moocCinemaT : moocCinemaTS) {
            CinemaVo cinemaVo = new CinemaVo();
            cinemaVo.setUuid(moocCinemaT.getUuid()+"");
            cinemaVo.setMinimumPrice(moocCinemaT.getMinimumPrice()+"");
            cinemaVo.setCinemaName(moocCinemaT.getCinemaName());
            cinemaVo.setAddress(moocCinemaT.getCinemaAddress());
            cinemas.add(cinemaVo);
        }

        //根据条件,判断影院列表总数
        long counts = moocCinemaTMapper.selectCount(entityWrapper);
        //组织返回值对象
        Page<CinemaVo> result = new Page<>();
        result.setRecords(cinemas);
        result.setSize(cinemaQueryVo.getPageSize());
        result.setTotal(counts);

        return result;
    }

    @Override
    public List<BrandVo> getBrands(int brandId) {
        /**
         * @Description //TODO 根据id条件获取品牌列表
           @Author Leo
         * @Date 20:22 2020/4/28
         * @Param [brandId]
         * @return java.util.List<com.stylefeng.guns.api.user.cinema.vo.BrandVo>
        */
        //标识位
        boolean flag = false;
        List<BrandVo> brandVoList = new ArrayList<>();

        //判断brandId是否存在
        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);
        //判断 brandId 是否等于99
        if(brandId == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null){
            flag = true;

        }
        //查询所有列表
        List<MoocBrandDictT> moocBrandDictTS = moocBrandDictTMapper.selectList(null);
        //判断flag 如果为true ,则将99置为 isActive
        for (MoocBrandDictT brand : moocBrandDictTS) {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandName(brand.getShowName());
            brandVo.setBrandId(brand.getUuid()+"");
            //如果flag为true,则需要99,如为false,则匹配上的内容为 active
            if(flag){
                if(brand.getUuid() == 99){
                  brandVo.setActive(true);
                }
            }else {
                if(brand.getUuid() == brandId){
                    brandVo.setActive(true);
                }
            }
            brandVoList.add(brandVo);
        }
        return brandVoList;
    }

    @Override
    public List<AreaVo> getAreas(int areaId) {
        /**
         * @Description //TODO 获取行政区域列表
           @Author Leo
         * @Date 20:23 2020/4/28
         * @Param [areaId]
         * @return java.util.List<com.stylefeng.guns.api.user.cinema.vo.AreaVo>
        */

        //标识位
        boolean flag = false;
        List<AreaVo> areaVoList = new ArrayList<>();

        //判断brandId是否存在
        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);
        //判断 brandId 是否等于99
        if(areaId == 99 || moocAreaDictT == null || moocAreaDictT.getUuid() == null){
            flag = true;
        }
        //查询所有列表
        List<MoocAreaDictT> moocAreaDictTS = moocAreaDictTMapper.selectList(null);
        //判断flag 如果为true ,则将99置为 isActive
        for (MoocAreaDictT area : moocAreaDictTS) {
            AreaVo areaVo = new AreaVo();
            areaVo.setAreaName(area.getShowName());
            areaVo.setAreaId(area.getUuid()+"");
            if(flag){
                //如果flag为true,则需要99,如为false,则匹配上的内容为 active
                if(area.getUuid() == 99){
                    areaVo.setActive(true);
                }
            }else {
                if(area.getUuid() == areaId){
                    areaVo.setActive(true);
                }
        }
            areaVoList.add(areaVo);
        }
        return areaVoList;
    }

    @Override
    public List<HallTypeVo> getHallTypes(int hallType) {
        /**
         * @Description //TODO 获取影厅类型列表
           @Author Leo
         * @Date 20:23 2020/4/28
         * @Param [hallType]
         * @return java.util.List<com.stylefeng.guns.api.user.cinema.vo.HallTypeVo>
        */
        //标识位
        boolean flag = false;
        List<HallTypeVo> hallTypeVoList = new ArrayList<>();

        //判断brandId是否存在
        MoocHallDictT moocHallDictT = moocHallDictTMapper.selectById(hallType);
        //判断 brandId 是否等于99
        if(hallType == 99 || moocHallDictT == null || moocHallDictT.getUuid() == null){
            flag = true;

        }
        //查询所有列表
        List<MoocHallDictT> moocHallDictTS = moocHallDictTMapper.selectList(null);
        //判断flag 如果为true ,则将99置为 isActive
        for (MoocHallDictT hall : moocHallDictTS) {
            HallTypeVo hallTypeVo = new HallTypeVo();
            hallTypeVo.setHalltypeName(hall.getShowName());
            hallTypeVo.setHalltypeId(hall.getUuid()+"");
            //如果flag为true,则需要99,如为false,则匹配上的内容为 active
            if(flag){
                if(hall.getUuid() == 99){
                    hallTypeVo.setActive(true);
                }
            }else {
                if(hall.getUuid() == hallType){
                    hallTypeVo.setActive(true);
                }
            }
            hallTypeVoList.add(hallTypeVo);
        }
        return hallTypeVoList;
    }

    @Override
    public CinemaInfoVo getCinemaInfoById(int cinemaId) {
        /**
         * @Description //TODO 根据影院编号,获取影院信息
           @Author Leo
         * @Date 20:24 2020/4/28
         * @Param [cinemaId]
         * @return com.stylefeng.guns.api.user.cinema.vo.CinemaInfoVo
        */
        //数据实体
        MoocCinemaT moocCinemaT = moocCinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfoVo = new CinemaInfoVo();
        //将数据实体转换成业务实体
        cinemaInfoVo.setCinemaAdress(moocCinemaT.getCinemaAddress());
        cinemaInfoVo.setImgUrl(moocCinemaT.getImgAddress());
        cinemaInfoVo.setCinemaPhone(moocCinemaT.getCinemaPhone());
        cinemaInfoVo.setCinemaName(moocCinemaT.getCinemaName());
        cinemaInfoVo.setCinemaId(moocCinemaT.getUuid()+"");

        return cinemaInfoVo;
    }

    @Override
    public List<FilmInfoVo> getFilmInfoByCinemaId(int cinemaId) {
        /**
         * @Description //TODO 获取所有电影的信息和对应的放映场次信息,根据影院编号
           @Author Leo
         * @Date 20:24 2020/4/28
         * @Param [cinemaId]
         * @return com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo
        */
        List<FilmInfoVo> filmInfos = moocFieldTMapper.getFilmInfos(cinemaId);

        return filmInfos;
    }

    @Override
    public HallInfoVo getFilmFieldInfo(int fieldId) {
        /**
         * @Description //TODO 根据放映场次ID获取放映信息
           @Author Leo
         * @Date 20:24 2020/4/28
         * @Param [fieldId]
         * @return com.stylefeng.guns.api.user.cinema.vo.FilmFieldVo
        */
        HallInfoVo hallInfo = moocFieldTMapper.getHallInfo(fieldId);

        return hallInfo;
    }

    @Override
    public FilmInfoVo getFilmInfoByFieldId(int fieldId) {
        /**
         * @Description //TODO 根据放映场次查询播放的电影编号,然后根据电影编号获取对应的电影信息
           @Author Leo
         * @Date 20:24 2020/4/28
         * @Param [fieldId]
         * @return com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo
        */
        FilmInfoVo info = moocFieldTMapper.getFilmInfoById(fieldId);

        return info;
    }

    @Override
    public OrderQueryVo getOrderNeeds(Integer fieldId) {
        /**
         * @Description //TODO 订单模块需要的
           @Author Leo
         * @Date 20:53 2020/5/2
         * @Param [fieldId]
         * @return com.stylefeng.guns.api.user.order.vo.OrderQueryVo
        */
        OrderQueryVo orderQueryVo = new OrderQueryVo();
        MoocFieldT moocFieldT = moocFieldTMapper.selectById(fieldId);

        orderQueryVo.setCinemaId(moocFieldT.getCinemaId()+"");
        orderQueryVo.setFilmPrice(moocFieldT.getPrice()+"");

        return orderQueryVo;
    }
}
