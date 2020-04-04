package org.common.consistent;

import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法工具
 *
 * @author nurhier
 * @date 2020/2/11
 */
public class ConsistentHash<T> {

    private HashFunction hashFunction;

    private SortedMap<Long, VirtualNode<T>> hashRing = new TreeMap<>();

    public ConsistentHash(List<Node<T>> nodeList, int virtualNodeCount) {
        this(nodeList, virtualNodeCount, new MD5Hash());
    }

    public ConsistentHash(List<Node<T>> nodeList, int virtualNodeCount, HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        nodeList.forEach(node -> addNode(node, virtualNodeCount));
    }

    public Node<T> route(String key) {
        SortedMap<Long, VirtualNode<T>> tailMap = hashRing.tailMap(hash(key));
        Long hashRingKey;
        if (!tailMap.isEmpty()) {
            hashRingKey = tailMap.firstKey();
        } else {
            hashRingKey = hashRing.firstKey();
        }
        return hashRing.get(hashRingKey);
    }

    public void addNode(Node<T> node, int virtualNodeCount) {
        for (int index = 0; index < virtualNodeCount; index++) {
            VirtualNode<T> virtualNode = new VirtualNode<>(node, index);
            hashRing.put(hash(virtualNode.getKey()), virtualNode);
        }
    }

    public void removeNode(Node<T> node) {
        hashRing.entrySet().removeIf(entry -> isSameVirtualNode(entry.getValue(), node));
    }

    private boolean isSameVirtualNode(VirtualNode<T> target, Node<T> source) {
        return Objects.equals(target.getPhysicalNode().getKey(), source.getKey());
    }

    private Long hash(String key) {
        return hashFunction.hash(key);
    }

    @ToString(callSuper = true)
    private static class VirtualNode<T> extends Node<T> {
        /**
         * physical node
         */
        private Node<T> physicalNode;
        /**
         * virtual node code.
         * virtual node code will increase
         */
        private int code;

        public VirtualNode(Node<T> physicalNode, int code) {
            super(physicalNode.getKey() + ":" + code);
            this.physicalNode = physicalNode;
            this.code = code;
        }

        public Node<T> getPhysicalNode() {
            return physicalNode;
        }
    }
}
