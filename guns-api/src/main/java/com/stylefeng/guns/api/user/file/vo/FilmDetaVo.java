package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: FilmDetaVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-21:50
 * email 1437665365@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDetaVo implements Serializable {

    private String filmId;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;

    private InfoRequestVo info04;

}
