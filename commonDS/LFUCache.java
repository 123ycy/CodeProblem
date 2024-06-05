import java.util.*;

/**
 * @ClassName: LFUCache
 * @Description: LFU 缓存
 * 双向链表直接使用LinkedHashSet
 * https://leetcode.cn/problems/lfu-cache/solutions/48636/java-13ms-shuang-100-shuang-xiang-lian-biao-duo-ji/
 * @Author: zhaooo
 * @Date: 2023/04/11 19:01
 */
class LFUCache {
    // 映射node
    Map<Integer, Node> nodeMap;
    // 存储每个频次与双向链表的映射
    // LinkedHashSet底层是一个 LinkedHashMap，底层维护了一个数组+双向链表。
    // 它根据元素的hashCode值来决定元素的存储位置,同时使用链表维护元素的次序, 这使得元素看起来是以插入顺序保存的。
    Map<Integer, LinkedHashSet<Node>> freqMap;
    int capacity;
    // 存储当前最小频次
    int min;

    public LFUCache(int capacity) {
        nodeMap = new HashMap<>(capacity);
        freqMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = nodeMap.get(key);
        if (node == null) {
            return -1;
        }
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        Node node = nodeMap.get(key);
        // 如果有该节点访问次数+1，修改value即可
        if (node != null) {
            node.value = value;
            freqInc(node);
        } else {
            // 满了删除最老节点
            if (nodeMap.size() == capacity) {
                Node delNode = removeEldestNode();
                nodeMap.remove(delNode.key);
            }
            Node newNode = new Node(key, value);
            addOneNode(newNode);
            nodeMap.put(key, newNode);
        }
    }

    /**
     * 将当前节点的频次+1
     *
     * @param node
     */
    void freqInc(Node node) {
        // 从原freq对应的链表里移除, 并更新min
        int freq = node.freq;
        LinkedHashSet<Node> set = freqMap.get(freq);
        set.remove(node);
        if (freq == min && set.isEmpty()) {
            min = freq + 1;
        }
        // 加入新freq对应的链表
        node.freq++;
        LinkedHashSet<Node> newSet = freqMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>());
        newSet.add(node);
    }

    /**
     * 添加freq=1的新节点
     *
     * @param node
     */
    void addOneNode(Node node) {
        LinkedHashSet<Node> set = freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>());
        set.add(node);
        min = 1;
    }

    Node removeEldestNode() {
        LinkedHashSet<Node> set = freqMap.get(min);
        Node delNode = set.iterator().next();
        // 使用链表维护元素的次序, 这使得元素看起来是以插入顺序保存的，故删除的是的一个插入的即最旧的元素
        set.remove(delNode);
        return delNode;
    }

    class Node {
        int key;
        int value;
        int freq;

        public Node() {
            this.freq = 1;
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
}