package com.qf.mapper;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author bbj 2019/7/11 11:55
 * @version 1.0
 */
public interface UserMapper {

    //1.校验,根据姓名查询个数
    Integer findCountByUsername(@Param("username") String username);

    //2.添加用户信息
    Integer save(User user);

    //3.根据用户名查询用户信息
    User findByUsername(@Param("username") String username);
}
