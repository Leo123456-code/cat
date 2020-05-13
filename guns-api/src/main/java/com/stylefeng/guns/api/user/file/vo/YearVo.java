package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: YearVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-12:00
 * email 1437665365@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearVo implements Serializable {

    private String yearId;

    private String yearName;

    private boolean isActive;

}
