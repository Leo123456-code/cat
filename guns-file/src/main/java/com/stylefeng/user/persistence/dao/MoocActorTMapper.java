package com.stylefeng.user.persistence.dao;

import com.stylefeng.guns.api.user.file.vo.ActorVo;
import com.stylefeng.user.persistence.model.MoocActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author Leo
 * @since 2020-04-26
 */
@Mapper
public interface MoocActorTMapper extends BaseMapper<MoocActorT> {

    List<ActorVo> getActors(@Param("filmId") String filmId);

}
