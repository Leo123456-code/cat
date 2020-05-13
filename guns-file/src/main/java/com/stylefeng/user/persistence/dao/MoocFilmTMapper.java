package com.stylefeng.user.persistence.dao;

import com.stylefeng.guns.api.user.file.vo.FilmDetaVo;
import com.stylefeng.user.persistence.model.MoocFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author Leo
 * @since 2020-04-26
 */
@Mapper
public interface MoocFilmTMapper extends BaseMapper<MoocFilmT> {

    //
    FilmDetaVo getFilmDetaByName(@Param("filmName") String filmName);

    FilmDetaVo getFilmDetaById(@Param("uuid") String uuid);

}
