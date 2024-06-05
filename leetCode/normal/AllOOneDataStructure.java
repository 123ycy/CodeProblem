package normal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: AllOOneDataStructure
 * @Description: 432. 全 O(1) 的数据结构
 * @Author: zhaooo
 * @Date: 2023/04/11 12:47
 */
public class AllOOneDataStructure {
    /**
     * 与LRUCache差不多
     */
    class AllOne {
        Node root;
        Map<String, Node> nodes;

        public AllOne() {
            root = new Node();
            // 初始化链表哨兵，下面判断节点的 next 若为 root，则表示 next 为空（prev 同理）
            root.next = root;
            root.prev = root;
            nodes = new HashMap<>();
        }

        public void inc(String key) {
            if (nodes.containsKey(key)) {
                Node cur = nodes.get(key);
                Node nxt = cur.next;
                // 需要重新创建node
                if (nxt == root || nxt.count > cur.count + 1) {
                    nodes.put(key, cur.insert(new Node(key, cur.count + 1)));
                } else {
                    // nxt.count = cur.count + 1
                    nxt.keys.add(key);
                    nodes.put(key, nxt);
                }
                // 从当前node的keys中删除当前key
                cur.keys.remove(key);
                if (cur.keys.isEmpty()) {
                    cur.remove();
                }
                // key不在nodes中
            } else {
                if (root.next == root || root.next.count > 1) {
                    nodes.put(key, root.insert(new Node(key, 1)));
                } else {
                    root.next.keys.add(key);
                    nodes.put(key, root.next);
                }
            }
        }

        public void dec(String key) {
            Node cur = nodes.get(key);
            // key 仅出现一次，将其移出 nodes
            if (cur.count == 1) {
                nodes.remove(key);
            } else {
                Node pre = cur.prev;
                // 需要重新创建node
                if (pre == root || pre.count < cur.count - 1) {
                    nodes.put(key, cur.prev.insert(new Node(key, cur.count - 1)));
                } else {
                    // pre.count = cur.count - 1
                    pre.keys.add(key);
                    nodes.put(key, pre);
                }
            }
            cur.keys.remove(key);
            if (cur.keys.isEmpty()) {
                cur.remove();
            }
        }

        public String getMaxKey() {
            return root.prev != null ? root.prev.keys.iterator().next() : "";
        }

        public String getMinKey() {
            return root.next != null ? root.next.keys.iterator().next() : "";
        }
    }

    class Node {
        Node prev;
        Node next;
        // 将count相同的key聚集在一起，这样count变化时只需要O(1)的复杂度就可以寻找比它大/小的
        Set<String> keys;
        int count;

        public Node() {
            this("", 0);
        }

        public Node(String key, int count) {
            this.count = count;
            keys = new HashSet<>();
            keys.add(key);
        }

        // 在this后插入 node
        public Node insert(Node node) {
            node.prev = this;
            node.next = this.next;
            node.prev.next = node;
            node.next.prev = node;
            return node;
        }

        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
    }
}
