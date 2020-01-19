package org.common.json.impl;

import com.alibaba.fastjson.JSON;
import org.common.json.JsonService;

public class FastJsonServiceImpl implements JsonService {
    @Override
    public <T> String toJSONString(T bean) {
        return JSON.toJSONString(bean);
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
