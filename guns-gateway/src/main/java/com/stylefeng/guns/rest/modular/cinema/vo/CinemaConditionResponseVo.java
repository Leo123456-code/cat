package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.user.cinema.vo.AreaVo;
import com.stylefeng.guns.api.user.cinema.vo.BrandVo;
import com.stylefeng.guns.api.user.cinema.vo.HallTypeVo;
import lombok.Data;

import java.util.List;

/**
 * ClassName: CinemaConditionResponseVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/1-22:04
 * email 1437665365@qq.com
 */
@Data
public class CinemaConditionResponseVo {

    private List<BrandVo> brandList;
    private List<AreaVo> areaList;
    private List<HallTypeVo> halltypeList;
}
