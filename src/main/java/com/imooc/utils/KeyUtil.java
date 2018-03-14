package com.imooc.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * @author hellozjf
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        String sixNumber = RandomStringUtils.randomNumeric(6);
        return System.currentTimeMillis() + sixNumber;
    }
}
