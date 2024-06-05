/**
 * @ClassName: MyHashMap
 * @Description: hashMap简化版
 * @Author: zhaooo
 * @Date: 2022/8/1/22:38
 */
public class MyHashMap<K, V> {

    /**
     * 默认数组的容量
     */
    static final int DEFAULT_CAPACITY = 16;

    /**
     * 默认负载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 哈希表中数组的最大长度
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 真正存储数据的数组
     */
    Node<K, V>[] hashTable;

    /**
     * 哈希表数组当中存储的数据的个数
     */
    int size;

    /**
     * 哈希表当中的负载因子
     */
    float loadFactor;

    /**
     * 哈希表扩容的阈值 = 哈希表的长度 x 负载因子
     * 当超过这个值的时候进行扩容
     * hashTable初始化时再设置此值
     */
    int threshold;

    /**
     * 返回第一个大于或者等于 capacity 且为 2 的整数次幂的那个数
     * 即每次将最左的2的k次方个1再扩大一倍 直至最左至最右全是1
     * @param capacity
     * @return
     */
    static int roundUp(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        // 如果最终得到的数据小于 0 则初始长度为 1
        // 如果长度大于我们所允许的最大的容量 则将初始长度设置为我们所允许的最大的容量：1 << 30;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private static class Node<K, V> {
        /**
         * 用于存储我们计算好的 key 的哈希值
         */
        final int hash;

        /**
         * Key Value 中的 Key 对象
         */
        final K key;

        /**
         * Key Value 中的 Value 对象
         */
        V value;

        /**
         * hash 是键值 key 的哈希值  key 是键 value 是值
         * @param hash
         * @param key
         * @param value
         */
        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public V setValue(V newValue) {
            value = newValue;
            return newValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        //this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("初始化长度不能小于0");
        }
        if (loadFactor <= 0 || loadFactor > 1) {
            throw new IllegalArgumentException("负载因子必须在0到1之间");
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        initialCapacity = roundUp(initialCapacity);
        hashTable = (Node<K, V>[]) new Node[initialCapacity];
        this.loadFactor = loadFactor;
        threshold = (int) (loadFactor * initialCapacity);
    }

    public MyHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 静态hash方法
     * @param key
     * @return
     */
    static int hash(Object key) {
        int h;
        // 上面的函数之所以要将对象的哈希值右移16，是因为我们的数组的长度一般不会超过2^16，因为2^16已经是一个比较大的值了，
        // 因此当哈希值与2^n−1进行&操作的时候，高位通常没有使用到，这样做的原理是可以充分利用数据哈希值当中的信息。
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 添加node到hashTable，采用线性再探测法
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if (null == key) {
            throw new IllegalArgumentException("哈希表的键值不能为空");
        }
        int hash = hash(key);
        // 如果hashTable为空，即使用了无参构造函数，初始化hashTable并直接放入
        if (null == hashTable) {
            hashTable = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
            threshold = (int) (hashTable.length * loadFactor);
            hashTable[hash & (DEFAULT_CAPACITY - 1)] = new Node<>(hash, key, value);
        } else {
            int n = hashTable.length;
            int idx = hash & (n - 1);
            //线性再探测法
            //如果为空或者有相同key就退出循环
            while (null != hashTable[idx] && !key.equals(hashTable[idx].key)) {
                idx = (idx + 1) & (n - 1);
            }
            hashTable[idx] = new Node<>(hash, key, value);
        }
        if (++size > threshold) {
            resize();
        }
    }

    /**
     * 扩容函数
     * 每次取一个node放入扩容后的新hashTable
     */
    private void resize() {
        int n = (hashTable.length << 1);
        threshold = (int) (n * loadFactor);
        Node<K, V>[] oldTable = hashTable;
        hashTable = (Node<K, V>[]) new Node[n];
        for (Node<K, V> kvNode : oldTable) {
            if (null == kvNode) {
                continue;
            }
            int idx = kvNode.hash & (n - 1);
            // 线性再探测
            while (null != hashTable[idx] && !kvNode.key.equals(hashTable[idx].key)) {
                idx = (idx + 1) & (n - 1);
            }
            hashTable[idx] = kvNode;
        }
    }

    public V get(K key) {
        if (null == key) {
            throw new IllegalArgumentException("查询的键值不能为空");
        }
        int hash = hash(key);
        int n = hashTable.length;
        // 初始idx
        int idx = hash & (n - 1);
        if (null == hashTable[idx]) {
            return null;
        }
        //JDK源码中，无限循环大多使用for(;;)而不是while(true)
        for (; ; ) {
            // 找到一个节点的hash和key都与key相同
            if (null != hashTable[idx]
                    && hash == hashTable[idx].hash
                    && key.equals(hashTable[idx].key)) {
                break;
            }
            idx = (idx + 1) & (n - 1);
        }
        return hashTable[idx].value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Node<K, V> kvNode : hashTable) {
            builder.append(kvNode);
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");
        return builder.toString();
    }
}
