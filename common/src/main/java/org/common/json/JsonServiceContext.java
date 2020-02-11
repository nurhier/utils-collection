package org.common.json;

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

    static JsonService getJsonService() {
        return jsonServiceList.get(0);
    }

    static JsonService getJsonService(Class<? extends JsonService> clazz) throws ClassNotFoundException {
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
