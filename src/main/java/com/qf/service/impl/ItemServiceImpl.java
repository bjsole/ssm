package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author bbj 2019/7/15 14:39
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size) {

        Long total = itemMapper.getCountByName(name);

        //创建pageInfo
        PageInfo<Item> pageInfo = new PageInfo<>(page, size, total);

        List<Item> list = itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffset(), pageInfo.getSize());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    @Transactional
    public Integer save(Item item) {
        return itemMapper.save(item);
    }

    @Override
    @Transactional
    public Integer del(Long id) {
        return itemMapper.delById(id);
    }

    @Override
    public Item findById(Long id) {
        return itemMapper.findById(id);
    }

    @Override
    @Transactional
    public Integer update(Item item) {
        return itemMapper.update(item);
    }
}
