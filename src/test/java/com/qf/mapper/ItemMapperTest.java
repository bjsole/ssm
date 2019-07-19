package com.qf.mapper;

import com.qf.AcTests;
import com.qf.pojo.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author bbj 2019/7/16 11:55
 * @version 1.0
 */
public class ItemMapperTest extends AcTests {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void getCountByName() {
        Long count = itemMapper.getCountByName(null);
        System.out.println(count);
    }

    @Test
    public void findItemByNameLikeAndLimit() {
        List<Item> list = itemMapper.findItemByNameLikeAndLimit(null, 0, 5);
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Test
    @Transactional
    public void save() {
        Item item = new Item();
        item.setName("测试");
        item.setPrice(new BigDecimal(1000));
        item.setProductionDate(new Date());
        item.setDescription("xasdfasdfasdfasf");
        item.setPic("yyyyyy");
        Integer count = itemMapper.save(item);
        assertEquals(new Integer(1), count);
    }

    @Test
    @Transactional
    public void delById() {
        Integer count = itemMapper.delById(17L);
        assertEquals(new Integer(1), count);
    }

    @Test
    public void findById() {
        Item item = itemMapper.findById(8L);
        System.out.println(item);
    }

    @Test
    @Transactional
    public void update() {
        Item item = new Item();
        item.setId(11L);
        item.setName("测试");
        item.setPrice(new BigDecimal(1111));
        item.setProductionDate(new Date());
        item.setDescription("xasdfasdfasdfasf");
        item.setPic("yyyyyy");
        Integer count = itemMapper.update(item);
        System.out.println(count);
    }
}