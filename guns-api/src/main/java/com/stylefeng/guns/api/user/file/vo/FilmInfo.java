package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: FilmInfo
 * Description: TODO 热映
 * Author: Leo
 * Date: 2020/4/26-20:32
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmInfo implements Serializable {

    private String filmId;

    private Integer filmType;

    private String imgAddress;

    private String filmName;

    private String filmScore;

    private Integer expectNum;

    private String showTime;

    private Integer boxNum;

    private String score;
}
