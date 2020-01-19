package org.common.json;

import org.common.json.enums.JsonEnum;

public class JsonUtils {
    private JsonUtils() {}

    private static JsonService jsonService;

    public static JsonUtils getInstance() {
        jsonService = JsonServiceContext.getJsonService();
        return new JsonUtils();
    }

    public static JsonUtils getInstance(Class<JsonService> clazz) {
        try {
            jsonService = JsonServiceContext.getJsonService(clazz);
            return new JsonUtils();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonUtils getInstance(JsonEnum jsonEnum) {
        jsonService = JsonServiceContext.getJsonService(jsonEnum);
        return new JsonUtils();
    }

    public <T> String toJSONString(T bean) {
        return jsonService.toJSONString(bean);
    }

    public <T> T parseObject(String json, Class<T> clazz) {
        return jsonService.parseObject(json, clazz);
    }
}
