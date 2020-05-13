package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: FilmRequestVo 影片查询接口表单
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-13:58
 * email 1437665365@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmRequestVo implements Serializable {

    //查询类型
    private Integer showType=1;
    //排序方式
    private Integer sortId=1;
    //类型编号
    private Integer catId=99;
    //区域编号
    private Integer sourceId=99;
    //年代编号
    private Integer yearId=99;
    //影片列表当前页，翻页使用
    private Integer nowPage=1;
    //每页显示条数
    private Integer pageSize=18;
}
