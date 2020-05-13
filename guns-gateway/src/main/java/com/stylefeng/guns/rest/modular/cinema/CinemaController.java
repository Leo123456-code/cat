package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.ICinemaServiceAPI;
import com.stylefeng.guns.api.user.IOrderServiceAPI;
import com.stylefeng.guns.api.user.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVo;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVo;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: CinemaController
 * Description: TODO 影院模块Controller
 * Author: Leo
 * Date: 2020/4/28-18:46
 * email 1437665365@qq.com
 */
@RestController
@RequestMapping("/cinema")
@Slf4j
public class CinemaController {

    @Reference(interfaceClass = ICinemaServiceAPI.class,
            cache = "lru",check = false)
    private ICinemaServiceAPI cinemaServiceAPI;

    @Reference(interfaceClass = IOrderServiceAPI.class,check = false)
    private IOrderServiceAPI orderServiceAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    //查询影院列表-根据条件查询所有影院
    @RequestMapping(value = "/getCinemas",method = RequestMethod.GET)
    public ResponseVO getCinemas(CinemaQueryVo cinemaQueryVo){
        //按照5个条件进行删选

        //判断是否有满足条件的删选
        try {
            Page<CinemaVo> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVo);
            if(cinemas.getRecords() == null || cinemas.getRecords().size() == 0){
                return ResponseVO.success("没有符合的影院可查");
            }else {
                //当前页,总页数
                return ResponseVO.success(cinemas.getCurrent(),(int)cinemas.getPages(),
                        IMG_PRE,cinemas.getRecords());
            }
        }catch (Exception e){
            //如果有异常
            log.error("获取影院列表异常",e);
            return ResponseVO.serviceFail("查询影院列表失败");
        }

    }

    //获取影院列表查询条件

    /**
     * 1.热点数据 --> 放缓存
     * @param cinemaQueryVo
     * @return
     */
    @RequestMapping(value = "/getCondition",method = RequestMethod.GET)
    public ResponseVO getCondition(CinemaQueryVo cinemaQueryVo){
        //获取三个集合,然后封装成一个对象返回即可
        List<BrandVo> brandVoList = cinemaServiceAPI.getBrands(cinemaQueryVo.getBrandId());
        List<AreaVo> areaVoList = cinemaServiceAPI.getAreas(cinemaQueryVo.getDistrictId());
        List<HallTypeVo> hallTypeVoList = cinemaServiceAPI.getHallTypes(cinemaQueryVo.getHallType());
        //组装对象
        CinemaConditionResponseVo cinemaConditionResponseVo = new CinemaConditionResponseVo();
        cinemaConditionResponseVo.setAreaList(areaVoList);
        cinemaConditionResponseVo.setBrandList(brandVoList);
        cinemaConditionResponseVo.setHalltypeList(hallTypeVoList);


        return ResponseVO.success(cinemaConditionResponseVo);
    }


    //3、获取播放场次接口
    @RequestMapping(value = "/getFields")
    public ResponseVO getFields(Integer cinemaId){
        try {
            CinemaInfoVo cinemaInfoVo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            List<FilmInfoVo> filmInfoVos = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);
            CinemaFieldsResponseVo cinemaFieldResponseVo = new CinemaFieldsResponseVo();
            cinemaFieldResponseVo.setCinemaInfo(cinemaInfoVo);
            cinemaFieldResponseVo.setFilmList(filmInfoVos);
            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVo);

        }catch (Exception e){
            log.error("获取播放场次失败",e);
            return ResponseVO.serviceFail("获取播放场次失败");
        }

    }

    //4、获取场次详细信息接口
    @RequestMapping(value = "/getFieldInfo",method = RequestMethod.POST)
    public ResponseVO getFieldInfo(Integer cinemaId,Integer fieldId){
        try {
            CinemaInfoVo cinemaInfoVo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfoVo filmInfoVo = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            HallInfoVo hallInfoVo = cinemaServiceAPI.getFilmFieldInfo(fieldId);

            //造几个销售的假数据,后续会对接订单接口
//            hallInfoVo.setSoldSeats("1,2,3");
            hallInfoVo.setSoldSeats(orderServiceAPI.getSoldSeatsByFieldId(fieldId));


            CinemaFieldResponseVo cinemaFieldResponseVo = new CinemaFieldResponseVo();
            cinemaFieldResponseVo.setCinemaInfo(cinemaInfoVo);
            cinemaFieldResponseVo.setFilmInfo(filmInfoVo);
            cinemaFieldResponseVo.setHallInfo(hallInfoVo);

            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVo);


        }catch (Exception e){
            log.error("获取选座信息失败",e);
            return ResponseVO.serviceFail("获取选座信息失败");
        }

    }


}
