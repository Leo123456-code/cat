package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.net.www.content.image.png;

import java.io.Serializable;

/**
 * ClassName: ImgVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-22:29
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgVo implements Serializable {

    private String mainImg;

    private String img01;

    private String img02;

    private String img03;

    private String img04;
}
