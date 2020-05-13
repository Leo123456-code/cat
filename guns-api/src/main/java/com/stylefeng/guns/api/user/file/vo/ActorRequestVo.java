package com.stylefeng.guns.api.user.file.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ActorRequestVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/27-23:25
 * email 1437665365@qq.com
 */
@Data
public class ActorRequestVo implements Serializable {

    private ActorVo director;

    private List<ActorVo> actors;
}
