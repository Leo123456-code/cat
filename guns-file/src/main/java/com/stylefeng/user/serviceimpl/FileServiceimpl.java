package com.stylefeng.user.serviceimpl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.user.IFileServiceAPI;
import com.stylefeng.guns.api.user.file.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.user.persistence.dao.*;
import com.stylefeng.user.persistence.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: FileServiceimpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/26-21:26
 * email 1437665365@qq.com
 */
@Component
@Service(interfaceClass = IFileServiceAPI.class, loadbalance = "roundrobin")
@Slf4j
public class FileServiceimpl implements IFileServiceAPI {
    //banner信息表 Mapper 接口
    @Autowired
    private MoocBannerTMapper moocBannerTMapper;
    //影片主表 Mapper 接口
    @Autowired
    private MoocFilmTMapper moocFilmTMapper;
    //类型信息表 Mapper 接口
    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;
    //年代信息表 Mapper 接口
    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    //区域信息表 Mapper 接口
    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    //    影片主表 Mapper 接口
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;
    //    影片主表 Mapper 接口
    @Autowired
    private MoocActorTMapper moocActorTMapper;

    //演员表 Mapper 接口
    @Override
    public List<BannerVo> getBanners() {
        /**
         * @Description //TODO 获取banners
         @Author Leo
          * @Date 21:29 2020/4/26
         * @Param []
         * @return com.stylefeng.guns.api.user.file.vo.BannerVo
         */
        List<BannerVo> list = new ArrayList<>();
        List<MoocBannerT> moocBanners = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBanners) {
            BannerVo bannerVo = new BannerVo();
            bannerVo.setBannerId(moocBannerT.getUuid() + "");
            bannerVo.setBannerUrl(moocBannerT.getBannerUrl());
            bannerVo.setBannerAddress(moocBannerT.getBannerAddress());
            list.add(bannerVo);
        }
        return list;
    }

    @Override
    public FilmVo getHotFilms(boolean isLimit, int nums) {
        /**
         * @Description //TODO 获取热映的影片
         @Author Leo
          * @Date 21:37 2020/4/26
         * @Param [isLimit, nums]
         * @return com.stylefeng.guns.api.user.file.vo.FilmVo
         */
        FilmVo filmVo = new FilmVo();
        //获取热映的影片的集合
        List<FilmInfo> filmInfos = new ArrayList<>();
//        限制条件
        //MoocFilmT 影片主表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //1.表示正在热映
        entityWrapper.eq("film_status", "1");
        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是,则限制条数,限制内容为热映影片
            //1.表示第一页,nums是用户传进来的,表示每页显示几条内容
            Page<MoocFilmT> page = new Page<>(1, nums);
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            filmVo.setFilmInfos(filmInfos);
        } else {
            //如果不是,则是列表页,同样需要限制内容为热映影片
        }


        return filmVo;
    }

    //获取filmInfo
    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilms) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MoocFilmT moocFilm : moocFilms) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilm.getFilmScore());
            filmInfo.setImgAddress(moocFilm.getImgAddress());
            filmInfo.setFilmType(moocFilm.getFilmType());
            filmInfo.setFilmScore(moocFilm.getFilmScore());
            filmInfo.setFilmName(moocFilm.getFilmName());
            filmInfo.setFilmId(moocFilm.getUuid() + "");
            filmInfo.setExpectNum(moocFilm.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilm.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilm.getFilmTime()));
            //将转换的对象放入结果集
            filmInfos.add(filmInfo);
        }
        return filmInfos;

    }

    @Override
    public FilmVo getSoonFilms(boolean isLimit, int nums) {
        /**
         * @Description //TODO 获取即将上映的影片[受欢迎程度排序]
         @Author Leo
          * @Date 22:14 2020/4/26
         * @Param [isLimit, nums]
         * @return com.stylefeng.guns.api.user.file.vo.FilmVo
         */
        FilmVo filmVo = new FilmVo();
        //获取热映的影片的集合
        List<FilmInfo> filmInfos = new ArrayList<>();
//        限制条件
        //MoocFilmT 影片主表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //2.即将上映
        entityWrapper.eq("film_status", "2");
        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是,则限制条数,限制内容为热映影片
            //1.表示第一页,nums是用户传进来的,表示每页显示几条内容
            Page<MoocFilmT> page = new Page<>(1, nums);
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            filmVo.setFilmInfos(filmInfos);
        } else {
            //如果不是,则是列表页,同样需要限制内容为热映影片
        }


        return filmVo;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        /**
         * @Description //TODO 获取票房排行榜
         @Author Leo
          * @Date 22:16 2020/4/26
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.FilmInfo>
         */
        //条件 --->正在上映的票房前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        //转换VO
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        /**
         * @Description //TODO 获取人气排行榜
         @Author Leo
          * @Date 22:18 2020/4/26
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.FilmInfo>
         */
        //条件 --->即将上映的,预售前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        //预售前10名
        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        //转换VO
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        /**
         * @Description //TODO 获取top100
         @Author Leo
          * @Date 22:18 2020/4/26
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.FilmInfo>
         */

        //条件 --->正在上映的,评分前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        //评分前10名
        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        //转换VO
        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);
        return filmInfos;
    }

    @Override
    public List<CatVo> getCats() {
        /**
         * @Description //TODO   //----获取影片条件 接口
         *     //分类条件
         @Author Leo
          * @Date 12:06 2020/4/27
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.CatVo>
         */
        List<CatVo> cats = new ArrayList<>();
        //查询实体对象
        List<MoocCatDictT> moocCats = moocCatDictTMapper.selectList(null);
        //将实体对象转换为业务对象 -CatVo
        for (MoocCatDictT moocCat : moocCats) {
            CatVo catVo = new CatVo();
            //显示名称
            catVo.setCatName(moocCat.getShowName());
            catVo.setCatId(moocCat.getUuid() + "");
            cats.add(catVo);
        }
        return cats;
    }

    @Override
    public List<SourceVo> getSources() {
        /**
         * @Description //TODO  //片源条件
         @Author Leo
          * @Date 12:06 2020/4/27
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.SourceVo>
         */
        List<SourceVo> sources = new ArrayList<>();
        //查询实体对象 -MoocSourceDictT
        List<MoocSourceDictT> moocSourceDicts =
                moocSourceDictTMapper.selectList(null);
        //将实体对象转换为Vo
        for (MoocSourceDictT moocSourceDict : moocSourceDicts) {
            SourceVo sourceVo = new SourceVo();
            sourceVo.setSourceName(moocSourceDict.getShowName());
            sourceVo.setSourceId(moocSourceDict.getUuid() + "");
            sources.add(sourceVo);
        }
        return sources;
    }

    @Override
    public List<YearVo> getYears() {
        /**
         * @Description //TODO 年代条件
         @Author Leo
          * @Date 12:07 2020/4/27
         * @Param []
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.YearVo>
         */
        List<YearVo> years = new ArrayList<>();
        //查询实体对象 -MoocCatDictT
        List<MoocYearDictT> moocYears = moocYearDictTMapper.selectList(null);
        //将实体对象转换为Vo
        for (MoocYearDictT moocCat : moocYears) {
            YearVo yearVo = new YearVo();
            yearVo.setYearName(moocCat.getShowName());
            yearVo.setYearId(moocCat.getUuid() + "");
            years.add(yearVo);
        }
        return years;
    }

    @Override
    public FilmVo getHotFilmsMart(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        /**
         * @Description //TODO 获取热映影片
         @Author Leo
          * @Date 14:19 2020/4/27
         * @Param [isLimit, nums, nowPage, sortId, sourceId, yearId, catId]
         * @return com.stylefeng.guns.api.user.file.vo.FilmVo
         */
        FilmVo filmVo = new FilmVo();
        //获取热映的影片的集合
        List<FilmInfo> filmInfos = new ArrayList<>();
//        限制条件
        //MoocFilmT 影片主表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //1.表示正在热映
        entityWrapper.eq("film_status", "1");
        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是,则限制条数,限制内容为热映影片
            //1.表示第一页,nums是用户传进来的,表示每页显示几条内容
            Page<MoocFilmT> page = new Page<>(1, nums);
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            filmVo.setFilmInfos(filmInfos);
        } else {
            //如果不是,则是列表页,同样需要限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(nowPage, nums);
            //如果sourceId,yearId,catId 不为99,则表示要按照对应的编号进行查询
            if (sortId != 99) {
                entityWrapper.eq("film_source", sortId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            //totalPages 需要总页数
            //totalCounts/nums 总条数/每页显示几条
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts / nums) + 1;

            filmVo.setFilmInfos(filmInfos);
            filmVo.setTotalPage(totalPages);
            filmVo.setNowPage(nowPage);
        }
        return filmVo;

    }

    @Override
    public FilmVo getSoonFilmsMart(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        /**
         * @Description //TODO 获取即将上映影片[受欢迎程度做排序]
         @Author Leo
          * @Date 14:19 2020/4/27
         * @Param [isLimit, nums, nowPage, sortId, sourceId, yearId, catId]
         * @return com.stylefeng.guns.api.user.file.vo.FilmVo
         */
        FilmVo filmVo = new FilmVo();
        //获取热映的影片的集合
        List<FilmInfo> filmInfos = new ArrayList<>();
//        限制条件
        //MoocFilmT 影片主表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //2.即将上映
        entityWrapper.eq("film_status", "2");
        //判断是否是首页需要的内容
        if (isLimit) {
            //如果是,则限制条数,限制内容为即将上映影片
            //1.表示第一页,nums是用户传进来的,表示每页显示几条内容
            Page<MoocFilmT> page = new Page<>(1, nums);
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            filmVo.setFilmInfos(filmInfos);
        } else {
            //如果不是,则是列表页,同样需要限制内容为即将上映影片
            Page<MoocFilmT> page = new Page<>(nowPage, nums);
            //如果sourceId,yearId,catId 不为99,则表示要按照对应的编号进行查询
            if (sortId != 99) {
                entityWrapper.eq("film_source", sortId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            //数据库查出的集合
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            //组织filmInfos
            filmInfos = getFilmInfos(moocFilms);
            filmVo.setFilmNum(moocFilms.size());
            //totalPages 需要总页数
            //totalCounts/nums 总条数/每页显示几条
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts / nums) + 1;

            filmVo.setFilmInfos(filmInfos);
            filmVo.setTotalPage(totalPages);
            filmVo.setNowPage(nowPage);
        }


        return filmVo;

    }

    @Override
    public FilmVo getClassicFilmsMart(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        /**
         * @Description //TODO 获取经典影片
         @Author Leo
          * @Date 14:20 2020/4/27
         * @Param [nums, nowPage, sortId, sourceId, yearId, catId]
         * @return com.stylefeng.guns.api.user.file.vo.FilmVo
         */
        FilmVo filmVo = new FilmVo();

        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "3");

        //如果不是,则是列表页,同样需要限制内容为即将上映影片
        Page<MoocFilmT> page = new Page<>(nowPage, nums);
        //如果sourceId,yearId,catId 不为99,则表示要按照对应的编号进行查询
        if (sortId != 99) {
            entityWrapper.eq("film_source", sortId);
        }
        if (yearId != 99) {
            entityWrapper.eq("film_date", yearId);
        }
        if (catId != 99) {
            String catStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", catStr);
        }
        //数据库查出的集合
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        //组织filmInfos
        filmInfos = getFilmInfos(moocFilms);
        filmVo.setFilmNum(moocFilms.size());
        //totalPages 需要总页数
        //totalCounts/nums 总条数/每页显示几条
        int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts / nums) + 1;

        filmVo.setFilmInfos(filmInfos);
        filmVo.setTotalPage(totalPages);
        filmVo.setNowPage(nowPage);

        return filmVo;
    }

    @Override
    public FilmDetaVo getFilmDeta(String searchParam,int searchType) {
        /**
         * @Description //TODO 根据影片ID或者名称获取影片信息
         @Author Leo
          * @Date 21:56 2020/4/27
         * @Param [searchParam, searchType]
         * @return com.stylefeng.guns.api.user.file.vo.FilmDetaVo
         */
        //searchType 1-按名称 2-按id查找
        FilmDetaVo filmDetaVo = null;
        if (searchType == 1) {
            filmDetaVo = moocFilmTMapper.getFilmDetaByName("%"+searchParam+"%");
        } else {
            filmDetaVo = moocFilmTMapper.getFilmDetaById(searchParam);
        }

        return filmDetaVo;
    }

    @Override
    public FileDescVo getFilmDesc(String filmId) {
        /**
         * @Description //TODO 获取影片的描述信息
         @Author Leo
          * @Date 22:44 2020/4/27
         * @Param [filmId]
         * @return com.stylefeng.guns.api.user.file.vo.FileDescVo
         */
        //  MoocFilmInfoT 影片主表对象
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        FileDescVo fileDescVo = new FileDescVo();
        fileDescVo.setBiography(moocFilmInfoT.getBiography());
        fileDescVo.setFilmId(filmId);
        return fileDescVo;
    }

    @Override
    public ImgVo getImgs(String filmId) {
        /**
         * @Description //TODO 获取图片信息
         @Author Leo
          * @Date 22:44 2020/4/27
         * @Param [filmId]
         * @return com.stylefeng.guns.api.user.file.vo.ImgVo
         */
        //  MoocFilmInfoT 影片主表对象
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        String filmImgStr = moocFilmInfoT.getFilmImgs();
        //按逗号进行拆分
        String[] filmImgs = filmImgStr.split(",");

        ImgVo imgVo = new ImgVo();
        imgVo.setMainImg(filmImgs[0]);
        imgVo.setImg01(filmImgs[1]);
        imgVo.setImg02(filmImgs[2]);
        imgVo.setImg03(filmImgs[3]);
        imgVo.setImg04(filmImgs[4]);

        return imgVo;
    }

    @Override
    public ActorVo getDectInfo(String filmId) {
        /**
         * @Description //TODO 获取导演信息
         @Author Leo
          * @Date 22:44 2020/4/27
         * @Param [filmId]
         * @return com.stylefeng.guns.api.user.file.vo.ActorVo
         */
        //  MoocFilmInfoT 影片主表对象
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        //获取导演id
        Integer directorId = moocFilmInfoT.getDirectorId();

        MoocActorT moocActorT = moocActorTMapper.selectById(directorId);
        ActorVo actorVo = new ActorVo();
        actorVo.setImgAddress(moocActorT.getActorImg());
        actorVo.setDirectorName(moocActorT.getActorName());

        return actorVo;
    }

    @Override
    public List<ActorVo> getActors(String filmId) {
        /**
         * @Description //TODO 获取演员信息
           @Author Leo
          *@Date 22:45 2020/4/27
         * @Param [filmId]
         * @return java.util.List<com.stylefeng.guns.api.user.file.vo.ActorVo>
         */
        List<ActorVo> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }

    //影片主表对象
    private MoocFilmInfoT getFilmInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);
        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);
        return moocFilmInfoT;
    }


}



