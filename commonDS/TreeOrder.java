import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: TreeOrder
 * @Description: 树的遍历
 * @Author: zhaooo
 * @Date: 2022/7/13/10:55
 */
public class TreeOrder {
    // =============前序=============
    // 前序递归
    public void preOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        preOrder(result, node.left);
        preOrder(result, node.right);
    }

    // 前序栈
    public void preOrder2(List<Integer> result, TreeNode node) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(node);
        // 每次取栈顶元素加入结果，再右孩子入栈，左孩子入栈（左孩子先访问）
        while (!stack.isEmpty()) {
            TreeNode tmpNode = stack.removeLast();
            result.add(tmpNode.val);
            if (tmpNode.right != null) {
                stack.addLast(tmpNode.right);
            }
            if (tmpNode.left != null) {
                stack.addLast(tmpNode.left);
            }
        }
    }

    // 前序Morris
    public void preOrder3(List<Integer> result, TreeNode root) {
        // Morris遍历加工成先序遍历的规则是：
        // https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/124293e937744525b074ecbde829640d~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                // 如果一个节点只能被访问一次，被访问时打印。
                result.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode pre = findPredecessor(cur);
                if (pre.right == null) {
                    // 如果一个节点能被访问两次，在第一次被访问时打印。
                    result.add(cur.val);
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    cur = cur.right;
                }
            }
        }
    }

    // =============中序=============
    // 中序递归
    public void inOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(result, node.left);
        result.add(node.val);
        inOrder(result, node.right);
    }

    // 中序栈
    public void inOrder2(List<Integer> result, TreeNode node) {
        // 1、申请一个栈stack，初始时令cur=head
        // 2、先把cur压入栈中，依次把左边界压入栈中，即不停的令cur=cur.left，重复步骤2（找到最左节点）
        // 3、不断重复2，直到为null，从stack中弹出一个节点，记为node，打印node的值，并令cur=node.right,重复步骤2（访问最左节点的右孩子，即向下）
        // 4、当stack为空且cur为空时，整个过程停止。
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (node != null || !stack.isEmpty()) {
            // 找到最左节点
            while (node != null) {
                stack.addLast(node);
                node = node.left;
            }
            // 没有左孩子就弹出
            node = stack.removeLast();
            // 左孩子已经访问过，现在访问根节点（左，根，右）
            result.add(node.val);
            // 左节点已经访问了
            node = node.right;
        }
    }

    // 中序Morris
    public void inOrder3(List<Integer> result, TreeNode root) {
        TreeNode cur = root;
        // Morris遍历加工成中序遍历的规则是：
        // https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/01fee57320464eb394640c0c47865d68~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?
        while (cur != null) {
            if (cur.left == null) {
                // 如果一个节点只能被访问一次，被访问时打印。
                result.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode pre = findPredecessor(cur);
                if (pre.right == null) {
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    // 如果一个节点能被访问两次，在第二次被访问时打印。
                    result.add(cur.val);
                    cur = cur.right;
                }
            }
        }
    }

    // =============后序=============
    // 后序递归
    public void postOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(result, node.left);
        postOrder(result, node.right);
        result.add(node.val);
    }

    // 后序栈
    public void postOrder2(List<Integer> result, TreeNode node) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode visited = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.addLast(node);
                node = node.left;
            }
            // 最左节点（无左孩子）
            node = stack.removeLast();
            // 每次访问时要判断该节点有没有右孩子
            // 如果没有右孩子或者右孩子已经访问，就向上返回并添加当前结果
            if (node.right == null || node.right == visited) {
                // 向上返回时设置当前节点以访问
                visited = node;
                result.add(node.val);
                node = null;
                // 否则向右重复过程
            } else {
                // 把右孩子的父节点添加上
                stack.addLast(node);
                node = node.right;
            }
        }
    }

    // 后序Morris
    public void postOrder3(List<Integer> result, TreeNode root) {
        TreeNode cur = root;
        // Morris遍历加工成中序遍历的规则是：
        // https://mp.weixin.qq.com/s?__biz=MzU0ODMyNDk0Mw==&mid=2247495649&idx=1&sn=29ef6461e8ae4e57832b14a5aa7712ee&chksm=fb427cc1cc35f5d706aba5f916889635c9991c4105c9deae704775fdf48113633f11b0d7612f&scene=27
        // 如下图所示，最先打印的是节点 4 ，这个是节点 2 的左子节点，接着打印的是节点 [5，2]，这个是节点 1 的左子节点往右走的逆序 …… ，所以我们发现如果当前节点没有左子节点不需要打印，如果有左子节点，直接打印左子节点一直往右走的逆序就行了。
        // https://mmbiz.qpic.cn/sz_mmbiz_png/PGmTibd8KQBG2Hu232qB6dauru7lWegFiaibduRmO0wbchT3Hgd154ic8LwMdw5lNvQKb2AUMrWgY1L6jTtjcXURJg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1
        while (cur != null) {
            if (cur.left == null) {
                // 如果当前节点 cur 没有左子节点，不用管。
                cur = cur.right;
            } else {
                TreeNode pre = findPredecessor(cur);
                if (pre.right == null) {
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    // 如果当前节点 cur 有左子节点，需要在第二次访问 cur 的时候打印"左子节点一直往右走的逆序"。
                    reverseAdd(result, cur.left);
                    cur = cur.right;
                }
            }
        }
        // 根节点没有父节点，所以最右侧的不会被打印到，在最后的时候要单独打印。
        reverseAdd(result, root);
    }

    // =============层序=============
    // 层序队列
    public void levelOrder(List<Integer> result, TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            result.add(node.val);
            if (node.right != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.left);
            }
        }
    }

    // =============Morris=============
    /**
     * Morris遍历
     * https://juejin.cn/post/7021341254457753631
     * <a href="https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/33c213e8ce2b4a309c51be107daef566~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp">...</a>?
     * <p>
     * 1、如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
     * 2、如果当前节点的有左孩子，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
     * <p>
     * a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
     * 相当于按中序遍历将父节点的前一个节点父节点连接（前一个节点的右孩子指后一个节点（父节点）），能在遍历完子树后回到父节点
     * 如果此时断开父节点的左孩子链接，只有中序遍历的顺序链接父节点和左子树，相当于链化一棵树，节点的右孩子变为next指针
     * <p>
     * b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。
     * 遍历完子树后回到父节点，父节点二次访问
     * <p>
     * 3、重复以上1、2直到当前节点为空。
     * <p>
     * 访问顺序相当于【根左根右】
     * <p>
     * 在Morris遍历的过程中，如果一个节点有左孩子，一定能访问到两次（回到父节点）；如果一个节点没有左孩子，只能访问到一次。
     */
    public void morris(List<Integer> result, TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            result.add(cur.val);
            if (cur.left == null) {
                cur = cur.right;
            } else {
                TreeNode pre = findPredecessor(cur);
                if (pre.right == null) {
                    // 如果一个节点能被访问两次，在第一次被访问时打印。
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    cur = cur.right;
                }
            }
        }
    }

    private TreeNode findPredecessor(TreeNode node) {
        TreeNode current = node.left;
        while (current.right != null && current.right != node) {
            current = current.right;
        }
        return current;
    }

    private void reverseAdd(List<Integer> posList, TreeNode node) {
        // 这里并不是直接逆序打印，而是在打印的时候往前插入，
        // 比如要逆序打印1，2，3。首先打印 1 ，结果是[1]。
        // 在打印 2，打印的时候往前插入，结果是[2，1]
        // 接着打印 3，打印的时候往前插入，结果是[3，2，1]
        int size = posList.size();
        while (node != null) {// 这里面是插入。
            posList.add(size, node.val);
            node = node.right;
        }
    }
}
