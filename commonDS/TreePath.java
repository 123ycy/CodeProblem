import dependency.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TreePath
 * @Description: 树的路径模板
 *
 * https://leetcode.cn/problems/longest-univalue-path/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-94j7/
 * https://leetcode.cn/problems/path-sum-iii/solution/rang-ni-miao-dong-de-hui-su-qian-zhui-he-ou6t/
 *
 * 一、自顶向下：
 * BinaryTreePaths                  257. 二叉树的所有路径
 * PathSumII                        113. 路径总和 II
 * PathSumIII                       437. 路径总和 III
 * SmallestStringStartingFromLeaf   988. 从叶结点开始的最小字符串
 *
 * 二、非自顶向下：
 * BinaryTreeMaximumPathSum         124. 二叉树中的最大路径和
 * LongestUnivaluePath              687. 最长同值路径
 * DiameterOfBinaryTree             543. 二叉树的直径
 *
 * 三、前缀和：
 * PathSumIII                       437. 路径总和 III
 *
 * @Author: zhaooo
 * @Date: 2022/9/3/14:35
 */
public class TreePath {
    List<List<Integer>> ret = new ArrayList<>();
    int res = 0;
    Map<Long, Integer> map = new HashMap<>();
    int targetSum;

    /**
     * 一般路径
     * @param root
     * @param path
     */
    void dfs1(TreeNode root, List<Integer> path) {
        if (root == null) {
            return;
        }
        // 执行对应操作
        path.add(root.val);
        // 如果是叶节点更新全局变量
        if (root.left == null && root.right == null) {
            ret.add(path);
            return;
        }
        // 递归
        // 复制path 保证进入其他路径时会出现不属于该路径的元素
        dfs1(root.left, new ArrayList<>(path));
        dfs1(root.right, new ArrayList<>(path));
    }

    /**
     * 一般路径（回溯）
     * @param root
     * @param path
     */
    void dfs2(TreeNode root, List<Integer> path) {
        if (root == null) {
            return;
        }
        // 执行对应操作
        path.add(root.val);
        // 如果是叶节点更新全局变量
        if (root.left == null && root.right == null) {
            ret.add(path);
            // 回溯
            path.remove(path.size() - 1);
            return;
        }
        // 递归
        // 不需要复制path
        dfs2(root.left, path);
        dfs2(root.right, path);
        // 回溯，删除本轮即本节点添加的元素
        // 与上方path.add(root.val)对应
        path.remove(path.size() - 1);
    }

    /**
     * 给定和的路径（有条件的路径）
     * 也可以如上dfs2 修改为回溯 例子：PathSumII
     * @param root
     * @param path
     * @param sum
     */
    void dfs3(TreeNode root, List<Integer> path, int sum) {
        if (root == null) {
            return;
        }
        // 执行对应操作
        path.add(root.val);
        sum -= root.val;
        // 如果是叶节点更新全局变量且满足对应路径条件
        if (root.left == null && root.right == null && sum == 0) {
            ret.add(path);
            return;
        }
        // 递归
        // 复制path 保证进入其他路径时会出现不属于该路径的元素
        dfs3(root.left, new ArrayList<>(path), sum);
        dfs3(root.right, new ArrayList<>(path), sum);
    }

    /**
     * 非自顶而下
     * dfs4:以root为路径起始点的最长路径
     * 例题 DiameterOfBinaryTree
     * @param root
     * @return
     */
    int dfs4(TreeNode root)
    {
        if (root == null) {
            return 0;
        }
        int left = dfs4(root.left);
        int right = dfs4(root.right);
        //更新全局变量
        res = Math.max(res, left + right + root.val);
        //返回左右路径较长者
        return Math.max(left, right);
    }

    /**
     * 前缀和，可以解决与路径和有关问题
     * 递归+回溯
     * 例题 PathSumIII
     * @param root
     * @param curSum
     */
    public void dfs5(TreeNode root, long curSum) {
        if (root == null) {
            return;
        }
        curSum += root.val;
        // 当前路径中存在以当前节点为终点的和为curSum的子路径即 0->...->cur-targetSum->...->curSum
        res += map.getOrDefault(curSum - targetSum, 0);
        // 更新
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);
        // 递归
        dfs5(root.left, curSum);
        dfs5(root.right, curSum);
        // 回溯
        map.put(curSum, map.get(curSum) - 1);
    }
}
