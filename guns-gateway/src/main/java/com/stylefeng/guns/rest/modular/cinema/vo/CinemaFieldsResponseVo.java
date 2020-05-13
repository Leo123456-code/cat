package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.user.cinema.vo.CinemaInfoVo;
import com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo;
import lombok.Data;

import java.util.List;

/**
 * ClassName: CinemaFieldResponseVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/1-22:20
 * email 1437665365@qq.com
 */
@Data
public class CinemaFieldsResponseVo {

    private CinemaInfoVo cinemaInfo;

    private List<FilmInfoVo> filmList;
}
