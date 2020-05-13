package com.stylefeng.user.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.IFilmAsyncService;
import com.stylefeng.guns.api.user.file.vo.ActorVo;
import com.stylefeng.guns.api.user.file.vo.FileDescVo;
import com.stylefeng.guns.api.user.file.vo.ImgVo;
import com.stylefeng.user.persistence.dao.MoocActorTMapper;
import com.stylefeng.user.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.user.persistence.model.MoocActorT;
import com.stylefeng.user.persistence.model.MoocFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: FileAsyncServiceimpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-12:28
 * email 1437665365@qq.com
 */
@Component
@Service(interfaceClass = IFilmAsyncService.class)
public class FileAsyncServiceimpl implements IFilmAsyncService {

    //    影片主表 Mapper 接口
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;
    //    影片主表 Mapper 接口
    @Autowired
    private MoocActorTMapper moocActorTMapper;

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
