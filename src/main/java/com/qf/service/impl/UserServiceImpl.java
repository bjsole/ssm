package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author bbj 2019/7/15 14:38
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer findCountByUsername(String username) {
        //健壮性代码
        if (!StringUtils.isEmpty(username)) {
            username = username.trim();
        }
        return userMapper.findCountByUsername(username);
    }

    @Override
    public Integer register(User user) {
        //对密码加密
        String md5 = new Md5Hash(user.getPassword(), null, 1024).toString();
        user.setPassword(md5);
        //调用mapper保存数据
        return userMapper.save(user);
    }

    @Override
    public User login(String username, String password) {
        //1.根据用户名查询用户信息
        User user = userMapper.findByUsername(username);
        //2.判断查询的结果是否为null
        if (user != null) {
            //2.1如果不为null,判断密码
            if (user.getPassword().equals(new Md5Hash(password, null, 1024).toString())) {
                //3.密码正确,返回查询到的user,
                return user;
            }
        }
        //其他情况
        return null;
    }
}
