package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: FilmInfoVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/28-19:31
 * email 1437665365@qq.com
 */
@Data
public class FilmInfoVo implements Serializable {

    private String filmId;
    private String filmName;
    private String filmLength;
    private String filmType;
    private String filmCats;
    private String actors;
    private String imgAddress;
    private List<FilmFieldVo> filmFields;

}
