package com.imooc.constant;

/**
 * redis常量
 * @author hellozjf
 * @date 2018/4/8 20:21
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    // 2小时
    Integer EXPIRE = 7200;
}
