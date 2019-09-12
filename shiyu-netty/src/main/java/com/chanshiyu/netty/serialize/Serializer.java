package com.chanshiyu.netty.serialize;

import com.chanshiyu.netty.serialize.impl.JSONSerializer;

/**
 * @author shiyu
 * @date 2019/9/12 10:48
 * @Description: 序列化接口
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * java 对象转换成文本
     */
    String serializeJSON(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
    /**
     * 文本 java 对象
     */
    <T> T deserialize(Class<T> clazz, String text);

}
