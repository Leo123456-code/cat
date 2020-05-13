package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: HotFilmsVo
 * Description: TODO 热映 hotFilms
 * Author: Leo
 * Date: 2020/4/26-20:31
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmVo implements Serializable {

    private Integer filmNum;

    private int nowPage;

    private int totalPage;

    private List<FilmInfo> filmInfos;

}
