package org.common.json;

import org.common.json.enums.JsonEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class JsonServiceContext {
    private static Map<String, JsonService> jsonServiceMap = new HashMap<>();
    private static List<JsonService> jsonServiceList = new ArrayList<>();
    private static ServiceLoader<JsonService> jsonServiceLoader = ServiceLoader.load(JsonService.class);

    static {
        for (JsonService jsonService : jsonServiceLoader) {
            jsonServiceList.add(jsonService);
            String jsonServiceClassName = jsonService.getClass().getName();
            jsonServiceMap.put(jsonServiceClassName, jsonService);
        }
    }

    public static JsonService getJsonService() {
        return jsonServiceList.get(0);
    }

    public static JsonService getJsonService(JsonEnum jsonEnum) {
        if (jsonEnum == null || jsonEnum.getClazz() == null) {
            return jsonServiceList.get(0);
        }
        return jsonServiceMap.get(jsonEnum.getClazz().getName());
    }

    public static JsonService getJsonService(Class<JsonService> clazz) throws ClassNotFoundException {
        if (clazz == null) {
            return jsonServiceList.get(0);
        }
        JsonService jsonService = jsonServiceMap.get(clazz.getName());
        if (jsonService == null) {
            throw new ClassNotFoundException(clazz.getName());
        }
        return jsonService;
    }
}
