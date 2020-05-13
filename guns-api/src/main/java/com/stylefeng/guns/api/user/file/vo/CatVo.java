package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: CatVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-11:56
 * email 1437665365@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatVo implements Serializable {

    private String catId;

    private String catName;

    private boolean isActive;

}
