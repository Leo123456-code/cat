package com.stylefeng.user.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.IUserAPIService;
import com.stylefeng.guns.api.user.model.UserInfoModel;
import com.stylefeng.guns.api.user.model.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.user.persistence.dao.MoocUserTMapper;
import com.stylefeng.user.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: UserServiceimpl
 * Description: TODO 用户接口实现类 对外暴露接口的实现
 * Author: Leo
 * Date: 2020/4/25-14:56
 * email 1437665365@qq.com
 */
@Component
//roundrobin 轮询
@Service(interfaceClass = IUserAPIService.class,loadbalance = "roundrobin")
public class UserServiceimpl implements IUserAPIService {
    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username, String password) {
        /**
         * @Description //TODO 登录
         @Author Leo
          * @Date 15:12 2020/4/25
         * @Param [username, password]
         * @return int
         */
        //根据登录账号获取数据库信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);

        //将获取到的结果,然后与加密以后的密码做匹配
        if (result != null && result.getUuid() > 0) {
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password)) {
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        /**
         * @Description //TODO 注册
         @Author Leo
          * @Date 15:00 2020/4/25
         * @Param [userModel]
         * @return boolean
         */
        //获取注册信息

        //将注册信息实体转换为数据实体
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setEmail(userModel.getEmail());
        //密码加密[Md5混淆加密+盐值]
        String md5Password = MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(md5Password);
        //将数据实体存入数据库
        Integer rows = moocUserTMapper.insert(moocUserT);
        if (rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUsername(String username) {
        /**
         * @Description //TODO 验证是否存在用户
         @Author Leo
          * @Date 15:24 2020/4/25
         * @Param [username]
         * @return boolean
         */
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        //数据库字段,传入的参数
        entityWrapper.eq("user_name", username);
        //根据姓名查询是否存在
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if (result != null && result > 0) {
            //存在用户名
            return false;
        } else {
            //不存在
            return true;
        }
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        /**
         * @Description //TODO 根据主键用户信息
         @Author Leo
          * @Date 21:00 2020/4/25
         * @Param [uuid]
         * @return com.stylefeng.guns.api.user.model.UserInfoModel
         */
        //根据主键查询用户信息[MoocUserT]
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        //将MoocUserT转换UserInfoModel
        UserInfoModel userInfoModel = do2UserInfo(moocUserT);
        //返回UserInfoModel
        return userInfoModel;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        /**
         * @Description //TODO 修改用户信息
           @Author Leo
         * @Date 21:24 2020/4/25
         * @Param [userInfoModel]
         * @return com.stylefeng.guns.api.user.model.UserInfoModel
        */
        //将传入的数据转换为MoocUserT
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(null);
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(null);
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setUserPhone(userInfoModel.getPhone());

        //将数据存入数据库
        Integer result = moocUserTMapper.updateById(moocUserT);
        //按照id将用户信息查出来
        if(result > 0){
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            //返回给前端
            return userInfo;
        }else {
            //失败,返回null
            return null;
        }

    }

    //将时间Long类型转换为 Date 类型
    private Date time2Date(long time) {
        Date date = new Date(time);
        return date;
    }

    //将 MoocUserT  转换为 UserInfoModel
    private UserInfoModel do2UserInfo(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setUuid(moocUserT.getUuid());
        userInfoModel.setUsername(moocUserT.getUserName());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setNickname(moocUserT.getNickName());
        //字符串转换
        userInfoModel.setLifeState(""+moocUserT.getLifeState());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        return userInfoModel;
    }
}
