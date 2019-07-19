package com.qf.mapper;

import com.qf.AcTests;
import com.qf.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author bbj 2019/7/15 15:02
 * @version 1.0
 */
public class UserMapperTest extends AcTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findCountByUsername() {
        Integer count = userMapper.findCountByUsername("admin123");
        System.out.println(count);
    }

    @Test
    @Transactional
    public void save() {
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("xxxxx");
        user.setPhone("12312312312");
        Integer count = userMapper.save(user);
        System.out.println(count);
        assertEquals(new Integer(1), count);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("admin");
        System.out.println(user);
    }
}