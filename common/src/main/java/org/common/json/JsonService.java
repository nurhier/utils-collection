package org.common.json;

public interface JsonService {
    /**
     * bean to json string
     *
     * @param bean bean
     * @return java.lang.String
     * @date 2020/1/19 9:39
     */
    <T> String toJSONString(T bean);

    /**
     * json string to bean
     *
     * @param json json
     * @param clazz clazz
     * @return T
     * @date 2020/1/19 9:40
     */
    <T> T parseObject(String json, Class<T> clazz);
}
