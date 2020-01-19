package org.common.json.enums;

import org.common.json.impl.GsonServiceImpl;
import org.common.json.impl.FastJsonServiceImpl;

/**
 * json implement
 *
 * @author nurhier
 * @date 2020/1/19
 */
public enum JsonEnum {
    /**
     * ali fast json
     */
    FAST_JSON(FastJsonServiceImpl.class),
    /**
     * google json
     */
    GSON(GsonServiceImpl.class);

    private Class<?> clazz;

    JsonEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
