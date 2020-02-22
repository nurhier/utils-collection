package org.common.consistent;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 一致性hash算法工具
 *
 * @author nurhier
 * @date 2020/2/11
 */
public class ConsistentHashUtils {
    private ConsistentHashUtils() {}

    private Map<Long, Node> hashRing = new TreeMap<>();

    public void addNode(Node node, int virtualNodeCount) {
        int replicasIndex;
        for (int index = 0; index < virtualNodeCount; index++) {
            
        }
        hashRing.put(hash(node.getKey()), node);
    }

    public void removeNode(Node node) {
        hashRing.entrySet().removeIf(entry -> isVirtualNode(entry.getValue(), node));
    }

    private boolean isVirtualNode(Node target, Node source) {
        return Objects.equals(target.getKey(), source.getKey());
    }

    private Long hash(String key) {
        return Long.decode(key);
    }
}
