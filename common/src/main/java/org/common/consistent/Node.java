package org.common.consistent;

import lombok.Getter;
import lombok.ToString;

/**
 * virtual node
 *
 * @author nurhier
 * @date 2020/4/4
 **/
@Getter
@ToString
public class Node<T> {
    /**
     * node key
     */
    private String key;
    /**
     * node data
     */
    private T data;

    public Node(String key) {
        this.key = key;
    }

    public Node(String key, T data) {
        this.data = data;
        this.key = key;
    }
}
