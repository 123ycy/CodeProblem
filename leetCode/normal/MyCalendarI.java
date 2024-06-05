package normal;

/**
 * @ClassName: MyCalendarI
 * @Description: 729. 我的日程安排表 I
 * @Author: zhaooo
 * @Date: 2022/7/6/10:21
 */
public class MyCalendarI {
    /**
     * 线段树-动态开点
     */
    class MyCalendar {

        Node root;
        final int L, R;

        public MyCalendar() {
            root = new Node();
            L = 0;
            R = (int) 1e9;
        }

        public boolean book(int l, int r) {
            if (query(root, L, R, l, r - 1) == 0) {
                update(root, L, R, l, r - 1, 1);
                return true;
            }
            return false;
        }

        class Node {
            Node left;
            Node right;
            int val, add;
        }

        // 在区间 [start, end] 中更新区间 [l, r] 的结果
        public void update(Node node, int start, int end, int l, int r, int val) {
            // 要更新的区间在该点内部时懒更新
            if (l <= start && end <= r) {
                node.val += (end - start + 1) * val;
                node.add = val;
                return;
            }
            int mid = (start + end) >> 1;
            // 开点和更新
            pushDown(node, mid - start + 1, end - mid);
            // 递归查询
            // [l,r]包含该点的左孩子
            if (l <= mid) {
                update(node.left, start, mid, l, r, val);
            }
            // [l,r]包含该点的右孩子
            if (r > mid) {
                update(node.right, mid + 1, end, l, r, val);
            }
            // 向上更新结果
            pushUp(node);
        }

        public int query(Node node, int start, int end, int l, int r) {
            //
            if (l <= start && end <= r) {
                return node.val;
            }
            int mid = (start + end) >> 1, ans = 0;
            // 开点和更新
            pushDown(node, mid - start + 1, end - mid);
            // 结果可以拆分为不同区间的结果
            // [l,r]包含该点的左孩子
            if (l <= mid) {
                ans += query(node.left, start, mid, l, r);
            }
            // [l,r]包含该点的右孩子
            if (r > mid) {
                ans += query(node.right, mid + 1, end, l, r);
            }
            return ans;
        }

        private void pushUp(Node node) {
            node.val = node.left.val + node.right.val;
        }

        private void pushDown(Node node, int leftNum, int rightNum) {
            if (node.left == null) {
                node.left = new Node();
            }
            if (node.right == null) {
                node.right = new Node();
            }
            if (node.add == 0) {
                return;
            }
            node.left.val += node.add * leftNum;
            node.right.val += node.add * rightNum;
            node.left.add = node.add;
            node.right.add = node.add;
            node.add = 0;
        }
    }
}
