package com.stylefeng.guns.rest.modular.file.vo;

import com.stylefeng.guns.api.user.file.vo.CatVo;
import com.stylefeng.guns.api.user.file.vo.SourceVo;
import com.stylefeng.guns.api.user.file.vo.YearVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: FilmConditionVo
 * Description: TODO 影片条件VO
 * Author: Leo
 * Date: 2020/4/27-12:47
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmConditionVo implements Serializable {

    private List<CatVo> catInfo;

    private List<SourceVo> sourceInfo;

    private List<YearVo> yearInfo;

}
