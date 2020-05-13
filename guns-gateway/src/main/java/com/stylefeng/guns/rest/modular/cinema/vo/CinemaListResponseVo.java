package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.user.cinema.vo.CinemaVo;
import lombok.Data;

import java.util.List;

/**
 * ClassName: CinemaListResponse
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/1-21:49
 * email 1437665365@qq.com
 */
@Data
public class CinemaListResponseVo {
    private List<CinemaVo> cinemas;
}
