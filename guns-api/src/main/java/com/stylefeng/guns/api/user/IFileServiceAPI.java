package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.file.vo.*;

import java.util.List;

/**
 * ClassName: FileServiceAPI
 * Description: TODO 影片服务接口
 * Author: Leo
 * Date: 2020/4/26-21:01
 * email 1437665365@qq.com
 */
public interface IFileServiceAPI {
    //获取banners
    List<BannerVo> getBanners();
    //获取热映的影片
    FilmVo getHotFilms(boolean isLimit,int nums);
    //获取即将上映的影片[受欢迎程度排序]
    FilmVo getSoonFilms(boolean isLimit,int nums);
    //获取票房排行榜
    List<FilmInfo> getBoxRanking();
    //获取人气排行榜
    List<FilmInfo> getExpectRanking();
    //获取top10
    List<FilmInfo> getTop();

    //----获取影片条件 接口
    //分类条件
    List<CatVo> getCats();
    //片源条件
    List<SourceVo> getSources();
    //年代条件
    List<YearVo> getYears();
    //-----3、影片查询接口
    // 获取热映影片
    FilmVo getHotFilmsMart(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取即将上映影片[受欢迎程度做排序]
    FilmVo getSoonFilmsMart(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取经典影片
    FilmVo getClassicFilmsMart(int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    //-------
    //根据影片ID或者名称获取影片信息
    FilmDetaVo getFilmDeta(String searchParam,int searchType);
    //获取影片的描述信息
    FileDescVo getFilmDesc(String filmId);
    //获取图片信息
    ImgVo getImgs(String filmId);
    //获取导演信息
    ActorVo getDectInfo(String filmId);
    //获取演员信息
    List<ActorVo> getActors(String filmId);
    //获取影片相关的其他信息[演员表,图片地址]
}
