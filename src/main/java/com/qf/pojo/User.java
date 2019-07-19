package com.qf.pojo;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
public class User {

    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "手机号不能为空")
    private String phone;
    private Date created;

    @NotBlank(message = "验证码不能为空")
    private String registerCode;


}
