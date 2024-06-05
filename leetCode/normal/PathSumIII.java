package normal;

import dependency.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PathSumIII
 * @Description: 437. 路径总和 III
 * @Author: zhaooo
 * @Date: 2022/9/2/12:36
 */
public class PathSumIII {
    int res = 0;

    /**
     * 模板解法
     * https://leetcode.cn/problems/longest-univalue-path/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-94j7/
     * 二重递归
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        // 以该节点为根
        dfs(root, targetSum);
        // 以其他节点为根
        pathSum(root.left, targetSum);
        pathSum(root.right, targetSum);
        return res;
    }

    // 包含根节点的dfs
    // long 避免溢出
    public void dfs(TreeNode root, long sum) {
        if (root == null) {
            return;
        }
        sum -= root.val;
        if (sum == 0) {
            res++;
        }
        dfs(root.left, sum);
        dfs(root.right, sum);
    }

    //<前缀和，其出现次数>
    Map<Long, Integer> map = new HashMap<>();
    int targetSum;

    /**
     * 前缀和
     *
     * 前缀和的概念：一个节点的前缀和就是该节点到根之间的路径和。
     * 前缀和的意义：因为对于同一路径上的两个节点，上面的节点是下面节点的祖先节点，所以其前缀和之差即是这两个节点间的路径和（不包含祖先节点的值）。
     * 哈希map的使用：key是前缀和， value是该前缀和的节点数量，记录数量是因为有出现复数路径的可能。
     * 回溯的意义：因为只有同一条路径上的节点间（节点和其某一祖先节点间）的前缀和做差才有意义。
     *          所以当前节点处理完之后，需要从map中移除这一个前缀和，然后再进入下一个分支路径。不可能从上方又到下方
     *
     * 链接：https://leetcode.cn/problems/path-sum-iii/solution/rang-ni-miao-dong-de-hui-su-qian-zhui-he-ou6t/
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum2(TreeNode root, int targetSum) {
        this.targetSum = targetSum;
        // 前缀和0的有一次（空） 初始情况
        map.put(0L,1);
        dfs2(root, 0);
        return res;
    }

    public void dfs2(TreeNode root, long curSum) {
        if (root == null) {
            return;
        }
        curSum += root.val;
        // 当前路径中存在以当前节点为终点的和为curSum的子路径即 0->...->cur-targetSum->...->curSum
        res += map.getOrDefault(curSum - targetSum, 0);
        // 更新
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);
        // 递归
        dfs2(root.left, curSum);
        dfs2(root.right, curSum);
        // 回溯
        map.put(curSum, map.get(curSum) - 1);
    }
}
