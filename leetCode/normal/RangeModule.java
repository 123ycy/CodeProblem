package normal;


/**
 * @ClassName: RangeModule
 * @Description: 715. Range 模块
 * @Author: zhaooo
 * @Date: 2022/6/20/10:52
 */
class RangeModule {
    class Node {
        Node left, right;
        // -1表示该点表示的线段已删除
        // 0表示该点表示的线段部分删除
        // 1表示该点表示的线段完整存在
        int val;
        // -1表示删除标记
        // 1表示添加标记
        int lazy;
    }

    int N = (int) 1e9 + 10;
    Node root;

    public void update(Node node, int start, int end, int left, int right, int isAdded) {
        if (left <= start && end <= right) {
            node.val = isAdded;
            node.lazy = isAdded;
            return;
        }
        pushDown(node);
        int mid = (start + end) >> 1;
        if (mid >= left) {
            update(node.left, start, mid, left, right, isAdded);
        }
        if (mid < right) {
            update(node.right, mid + 1, end, left, right, isAdded);
        }
        pushUp(node);
    }

    public boolean query(Node node, int start, int end, int left, int right) {
        if (left <= start && end <= right) {
            return node.val == 1;
        }
        pushDown(node);
        int mid = (start + end) >> 1;
        boolean ans = true;
        if (mid >= left) {
            ans &= query(node.left, start, mid, left, right);
        }
        if (right > mid) {
            ans &= query(node.right, mid + 1, end, left, right);
        }
        return ans;
    }

    private void pushDown(Node node) {
        if (node.left == null) {
            node.left = new Node();
            node.left.val = node.val;
        }
        if (node.right == null) {
            node.right = new Node();
            node.right.val = node.val;
        }
        if (node.lazy != 0) {
            node.left.val = node.lazy;
            node.left.lazy = node.lazy;
            node.right.val = node.lazy;
            node.right.lazy = node.lazy;
            node.lazy = 0;
        }
    }

    private void pushUp(Node node) {
        int sumVar = node.left.val + node.right.val;
        // 如果左右孩子节点中有一个及以上的0则Math.abs(sumVar) < 2
        node.val = Math.abs(sumVar) < 2 ? 0 : sumVar > 0 ? 1 : -1;
    }

    public RangeModule() {
        root = new Node();
        root.val = -1;
    }

    public void addRange(int left, int right) {
        update(root, 1, N - 1, left, right - 1, 1);
    }

    public boolean queryRange(int left, int right) {
        return query(root, 1, N - 1, left, right - 1);
    }

    public void removeRange(int left, int right) {
        update(root, 1, N - 1, left, right - 1, -1);
    }
}
