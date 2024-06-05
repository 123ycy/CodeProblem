import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

/**
 * @ClassName: Skiplist
 * @Description: 跳表
 * @Author: zhaooo
 * @Date: 2022/7/26/15:15
 */
class Skiplist {

    private Node head;
    private Random random;

    public Skiplist() {
        this.head = new Node(-1, null, null);
        this.random = new Random();
    }

    /**
     * 从头节点开始遍历，直到找到目标值，如果当前层没找到，到下一层继续找
     * @param target
     * @return
     */
    public boolean search(int target) {
        Node curr = head;
        while (curr != null) {
            while (curr.next != null && curr.next.val < target) {
                curr = curr.next;
            }
            if (curr.next != null && curr.next.val == target) {
                return true;
            }
            curr = curr.down;
        }
        return false;
    }

    /**
     * 使用栈记录，要添加的值的前一个节点和其下层节点，之后构建新节点插入当前层，并根据随机值决定是否添加到下一层，最后判断是否需要拉高最上层的head
     * @param num
     */
    public void add(int num) {
        Node curr = head;
        // 记录最后一层到第一层可以插入num的路径（curr.next为num）
        Deque<Node> queue = new ArrayDeque<>();
        while (curr != null) {
            while (curr.next != null && curr.next.val < num) {
                curr = curr.next;
            }
            queue.offerLast(curr);
            curr = curr.down;
        }

        boolean isInsert = true;
        Node down = null;
        // 从最后一层不断出栈
        while (isInsert && !queue.isEmpty()) {
            curr = queue.pollLast();
            curr.next = new Node(num, curr.next, down);
            // down是当前插入节点
            down = curr.next;
            isInsert = random.nextDouble() < 0.5;
        }
        // 顶层
        if (isInsert) {
            head = new Node(-1, null, head);
        }
    }

    /**
     * 从头节点开始遍历，直到找到目标值，对其节点进行删除，然后对下层继续遍历，判断是否需要删除。
     * @param num
     * @return
     */
    public boolean erase(int num) {
        Node curr = head;
        boolean isFound = false;
        while (curr != null) {
            // 本层未找到则进入下一层
            while (curr.next != null && curr.next.val < num) {
                curr = curr.next;
            }
            // 如果已经找到该节点则curr.next = num
            if (isFound || (curr.next != null && curr.next.val == num)) {
                curr.next = curr.next.next;
                isFound = true;
            }
            curr = curr.down;
        }
        return isFound;
    }

    class Node {
        private int val;
        private Node next, down;

        public Node(int val, Node next, Node down) {
            this.val = val;
            this.next = next;
            this.down = down;
        }
    }
}

/*
 *
 * 按照论文写法
 * 优化 https://leetcode.cn/problems/design-skiplist/solution/she-ji-tiao-biao-by-leetcode-solution-e8yh/
 * 论文 https://15721.courses.cs.cmu.edu/spring2018/papers/08-oltpindexes1/pugh-skiplists-cacm1990.pdf
 *
class Skiplist {
    static final int MAX_LEVEL = 32;
    static final double P_FACTOR = 0.25;
    private SkiplistNode head;
    private int level;
    private Random random;

    public Skiplist() {
        this.head = new SkiplistNode(-1, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }

    public boolean search(int target) {
        SkiplistNode curr = this.head;
        for (int i = level - 1; i >= 0; i--) {
            // 找到第 i 层小于且最接近 target 的元素
            while (curr.forward[i] != null && curr.forward[i].val < target) {
                curr = curr.forward[i];
            }
        }
        curr = curr.forward[0];
        // 检测当前元素的值是否等于 target
        return curr != null && curr.val == target;
    }

    public void add(int num) {
        SkiplistNode[] update = findPath(num, true);
        int lv = randomLevel();
        level = Math.max(level, lv);
        SkiplistNode newNode = new SkiplistNode(num, lv);
        for (int i = 0; i < lv; i++) {
            // 对第 i 层的状态进行更新，将当前元素的 forward 指向新的节点
            newNode.forward[i] = update[i].forward[i];
            // newNode插入update和其forward之间
            update[i].forward[i] = newNode;
        }
    }

    public boolean erase(int num) {
        SkiplistNode[] update = findPath(num, false);
        // curr为path最底层的下一个
        SkiplistNode curr = update[0].forward[0];
        // 如果值不存在则返回 false
        if (curr == null || curr.val != num) {
            return false;
        }
        for (int i = 0; i < level; i++) {
            if (update[i].forward[i] != curr) {
                break;
            }
            // 对第 i 层的状态进行更新，将 forward 指向被删除节点的下一跳
            update[i].forward[i] = curr.forward[i];
        }
        // 更新当前的 level
        while (level > 1 && head.forward[level - 1] == null) {
            level--;
        }
        return true;
    }


    private int randomLevel() {
        int lv = 1;
        // 随机生成 level，每一次判断random.nextDouble() < P_FACTOR如果是则加一层
        while (random.nextDouble() < P_FACTOR && lv < MAX_LEVEL) {
            lv++;
        }
        return lv;
    }

    private SkiplistNode[] findPath(int target, boolean isAdd) {
        // path是寻找num的路径即update的每一个值都是不大于num的最大值
        SkiplistNode[] path = new SkiplistNode[MAX_LEVEL];
        if (isAdd) {
            Arrays.fill(path, head);
        }
        SkiplistNode curr = this.head;
        for (int i = level - 1; i >= 0; i--) {
            // 找到第 i 层小于且最接近 num 的元素
            while (curr.forward[i] != null && curr.forward[i].val < target) {
                curr = curr.forward[i];
            }
            path[i] = curr;
        }
        return path;
    }

    class SkiplistNode {
        int val;
        // forward指向每个节点的下一个节点,第0层为全部元素
        SkiplistNode[] forward;

        public SkiplistNode(int val, int maxLevel) {
            this.val = val;
            this.forward = new SkiplistNode[maxLevel];
        }
    }
}
 */