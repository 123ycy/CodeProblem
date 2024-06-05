package normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: CloneGraph
 * @Description: 133. 克隆图
 * 类似：https://leetcode.cn/problems/copy-list-with-random-pointer/description/
 * @Author: zhaooo
 * @Date: 2023/07/30 09:31
 */
public class CloneGraph {
    /**
     * dfs
     * 利用数组保存所有节点
     * 如果题目修改为节点的值和它的索引不相同 可以使用HashMap
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        Node[] nodes = new Node[101];
        dfs(node, nodes);
        return nodes[1];
    }

    void dfs(Node node, Node[] nodes) {
        if (node == null) {
            return;
        }
        int val = node.val;
        nodes[val] = new Node(val);
        node.neighbors.forEach(neighbor -> {
            // 剪枝
            // nodes[val] == null才调用以下逻辑 保证访问一次
            if (nodes[neighbor.val] == null) {
                dfs(neighbor, nodes);
            }
            // 递归完毕后 所有节点都创建 建立连接
            nodes[val].neighbors.add(nodes[neighbor.val]);
        });
    }
//    比用forEach快
//    void dfs(Node node, Node[] nodes) {
//        if (node == null) {
//            return;
//        }
//        int val = node.val;
//        List<Node> neighbors = node.neighbors;
//        nodes[val] = new Node(val);
//        for (Node neighbor : neighbors) {
//            if (nodes[neighbor.val] == null) {
//                dfs(neighbor, nodes);
//            }
//            nodes[val].neighbors.add(nodes[neighbor.val]);
//        }
//    }

    /**
     * dfs
     */
    private HashMap<Node, Node> visited = new HashMap<>();

    public Node cloneGraph2(Node node) {
        if (node == null) {
            return node;
        }

        // 如果该节点已经被访问过了，则直接从哈希表中取出对应的克隆节点返回
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // 克隆节点，注意到为了深拷贝我们不会克隆它的邻居的列表
        Node cloneNode = new Node(node.val, new ArrayList<>());
        // 哈希表存储
        visited.put(node, cloneNode);

        // 遍历该节点的邻居并更新克隆节点的邻居列表
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraph2(neighbor));
        }
        return cloneNode;
    }

    /**
     * bfs
     *
     * @param node
     * @return
     */
    public Node cloneGraph3(Node node) {
        if (node == null) {
            return node;
        }

        HashMap<Node, Node> visited = new HashMap<>();

        // 将题目给定的节点添加到队列
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        // 克隆第一个节点并存储到哈希表中
        visited.put(node, new Node(node.val, new ArrayList<>()));

        // 广度优先搜索
        while (!queue.isEmpty()) {
            // 取出队列的头节点
            Node n = queue.remove();
            // 遍历该节点的邻居
            for (Node neighbor : n.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // 如果没有被访问过，就克隆并存储在哈希表中
                    visited.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    // 将邻居节点加入队列中
                    queue.add(neighbor);
                }
                // 更新当前节点的邻居列表
                visited.get(n).neighbors.add(visited.get(neighbor));
            }
        }
        return visited.get(node);
    }

    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
