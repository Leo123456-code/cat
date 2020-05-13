package com.stylefeng.guns.rest.modular.file.vo;

import com.stylefeng.guns.api.user.file.vo.BannerVo;
import com.stylefeng.guns.api.user.file.vo.FilmInfo;
import com.stylefeng.guns.api.user.file.vo.FilmVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: FileIndexVo
 * Description: 首页信息Vo
 * Author: Leo
 * Date: 2020/4/26-20:24
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileIndexVo implements Serializable {

    private List<BannerVo> banners;

    private FilmVo hotFilms;

    private FilmVo soonFilms;

    private List<FilmInfo> boxRanking;

    private List<FilmInfo> expectRanking;

    private List<FilmInfo> top100 ;


}
