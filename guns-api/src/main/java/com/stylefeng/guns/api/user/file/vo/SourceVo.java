package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: SourceVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-11:58
 * email 1437665365@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceVo implements Serializable {

    private String sourceId;

    private String sourceName;

    private boolean isActive;
}
