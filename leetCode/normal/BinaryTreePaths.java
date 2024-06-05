package normal;

import dependency.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BinaryTreePaths
 * @Description: 257. 二叉树的所有路径
 * @Author: zhaooo
 * @Date: 2022/9/2/11:05
 */
public class BinaryTreePaths {
    List<String> res = new ArrayList<>();

    /**
     * dfs 先添加root节点
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return res;
        }
        if (root.left == null && root.right == null) {
            res.add(String.valueOf(root.val));
            return res;
        }
        dfs(root.left, new StringBuilder().append(root.val));
        dfs(root.right, new StringBuilder().append(root.val));
        return res;
    }

    public void dfs(TreeNode root, StringBuilder path) {
        if (root == null) {
            return;
        }
        path.append("->").append(root.val);
        if (root.left == null && root.right == null) {
            res.add(path.toString());
            return;
        }
        dfs(root.left, new StringBuilder(path));
        dfs(root.right, new StringBuilder(path));
    }

    /**
     * 模板解法
     * dfs 优化->的添加方式
     * https://leetcode.cn/problems/longest-univalue-path/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-94j7/
     * @param root
     * @return
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        dfs2(root, new StringBuilder());
        return res;
    }

    public void dfs2(TreeNode root, StringBuilder path) {
        // 空节点返回
        if (root == null) {
            return;
        }
        // 执行操作
        path.append(root.val);
        // 如果是叶节点
        if (root.left == null && root.right == null) {
            // 添加答案
            res.add(path.toString());
            return;
        }
        path.append("->");
        // dfs
        // 每次重新开辟一个新StringBuilder
        dfs2(root.left, new StringBuilder(path));
        dfs2(root.right, new StringBuilder(path));
    }

    /**
     * 模板+回溯
     * @param root
     * @return
     */
    public List<String> binaryTreePaths3(TreeNode root) {
        dfs2(root, new StringBuilder());
        return res;
    }

    public void dfs3(TreeNode root, StringBuilder path) {
        if (root == null) {
            return;
        }
        path.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(path.toString());
            // 删除访问到当前节点增加的字符
            path.delete(path.length() - String.valueOf(root.val).length(), path.length());
            return;
        }

        path.append("->");
        // 不需要复制path，使用同一个path
        dfs3(root.left, path);
        dfs3(root.right, path);
        // 删除访问到当前节点增加的字符，2是“->”
        path.delete(path.length() - 2 - String.valueOf(root.val).length(), path.length());
    }
}
