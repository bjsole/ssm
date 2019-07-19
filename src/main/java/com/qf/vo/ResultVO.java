package com.qf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bbj 2019/7/15 14:52
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    private Integer code;
    private String msg;
    private Object data;
}
