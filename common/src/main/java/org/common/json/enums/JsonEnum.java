package org.common.json.enums;

import org.common.json.JsonService;
import org.common.json.impl.FastJsonServiceImpl;
import org.common.json.impl.GsonServiceImpl;

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

    private Class<? extends JsonService> clazz;

    JsonEnum(Class<? extends JsonService> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends JsonService> getClazz() {
        return clazz;
    }
}
