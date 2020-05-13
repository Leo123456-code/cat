package com.stylefeng.guns.rest.common.util;

import lombok.Data;

import java.util.UUID;

/**
 * ClassName: UUIDUtil
 * Description: TODO
 * Author: Leo
 * Date: 2020/5/2-20:40
 * email 1437665365@qq.com
 */
@Data
public class UUIDUtil {

    public static final String genUUid(){
        return UUID.randomUUID().toString();
    }

}
