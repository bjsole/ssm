package com.qf.service;

import com.qf.pojo.User;

/**
 * @author bbj 2019/7/15 14:38
 * @version 1.0
 */
public interface UserService {


    Integer findCountByUsername(String username);

    Integer register(User user);

    User login(String username, String password);
}
