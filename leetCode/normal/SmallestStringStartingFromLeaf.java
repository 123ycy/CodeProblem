package normal;

import dependency.TreeNode;

/**
 * @ClassName: SmallestStringStartingFromLeaf
 * @Description: 988. 从叶结点开始的最小字符串
 * @Author: zhaooo
 * @Date: 2022/9/3/11:03
 */
public class SmallestStringStartingFromLeaf {
    StringBuilder res;

    /**
     * 暴力
     * 模板解法 全部遍历一次
     * https://leetcode.cn/problems/longest-univalue-path/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-94j7/
     * @param root
     * @return
     */
    public String smallestFromLeaf(TreeNode root) {
        dfs(root, new StringBuilder());
        return res.reverse().toString();
    }

    void dfs(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }
        sb.append(((char) (root.val + 'a')));
        if (root.left == null && root.right == null) {
            res = getMin(res, sb);
        }
        dfs(root.left, new StringBuilder(sb));
        dfs(root.right, new StringBuilder(sb));
    }

    StringBuilder getMin(StringBuilder s1, StringBuilder s2) {
        if (s1 == null) {
            return s2;
        }
        for (int i = s1.length() - 1, j = s2.length() - 1; i >= 0 && j >= 0; i--, j--) {
            if (s1.charAt(i) < s2.charAt(j)) {
                return s1;
            }
            if (s1.charAt(i) > s2.charAt(j)) {
                return s2;
            }
        }
        return s1.length() < s2.length() ? s1 : s2;
    }

    // ~在ASCII码表中是126
    String ans = "~";

    /**
     * 暴力 细节优化
     * @param root
     * @return
     */
    public String smallestFromLeaf2(TreeNode root) {
        dfs(root, new StringBuilder());
        return ans;
    }

    public void dfs2(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append((char) ('a' + node.val));
        if (node.left == null && node.right == null) {
            sb.reverse();
            String cur = sb.toString();
            sb.reverse();
            // String实现了Comparable<String>
            if (cur.compareTo(ans) < 0) {
                ans = cur;
            }
        }
        dfs2(node.left, sb);
        dfs2(node.right, sb);
        // 回溯
        sb.deleteCharAt(sb.length() - 1);
    }
}
