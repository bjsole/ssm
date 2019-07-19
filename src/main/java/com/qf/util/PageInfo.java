package com.qf.util;

import lombok.Data;

import java.util.List;

/**
 * 页面信息,商品信息
 *
 * @author bbj 2019/7/16 11:34
 * @version 1.0
 */
@Data
public class PageInfo<T> {

    //当前页        已知项
    private Integer page;

    //每页显示条数   已知项
    private Integer size;

    //总条数        已知项
    private Long total;

    //总页数         total%size==0?total/size:total/size+1
    private Integer pages;

    //起始索引      (page-1)*size
    private Integer offset;

    //商品信息       查询可得
    private List<T> list;


    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page < 1 ? 1 : page;
        this.size = size < 1 ? 5 : size;
        this.total = total < 0 ? 0 : total;
        this.pages = (int) (this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1);
        this.offset = (this.page - 1) * this.size;
    }
}
