package normal;

import dependency.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FindDuplicateSubtrees
 * @Description: 652. 寻找重复的子树 
 * @Author: zhaooo
 * @Date: 2022/9/5/10:38
 */
public class FindDuplicateSubtrees {
    Map<String, Integer> map = new HashMap<>();
    List<TreeNode> result = new ArrayList<>();

    /**
     * hashMap储存结构+递归
     * https://leetcode.cn/problems/find-duplicate-subtrees/solution/by-muse-77-lsy1/
     * @param root
     * @return
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        dfs(root);
        return result;
    }

    public String dfs(TreeNode node) {
        if (node == null) {
            return "";
        }
        // 先序变量获取当前路径以及结构
        String key = new StringBuilder().append(node.val).append(',').
                append(dfs(node.left)).append(',').append(dfs(node.right)).toString();
        // 如果当前路径和结构已经出现一次 即是重复的子树
        if (map.getOrDefault(key, 0) == 1) {
            result.add(node);
        }
        map.put(key, map.getOrDefault(key, 0) + 1);
        return key;
    }
}
