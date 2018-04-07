package com.imooc.utils;

import com.imooc.enums.CodeEnum;

/**
 * @author hellozjf
 * @date 2018/4/7 16:24
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
