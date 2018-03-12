package com.imooc.enums;

import lombok.Getter;

/**
 * @author hellozjf
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISH(1, "完结订单"),
    CANCEL(2, "已取消")
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
