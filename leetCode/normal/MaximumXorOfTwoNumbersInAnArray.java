package normal;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MaximumXorOfTwoNumbersInAnArray
 * @Description: 421. 数组中两个数的最大异或值
 * @Author: zhaooo
 * @Date: 2023/12/22 10:21
 */
public class MaximumXorOfTwoNumbersInAnArray {
    int ret = 0;

    /**
     * Trie + dfs
     * Trie（可以理解为二叉树）保存所有nums，然后dfs获取异或最大值
     * @param nums
     * @return
     */
    public int findMaximumXOR(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        // 所有位的1的或
        int bits = 0;
        // 最高位的1的位置，即所有num的同一长度，不够长num补充前导0
        int len = 0;
        Node root = new Node();
        // 或
        for (int num : nums) {
            bits |= num;
        }
        // 求最高位
        while (bits > 0) {
            len++;
            bits >>= 1;
        }
        // 将所有num按高位到低位的顺序插入二叉树
        for (int num : nums) {
            Node tmp = root;
            // 从高位开始插入，因为高位异或为1的值更大
            for (int i = len - 1; i >= 0; i--) {
                // 该位为0插入左边
                if ((num & (1 << i)) == 0) {
                    tmp = tmp.left == null ? tmp.left = new Node() : tmp.left;
                    // 为1插入右边
                } else {
                    tmp = tmp.right == null ? tmp.right = new Node() : tmp.right;
                }
            }
        }
        // 从第一个存在不同的位开始dfs
        while (root.left == null || root.right == null) {
            root = root.left == null ? root.right : root.left;
            len--;
        }
        dfs(root.left, root.right, len, 1);
        return ret;
    }

    void dfs(Node l, Node r, int count, int val) {
        // 可以优化为ret是2^(len-1)即1<<len-1时退出，此时有2个数所有位都不同，ret一定是最大值
        if (--count == 0) {
            ret = Math.max(ret, val);
            return;
        }
        if (l.left != null) {
            // 由于每个节点都能访问到底，因为所有num的同一长度，不够长num补充前导0
            // 故判断完r.right == null时便可以确定访问dfs的参数，当前结果是(val<<1)+节点代表0/1的异或
            dfs(l.left, r.right == null ? r.left : r.right, count, (val << 1) + (r.right == null ? 0 : 1));
        }
        if (l.right != null) {
            // 同上
            dfs(l.right, r.left == null ? r.right : r.left, count, (val << 1) + (r.left == null ? 0 : 1));
        }
    }

    class Node {
        Node left;
        Node right;
    }

    /**
     * 哈希表+遍历
     * https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/solutions/778291/shu-zu-zhong-liang-ge-shu-de-zui-da-yi-h-n9m9
     * 该回答中也有使用trie枚举a_i找到每个a_i对应的最大值
     */
    // 最高位的二进制位编号为 30
    static final int HIGH_BIT = 30;

    public int findMaximumXOR2(int[] nums) {
        int x = 0;
        for (int k = HIGH_BIT; k >= 0; --k) {
            Set<Integer> seen = new HashSet<>();
            // 将所有的 pre^k(a_j) 放入哈希表中
            for (int num : nums) {
                // 如果只想保留从最高位开始到第 k 个二进制位为止的部分
                // 只需将其右移 k 位
                seen.add(num >> k);
            }

            // 目前 x 包含从最高位开始到第 k+1 个二进制位为止的部分
            // 我们尝试将 x 的第 k 个二进制位置为 1，即为 x = x*2+1，
            // 此时xNext的位长度同num>>k
            int xNext = (x << 1) + 1;
            boolean found = false;

            // 枚举 i
            for (int num : nums) {
                // xNext = (pre^k(a_i)) ^ (pre^k(a_j)) --> xNext ^ (pre^k(a_i)) = (pre^k(a_j))
                // seen.contains(xNext ^ (pre^k(a_i))) = seen.contains(pre^k(a_j)) 即存在一个pre^k(a_i)使以上等式成立，即x 的第 k 个二进制位置可以为 1
                // 即pre^k(a_i)和pre^k(a_j)的异或能使第k位为1
                if (seen.contains(xNext ^ (num >> k))) {
                    found = true;
                    break;
                }
            }

            if (found) {
                x = xNext;
            } else {
                // 如果没有找到满足等式的 a_i 和 a_j，那么 x 的第 k 个二进制位只能为 0
                // 即为 x = x*2
                x = xNext - 1;
            }
        }
        return x;
    }

    /**
     * https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/solutions/2511644/tu-jie-jian-ji-gao-xiao-yi-tu-miao-dong-1427d
     * 优化上面回答
     * 将上面的先添加所有a_i再枚举a_j 改为 同时枚举a_i和a_j，因为a_i和a_j的顺序没有影响
     * @param nums
     * @return
     */
    public int findMaximumXOR3(int[] nums) {
        int ans = 0, mask = 0, bits = 0, len = 0;
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            bits |= num;
        }
        while (bits > 0) {
            len++;
            bits >>= 1;
        }
        // 从最高位开始枚举
        for (int i = len - 1; i >= 0; i--) {
            seen.clear();
            mask |= 1 << i;
            // 尝试赋值该位为1
            int addOne = ans | (1 << i);
            for (int x : nums) {
                // 低于 i 的比特位置为 0
                x &= mask;
                // 已经添加的num（a_i）和该num（a_j）可以异或出addOne
                if (seen.contains(addOne ^ x)) {
                    // 这个比特位可以是 1，将 ans 更新为 ans | (1 << i)
                    ans = addOne;
                    break;
                }
                seen.add(x);
            }
        }
        return ans;
    }
}
