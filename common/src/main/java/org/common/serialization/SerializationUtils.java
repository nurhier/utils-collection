package org.common.serialization;

/**
 * @author nurhier
 * @date 2020/1/21
 */
public class SerializationUtils {
    private SerializationUtils() {}

    public static SerializationUtils getInstance() {
        return new SerializationUtils();
    }
}
