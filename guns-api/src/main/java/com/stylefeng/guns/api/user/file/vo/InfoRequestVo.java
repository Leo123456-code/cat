package com.stylefeng.guns.api.user.file.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: InfoRequestVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-23:28
 * email 1437665365@qq.com
 */
@Data
public class InfoRequestVo implements Serializable {

    private String biography;
    private ActorRequestVo actors;
    private ImgVo imgs;
    private String filmId ;
}
