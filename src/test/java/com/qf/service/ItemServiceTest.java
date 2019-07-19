package com.qf.service;

import com.qf.AcTests;
import com.qf.pojo.Item;
import com.qf.util.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author bbj 2019/7/16 12:02
 * @version 1.0
 */
public class ItemServiceTest extends AcTests {

    @Autowired
    private ItemService itemService;

    @Test
    public void findItemAndLimit() {
        PageInfo<Item>  pageInfo = itemService.findItemAndLimit(null, 1, 5);
        System.out.println(pageInfo);
        for (Item item : pageInfo.getList()) {
            System.out.println(item);
        }
    }

    @Test
    @Transactional
    public void save() {
        Item item = new Item();
        item.setName("测试");
        item.setPrice(new BigDecimal(1231));
        item.setProductionDate(new Date());
        item.setDescription("xasdfasdfasdfasf");
        item.setPic("yyyyyy");
        Integer count = itemService.save(item);
        assertEquals(new Integer(1), count);
    }

    @Test
    @Transactional
    public void del() {
        Integer count = itemService.del(17L);
        assertEquals(new Integer(1), count);
    }

    @Test
    public void findById() {
        Item item = itemService.findById(8L);
        System.out.println(item);
    }

    @Test
    @Transactional
    public void update() {
        Item item = new Item();
        item.setId(11L);
        item.setName("");
        item.setPrice(new BigDecimal(1111));
        item.setProductionDate(new Date());
        item.setDescription("xasdfasdfasdfasf");
        item.setPic("yyyyyy");
        Integer count = itemService.update(item);
        System.out.println(count);
    }
}