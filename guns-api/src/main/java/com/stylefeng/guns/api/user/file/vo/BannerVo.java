package com.stylefeng.guns.api.user.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: BannerVo
 * Description: BannerVo
 * Author: Leo
 * Date: 2020/4/26-20:25
 * email 1437665365@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerVo implements Serializable {

    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;
}
