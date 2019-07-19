package com.qf.mapper;

import com.qf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author bbj 2019/7/11 20:50
 * @version 1.0
 */
public interface ItemMapper {

    //查询总条数
    Long getCountByName(@Param("name") String name);

    //分页查询商品信息
    List<Item> findItemByNameLikeAndLimit(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("size") Integer size);

    //添加商品信息
    Integer save(Item item);

    //删除商品
    Integer delById(@Param("id") Long id);

    //根据id查询商品
    Item findById(@Param("id") Long id);

    //更新商品信息
    Integer update(Item item);

}
