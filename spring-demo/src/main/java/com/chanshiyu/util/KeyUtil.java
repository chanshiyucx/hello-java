package com.chanshiyu.util;

import java.util.Random;

/**
 * @author chanshiyu
 * @date 2019/8/27 10:12
 * @description 生成唯一主键
 */
public class KeyUtil {

    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

}
