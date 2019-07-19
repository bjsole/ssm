package com.qf.service;

import com.qf.pojo.Item;
import com.qf.util.PageInfo;

/**
 * @author bbj 2019/7/15 14:38
 * @version 1.0
 */
public interface ItemService {
    PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size);

    Integer save(Item item);

    Integer del(Long id);

    Item findById(Long id);

    Integer update(Item item);
}
