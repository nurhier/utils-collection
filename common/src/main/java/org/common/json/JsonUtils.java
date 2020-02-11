package org.common.json;

import org.common.json.enums.JsonEnum;

/**
 * json 工具类
 *
 * @author nurhier
 * @date 2020/2/11
 */
public class JsonUtils {
    private JsonUtils() {}

    /**
     * bean to json string (default json service)
     *
     * @param bean bean
     * @return java.lang.String
     * @date 2020/2/11 21:34
     */
    public static <T> String toJSONString(T bean) {
        return JsonServiceContext.getJsonService().toJSONString(bean);
    }

    /**
     * json string to bean object (default json service)
     *
     * @param json  json
     * @param clazz clazz
     * @return T
     * @date 2020/2/11 21:34
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JsonServiceContext.getJsonService().parseObject(json, clazz);
    }

    /**
     * bean to json string (default json service)
     *
     * @param bean bean
     * @return java.lang.String
     * @date 2020/2/11 21:34
     */
    public static <T> String toJSONString(JsonEnum jsonEnum, T bean) {
        return toJSONString(jsonEnum.getClazz(), bean);
    }

    /**
     * json string to bean object (can choose json service by enum)
     *
     * @param json  json
     * @param clazz clazz
     * @return T
     * @date 2020/2/11 21:34
     */
    public static <T> T parseObject(JsonEnum jsonEnum, String json, Class<T> clazz) {
        return parseObject(jsonEnum.getClazz(), json, clazz);
    }

    /**
     * bean to json string (can choose json service by enum)
     *
     * @param jsonClazz json service class
     * @param bean      bean
     * @return java.lang.String
     * @date 2020/2/11 21:35
     */
    public static <T> String toJSONString(Class<? extends JsonService> jsonClazz, T bean) {
        try {
            return JsonServiceContext.getJsonService(jsonClazz).toJSONString(bean);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json string to bean object (can choose json service by class)
     *
     * @param jsonClazz json service class
     * @param json      json
     * @param clazz     clazz
     * @return T
     * @date 2020/2/11 21:36
     */
    public static <T> T parseObject(Class<? extends JsonService> jsonClazz, String json, Class<T> clazz) {
        try {
            return JsonServiceContext.getJsonService(jsonClazz).parseObject(json, clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
