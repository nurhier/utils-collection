package org.common.generator;

import java.util.UUID;

/**
 * @author nurhier
 * @date 2020/2/7
 */
public class UUIDUtils {
    private UUIDUtils() {}

    /**
     * generate UUID no hyphen
     *
     * @return java.lang.String
     * @date 2020/2/7 21:00
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
