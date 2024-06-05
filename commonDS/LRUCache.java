import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: LruCache
 * @Description: LRU 缓存
 * 使用双向链表+hashMap实现 可用LinkedHashMap替换
 * @Author: zhaooo
 * @Date: 2022/7/8/17:02
 */
public class LRUCache {

    /**
     * 自定义双向链表
     */
    static class LRUCacheWithDoubleList {
        int capacity;
        Map<Integer, Node> map;
        DoubleList list;

        public LRUCacheWithDoubleList(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>(capacity);
            list = new DoubleList();
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            Node node = map.get(key);
            list.remove(node);
            list.addLast(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.val = value;
                // 放入队尾
                get(key);
            } else {
                Node node = new Node(key, value);
                if (list.size() == capacity) {
                    Node rm = list.removeFirst();
                    map.remove(rm.key);
                }
                map.put(key, node);
                list.addLast(node);
            }
        }

        static class Node {
            // 保存key是为了删除时可以找到map中的元素
            public int key, val;
            // 保存prev是为了在删除时可以找到要删除的元素即可
            public Node next, prev;

            public Node(int k, int v) {
                this.key = k;
                this.val = v;
            }
        }

        static class DoubleList {
            // 头尾虚节点
            private final Node head, tail;
            // 链表元素数
            private int size;

            public DoubleList() {
                // 初始化双向链表的数据
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
                size = 0;
            }

            // 在链表尾部添加节点 x，时间 O(1)
            public void addLast(Node x) {
                x.prev = tail.prev;
                x.next = tail;
                tail.prev.next = x;
                tail.prev = x;
                size++;
            }

            // 删除链表中的 x 节点（x 一定存在）
            // 由于是双链表且给的是目标 Node 节点，时间 O(1)
            public void remove(Node x) {
                x.prev.next = x.next;
                x.next.prev = x.prev;
                size--;
            }

            // 删除链表中第一个节点，并返回该节点，时间 O(1)
            public Node removeFirst() {
                if (head.next == tail) {
                    return null;
                }
                Node first = head.next;
                remove(first);
                return first;
            }

            // 返回链表长度，时间 O(1)
            public int size() {
                return size;
            }

        }
    }

    /**
     * 使用LinkedHashMap
     */
    static class LRUCacheWithLinkedHashMap extends LinkedHashMap<Integer, Integer> {
        private final int capacity;

        public LRUCacheWithLinkedHashMap(int capacity) {
            // accessOrder – the ordering mode - true for access-order, false for insertion-order
            // 即false按照插入顺序排序 true按照访问顺序排序
            // 如果accessOrder为true，则LinkedHashMap的遍历顺序将受到元素的访问顺序的影响。
            // 当一个元素被访问时，它会被移到双向链表的尾部，表示最近访问过。
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        // 这个可不写
        public void put(int key, int value) {
            super.put(key, value);
        }

        // removeEldestEntry在LinkedHashMap中的调用逻辑如下
        // void afterNodeInsertion(boolean evict) {
        //     LinkedHashMap.Entry<K,V> first;
        //     if (evict && (first = head) != null && removeEldestEntry(first)) {
        //         K key = first.key;
        //         removeNode(hash(key), key, null, false, true);
        //     }
        // }
        // 即removeEldestEntry为true就删除head
        // 其中成员变量head定义如下

        /**
         * The head (eldest) of the doubly linked list.
         */
        // transient LinkedHashMap.Entry<K,V> head;
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
}