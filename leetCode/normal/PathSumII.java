package normal;

import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: PathSumII
 * @Description: 113. 路径总和 II 
 * @Author: zhaooo
 * @Date: 2022/9/2/11:49
 */
public class PathSumII {
    List<List<Integer>> res = new ArrayList<>();
    Deque<Integer> path = new ArrayDeque<>();

    /**
     * 模板解法
     * https://leetcode.cn/problems/longest-univalue-path/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-94j7/
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, new ArrayList<>(), targetSum);
        return res;
    }

    public void dfs(TreeNode root, List<Integer> path, int sum) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        sum -= root.val;
        if (root.left == null && root.right == null && sum == 0) {
            res.add(path);
            return;
        }
        dfs(root.left, new ArrayList<>(path), sum);
        dfs(root.right, new ArrayList<>(path), sum);
    }


    /**
     * 使用双端队列 避免多次复制
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        dfs2(root, targetSum);
        return res;
    }

    public void dfs2(TreeNode root, int sum) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        sum -= root.val;
        if (root.left == null && root.right == null && sum == 0) {
            res.add(new ArrayList<>(path));
        }
        // 如果当前节点还有孩子继续遍历
        dfs2(root.left, sum);
        // 如果在此处path.pollLast() 则会只删除左儿子
        dfs2(root.right, sum);
        // 到底删除当前元素 与前面offerLast结合
        path.pollLast();
    }
}
