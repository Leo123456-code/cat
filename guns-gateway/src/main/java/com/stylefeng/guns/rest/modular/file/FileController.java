package com.stylefeng.guns.rest.modular.file;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.user.IFileServiceAPI;
import com.stylefeng.guns.api.user.IFilmAsyncService;
import com.stylefeng.guns.api.user.file.vo.*;
import com.stylefeng.guns.rest.modular.file.vo.FileIndexVo;
import com.stylefeng.guns.rest.modular.file.vo.FilmConditionVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ClassName: FileController
 * Description: 影片模块Controller
 * Author: Leo
 * Date: 2020/4/26-20:09
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/film")
public class FileController {
    //dubbo
    @Reference(interfaceClass = IFileServiceAPI.class,check = false)
    private IFileServiceAPI fileAPI;

    //dubbo异步调用
    @Reference(interfaceClass = IFilmAsyncService.class,async = true)
    private IFilmAsyncService filmAsyncAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    //获取首页信息接口

    /**
     * API网关
     * 1.功能聚合【API聚合】
     * @return
     */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public ResponseVO getIndex(){
        FileIndexVo fileIndexVo = new FileIndexVo();
        //获取banner信息
        fileIndexVo.setBanners(fileAPI.getBanners());
        //获取正在热映的电影
//        fileIndexVo.setHotFilms(fileAPI.getHotFilms(true,10));
        fileIndexVo.setHotFilms(fileAPI.getHotFilmsMart(true,8,1,1,99,99,99));
        //获取上映的电影
//        fileIndexVo.setSoonFilms(fileAPI.getSoonFilms(true,10));
        fileIndexVo.setSoonFilms(fileAPI.getSoonFilmsMart(true,8,1,1,99,99,99));
        //票房排行榜
        fileIndexVo.setBoxRanking(fileAPI.getBoxRanking());
        //获取受欢迎的榜单
        fileIndexVo.setExpectRanking(fileAPI.getExpectRanking());
        //获取前100
        fileIndexVo.setTop100(fileAPI.getTop());

        return ResponseVO.success(IMG_PRE,fileIndexVo);
    }


    //2、影片条件列表查询接口
    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name = "catId",required = false,defaultValue = "99") String catId,
   @RequestParam(name = "sourceId",required = false,defaultValue = "99") String sourceId,
    @RequestParam(name = "yearId",required = false,defaultValue = "99") String yearId){
        FilmConditionVo filmConditionVo = new FilmConditionVo();
        //标识位
        boolean flag = false;
        //catInfo:类型集合
        List<CatVo> cats = fileAPI.getCats();
        List<CatVo> catResult = new ArrayList<>();
        CatVo cat = null;

        for (CatVo catVo : cats) {
            if(catVo.getCatId().equals("99")){
                cat = catVo;
                continue;
            }
            //判断集合是否存在catId，如果存在,则将对应的实体变成active状态
            if(catVo.getCatId().equals(catId)){
                flag = true;
                catVo.setActive(true);
            }else {
                catVo.setActive(false);
            }
            catResult.add(catVo);
        }
        //如果不存在,则默认将全部变为Active状态
        if(!flag){
            //全部为true
            cat.setActive(true);
            catResult.add(cat);
        }else {
            cat.setActive(false);
            catResult.add(cat);
        }
        //sourceInfo 片源集合
        flag = false;
        List<SourceVo> sources = fileAPI.getSources();
        List<SourceVo> sourceResult = new ArrayList<>();
        SourceVo source = null;

        for (SourceVo sourceVo : sources) {
            if(sourceVo.getSourceId().equals("99")){
                source = sourceVo;
                continue;
            }
            //判断集合是否存在catId，如果存在,则将对应的实体变成active状态
            if(sourceVo.getSourceId().equals(sourceId)){
                flag = true;
                sourceVo.setActive(true);
            }else {
                sourceVo.setActive(false);
            }
            sourceResult.add(sourceVo);
        }
        //如果不存在,则默认将全部变为Active状态
        if(!flag){
            //全部为true
            source.setActive(true);
            sourceResult.add(source);
        }else {
            source.setActive(false);
            sourceResult.add(source);
        }

        //yearInfo 年代集合
        flag = false;
        List<YearVo> years = fileAPI.getYears();
        List<YearVo> yearResult = new ArrayList<>();
        YearVo year = null;

        for (YearVo yearVo : years) {
            if(yearVo.getYearId().equals("99")){
                year = yearVo;
                continue;
            }
            //判断集合是否存在catId，如果存在,则将对应的实体变成active状态
            if(yearVo.getYearId().equals(yearId)){
                flag = true;
                yearVo.setActive(true);
            }else {
                yearVo.setActive(false);
            }
            yearResult.add(yearVo);
        }
        //如果不存在,则默认将全部变为Active状态
        if(!flag){
            //全部为true
            year.setActive(true);
            yearResult.add(year);
        }else {
            year.setActive(false);
            yearResult.add(year);
        }

        filmConditionVo.setCatInfo(catResult);
        filmConditionVo.setSourceInfo(sourceResult);
        filmConditionVo.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVo);
    }




    //3.影片查询接口
    @RequestMapping(value = "/getFilms",method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVo requestVo){
        FilmVo filmVo = null;
        //根据showType 判断影片查询类型
        switch (requestVo.getShowType()){
            case 1:
                filmVo = fileAPI.getHotFilmsMart(false,requestVo.getPageSize(),requestVo.getNowPage(),
                        requestVo.getSortId(),requestVo.getSourceId(),requestVo.getYearId(),requestVo.getCatId());
                break;
            case 2:
                filmVo = fileAPI.getSoonFilmsMart(false,requestVo.getPageSize(),requestVo.getNowPage(),
                        requestVo.getSortId(),requestVo.getSourceId(),requestVo.getYearId(),requestVo.getCatId());
                break;
            case 3:
                filmVo = fileAPI.getClassicFilmsMart(requestVo.getPageSize(),requestVo.getNowPage(),
                        requestVo.getSortId(),requestVo.getSourceId(),requestVo.getYearId(),requestVo.getCatId());
                break;
            default:
                filmVo = fileAPI.getHotFilmsMart(false,requestVo.getPageSize(),requestVo.getNowPage(),
                        requestVo.getSortId(),requestVo.getSourceId(),requestVo.getYearId(),requestVo.getCatId());
                break;
        }

        return ResponseVO.success(filmVo.getNowPage(),filmVo.getTotalPage(),IMG_PRE,filmVo.getFilmInfos());
    }


    //4.影片详情查询接口
    @RequestMapping(value = "/films/{searchParam}",method = RequestMethod.GET)
    public ResponseVO getfilms(@PathVariable("searchParam") String searchParam,
                                   @RequestParam("searchType") int searchType) throws Exception{
        //根据searchType,判断查询类型

        //不同的查询类型,传入的条件略有不同
        //查询影片的详细信息---> Dubbo的异步获取
        //获取影片的描述信息
        FilmDetaVo filmDetaVo = fileAPI.getFilmDeta(searchParam,searchType);
        if(filmDetaVo == null){
            return ResponseVO.serviceFail("没有可查询的影片");
        }else if(filmDetaVo.getFilmId()==null || filmDetaVo.getFilmId().trim().length()==0){
            return ResponseVO.serviceFail("没有可查询的影片");
        }
        String filmId = filmDetaVo.getFilmId();

        //获取影片描述信息 ---> Dubbo的异步调用
        filmAsyncAPI.getFilmDesc(filmId);
        Future<FileDescVo> fileDescVoFuture = RpcContext.getContext().getFuture();
        //获取图片信息
        filmAsyncAPI.getImgs(filmId);
        Future<ImgVo> imgVoFuture = RpcContext.getContext().getFuture();
        //获取导演信息
        filmAsyncAPI.getDectInfo(filmId);
        Future<ActorVo> dectInfoFuture = RpcContext.getContext().getFuture();
        //获取演员信息
        filmAsyncAPI.getActors(filmId);
        Future<List<ActorVo>> actorsFuture = RpcContext.getContext().getFuture();
        //组织Info对象
        InfoRequestVo infoRequestVo = new InfoRequestVo();
        ActorRequestVo actorRequestVo = new ActorRequestVo();
        //组织Actor对象
        actorRequestVo.setActors(actorsFuture.get());
        actorRequestVo.setDirector(dectInfoFuture.get());
        //组织Info对象
        infoRequestVo.setActors(actorRequestVo);
        infoRequestVo.setBiography(fileDescVoFuture.get().getBiography());
        infoRequestVo.setFilmId(filmId);
        infoRequestVo.setImgs(imgVoFuture.get());
        //组织返回值
        filmDetaVo.setInfo04(infoRequestVo);

        return ResponseVO.success(IMG_PRE,filmDetaVo);
    }

}
