package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.user.cinema.vo.CinemaInfoVo;
import com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.user.cinema.vo.HallInfoVo;
import lombok.Data;

/**
 * ClassName: CinemaFieldResponseVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/1-22:30
 * email 1437665365@qq.com
 */
@Data
public class CinemaFieldResponseVo {
    private FilmInfoVo filmInfo;
    private CinemaInfoVo cinemaInfo;
    private HallInfoVo hallInfo;
}
