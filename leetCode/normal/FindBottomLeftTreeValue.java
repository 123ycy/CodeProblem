package normal;

import dependency.TreeNode;

import java.util.HashMap;

/**
 * @ClassName: FindBottomLeftTreeValue
 * @Description: 513. 找树左下角的值
 * @Author: zhaooo
 * @Date: 2022/6/23/9:56
 */
public class FindBottomLeftTreeValue {
    HashMap<TreeNode, Integer> map = new HashMap<>();

    /**
     * 使用hashMap存储 2次遍历
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        high(root);
        return dfs1(root, 0);
    }

    public int dfs1(TreeNode node, int pre) {
        if (node == null) {
            return pre;
        }
        if (map.getOrDefault(node.left, 0) < map.getOrDefault(node.right, 0)) {
            return dfs1(node.right, node.val);
        }
        return dfs1(node.left, node.val);
    }

    public int high(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int h = Math.max(high(node.left), high(node.right)) + 1;
        map.put(node, h);
        return h;
    }

    int curVal = 0;
    int curHeight = 0;

    /**
     * 一次遍历
     * @param root
     * @return
     */
    public int findBottomLeftValue2(TreeNode root) {
        dfs(root, 0);
        return curVal;
    }

    public void dfs(TreeNode root, int height) {
        if (root == null) {
            return;
        }
        height++;
        // 先left保证同等高度left优先
        dfs(root.left, height);
        dfs(root.right, height);
        //使用>不用>=
        if (height > curHeight) {
            curHeight = height;
            curVal = root.val;
        }
    }
}
