package normal;

import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: SerializeAndDeserializeBinaryTree
 * @Description: 297. 二叉树的序列化与反序列化
 * @Author: zhaooo
 * @Date: 2023/11/25 14:36
 */
public class SerializeAndDeserializeBinaryTree {
    /**
     * bfs+层序遍历
     */
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            Deque<TreeNode> deque = new ArrayDeque<>();
            deque.addLast(root);
            sb.append(root.val).append(",");
            while (!deque.isEmpty()) {
                TreeNode node = deque.removeFirst();
                if (node.left != null) {
                    sb.append(node.left.val).append(",");
                    deque.addLast(node.left);
                } else {
                    sb.append("null,");
                }
                if (node.right != null) {
                    sb.append(node.right.val).append(",");
                    deque.addLast(node.right);
                } else {
                    sb.append("null,");
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data.isEmpty()) {
                return null;
            }
            String[] nodes = data.split(",");
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
            Deque<TreeNode> deque = new ArrayDeque<>();
            deque.addLast(root);
            for (int i = 1; i < nodes.length; ) {
                TreeNode node = deque.removeFirst();
                if (!nodes[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(nodes[i]));
                    deque.addLast(node.left);
                }
                i++;
                if (!nodes[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(nodes[i]));
                    deque.addLast(node.right);
                }
                i++;
            }
            return root;
        }
    }

    /**
     * dfs+先序遍历
     */
    public class Codec2 {
        int idx;

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            return serialize(root, new StringBuilder()).toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            idx = 0;
            String[] dataArray = data.split(",");
            return deserialize(dataArray);
        }

        private TreeNode deserialize(String[] dataList) {
            if ("#".equals(dataList[idx])) {
                idx++;
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(dataList[idx++]));
            root.left = deserialize(dataList);
            root.right = deserialize(dataList);
            return root;
        }

        public StringBuilder serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#,");
            } else {
                sb.append(root.val).append(",");
                serialize(root.left, sb);
                serialize(root.right, sb);
            }
            return sb;
        }
    }
}
