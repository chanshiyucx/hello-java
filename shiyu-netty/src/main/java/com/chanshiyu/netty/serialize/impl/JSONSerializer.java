package com.chanshiyu.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.chanshiyu.netty.serialize.Serializer;
import com.chanshiyu.netty.serialize.SerializerAlgorithm;

/**
 * @author shiyu
 * @date 2019/9/12 10:49
 * @Description: 反序列化消息数据
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public String serializeJSON(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, String text) {
        return JSON.parseObject(text, clazz);
    }

}
