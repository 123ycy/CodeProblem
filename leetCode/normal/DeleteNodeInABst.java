package normal;

import dependency.TreeNode;

/**
 * @ClassName : DeleteNodeInABst
 * @Description: 450. 删除二叉搜索树中的节点
 * @Author zhaooo
 * @Date 2022/6/2/11:06
 */
public class DeleteNodeInABst {
    /**
     * 递归
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        // root.val == key
        // 为叶节点,直接删除
        if (root.left == null && root.right == null) {
            return null;
        }
        // 只有右子树,续接
        if (root.right == null) {
            return root.left;
        }
        // 只有左子树,续接
        if (root.left == null) {
            return root.right;
        }
        // 左右子树都存在
        // 寻找右子树最左节点
        TreeNode successor = root.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        // 在右边删除最左节点(successor)
        root.right = deleteNode(root.right, successor.val);
        // successor替代原root
        successor.right = root.right;
        successor.left = root.left;
        return successor;
    }


    /**
     * 迭代
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode2(TreeNode root, int key) {
        // 找到key对应的node和parent
        TreeNode cur = root, curParent = null;
        while (cur != null && cur.val != key) {
            curParent = cur;
            if (cur.val > key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        // 找不到key
        if (cur == null) {
            return root;
        }
        // cur为叶节点
        if (cur.left == null && cur.right == null) {
            cur = null;
            // 只有一个子节点
        } else if (cur.right == null) {
            cur = cur.left;
        } else if (cur.left == null) {
            cur = cur.right;
            // 左右子树都存在
        } else {
            // 找到右子树最左节点
            TreeNode successor = cur.right, successorParent = cur;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            // successor没有左子树(successorParent = cur)
            if (successorParent.val == cur.val) {
                // 续接cur的右子树
                successorParent.right = successor.right;
            } else {
                // 续接successor的left
                successorParent.left = successor.right;
            }
            // 替换successor和cur
            successor.right = cur.right;
            successor.left = cur.left;
            cur = successor;
        }
        // cur是根节点
        if (curParent == null) {
            return cur;
        } else {
            // 删除key对应的node
            if (curParent.left != null && curParent.left.val == key) {
                curParent.left = cur;
            } else {
                curParent.right = cur;
            }
            return root;
        }
    }


    /**
     * 递归+迭代
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode3(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        TreeNode keyNode = null, keyPre = null, tmp = root;
        while (tmp != null) {
            if (tmp.val == key) {
                keyNode = tmp;
                break;
            }
            keyPre = tmp;
            if (tmp.val < key) {
                tmp = tmp.right;
            } else {
                tmp = tmp.left;
            }
        }
        // 找不到key
        if (keyNode == null) {
            return root;
        }
        // 只有一个节点,且该节点是key
        if (keyPre == null && root.left == null && root.right == null) {
            return null;
        }
        deleteNode(keyNode, keyPre);
        return root;
    }

    public void deleteNode(TreeNode node, TreeNode pre) {
        if (node == null) {
            return;
        }
        TreeNode newPre = null, newNode = null;
        // node有左子树
        if (node.left != null) {
            newPre = node;
            newNode = node.left;
            // 找左子树的最右节点
            while (newNode.right != null) {
                newPre = newNode;
                newNode = newNode.right;
            }
            // node有右子树
        } else if (node.right != null) {
            newPre = node;
            newNode = node.right;
            // 找右子树的最左节点
            while (newNode.left != null) {
                newPre = newNode;
                newNode = newNode.left;
            }
            // node为叶节点
        } else {
            if (pre.left == node) {
                pre.left = null;
            } else {
                pre.right = null;
            }
            return;
        }
        int newNodeVal = newNode.val;
        deleteNode(newNode, newPre);
        node.val = newNodeVal;
    }
}
