package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.file.vo.ActorVo;
import com.stylefeng.guns.api.user.file.vo.FileDescVo;
import com.stylefeng.guns.api.user.file.vo.ImgVo;

import java.util.List;

/**
 * @program: schekafka
 * @ClassName: IFilmAsyncService
 * @Description: TODO dubbo异步调用
 * @Author: Leo
 * @Date: 2020/4/28-12:10
 */
public interface IFilmAsyncService {

    //获取影片的描述信息
    FileDescVo getFilmDesc(String filmId);
    //获取图片信息
    ImgVo getImgs(String filmId);
    //获取导演信息
    ActorVo getDectInfo(String filmId);
    //获取演员信息
    List<ActorVo> getActors(String filmId);

}
