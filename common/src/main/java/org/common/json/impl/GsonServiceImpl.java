package org.common.json.impl;

import com.google.gson.Gson;
import org.common.json.JsonService;

/**
 * google json service
 *
 * @author nurhier
 * @date 2020/1/19
 */
public class GsonServiceImpl implements JsonService {
    @Override
    public <T> String toJSONString(T bean) {
        Gson gson = new Gson();
        return gson.toJson(bean);
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
