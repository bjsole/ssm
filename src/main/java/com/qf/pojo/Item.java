package com.qf.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表对应的实体类
 */
@Data
public class Item {

    private Long id;

    @NotBlank(message = "商品名称为必填项..")
    private String name;

    @NotNull(message = "商品价格为必填项..")
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;

    @NotBlank(message = "商品描述为必填项..")
    private String description;

    @NotNull(message = "商品图片为必填项..")
    private MultipartFile picFile;

    private String pic;

    private Date created;

}
