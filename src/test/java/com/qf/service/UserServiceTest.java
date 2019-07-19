package com.qf.service;

import com.qf.AcTests;
import com.qf.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author bbj 2019/7/15 15:05
 * @version 1.0
 */
public class UserServiceTest extends AcTests {

    @Autowired
    private UserService userService;

    @Test
    public void findCountByUsername() {
        Integer count = userService.findCountByUsername("admin");
        System.out.println(count);
    }

    @Test
    @Transactional
    public void register() {
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("xxxxx");
        user.setPhone("12312312312");
        Integer count = userService.register(user);
        System.out.println(count);
        assertEquals(new Integer(1), count);
    }

    @Test
    public void login() {
        User user = userService.login("aa", "123");
        System.out.println(user);
    }
}