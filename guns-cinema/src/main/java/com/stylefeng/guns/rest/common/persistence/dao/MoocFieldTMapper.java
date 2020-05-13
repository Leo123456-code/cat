package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.user.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.user.cinema.vo.HallInfoVo;
import com.stylefeng.guns.rest.common.persistence.model.MoocFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author Leo
 * @since 2020-04-28
 */
@Mapper
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {
    //查询 一对多
    //FilmInfoVo 多
    List<FilmInfoVo> getFilmInfos(@Param("cinemaId") int cinemaId);
    //
    HallInfoVo getHallInfo(@Param("fieldId")int fieldId);
    //
    FilmInfoVo getFilmInfoById(@Param("fieldId")int fieldId);

}
