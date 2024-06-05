/**
 * @ClassName: SegmentTreeDynamic
 * @Description: 线段树（动态开点）
 * 使用该数据结构的题:
 *  MyCalendarI
 *  RangeModule
 *  RectangleAreaII
 * @Author: zhaooo
 * @Date: 2022/6/20/12:14
 */
public class SegmentTreeDynamic {
    class Node {
        Node left, right;
        // val是该节点的值，本代码中是该线段的总和
        // add是懒标记即该点区间内每个点要更新的值
        int val, add;
    }

    // 在区间 [l, r] 中更新区间 [start, end] 的结果
    // Node范围是[start, end]，更新范围是[l, r]
    public void update(Node node, int start, int end, int l, int r, int add) {
        // [l, r]包裹[start, end]（Node）时才能更新值
        if (l <= start && end <= r) {
            node.val += (end - start + 1) * add;
            // 也可为node.add=add 因为原add要么为0（每次向下都要pushDown使用懒标记），要么已经包含在node.val里
            // 更新懒标记
            node.add += add;
            return;
        }
        // [l, r]不能完全包裹[start, end]（Node）
        // 取Node的中点，分别递归更新左右子树
        int mid = (start + end) >> 1;
        // 开点和使用懒标记，注意此时还未更新Node的值，在pushUp中更新
        pushDown(node, mid - start + 1, end - mid);
        // [l,r]和Node的左孩子有交集
        if (l <= mid) {
            update(node.left, start, mid, l, r, add);
        }
        // [l,r]和Node的右孩子有交集
        if (r > mid) {
            update(node.right, mid + 1, end, l, r, add);
        }
        // 向上更新结果
        pushUp(node);
    }

    public int query(Node node, int start, int end, int l, int r) {
        if (l <= start && end <= r) {
            return node.val;
        }
        int mid = (start + end) >> 1, ans = 0;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) {
            ans += query(node.left, start, mid, l, r);
        }
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
        // 对某个节点的懒标记进行下放时，左右儿子的标记也要更新，并且该节点的标记值要清零，以防重复下放
        node.left.val += node.add * leftNum;
        node.right.val += node.add * rightNum;
        node.left.add = node.add;
        node.right.add = node.add;
        node.add = 0;
    }
}
