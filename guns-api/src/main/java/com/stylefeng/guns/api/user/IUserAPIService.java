package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.model.UserInfoModel;
import com.stylefeng.guns.api.user.model.UserModel;

/**
 * @program: renren-generator
 * @ClassName: UserAPI
 * @Description: TODO 用户模块接口
 * @Author: Leo
 * @Date: 2020/4/19-22:56
 */
public interface IUserAPIService {
     //是否登录
    int login(String username,String password);
    //是否注册
    boolean register(UserModel userModel);
    //验证是否存在用户
    boolean checkUsername(String username);
    //当前用户信息查询接口
    UserInfoModel getUserInfo(int uuid);
    //修改用户信息
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);



}
