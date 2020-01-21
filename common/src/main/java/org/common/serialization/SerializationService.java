package org.common.serialization;

/**
 * @author nurhier
 * @date 2020/1/21
 */
public interface SerializationService {
    /**
     * serialize
     *
     * @param bean bean
     * @return byte[]
     * @date 2020/1/21 18:00
     */
    <T> byte[] serialize(T bean);

    /**
     * deserialize
     *
     * @param bytes bytes
     * @return java.lang.Object
     * @date 2020/1/21 18:00
     */
    Object deserialize(byte[] bytes);

    /**
     * deserialize
     *
     * @param bytes bytes
     * @param clazz clazz
     * @return T
     * @date 2020/1/21 18:00
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
