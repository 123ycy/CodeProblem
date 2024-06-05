package normal;

/**
 * @ClassName: LogicalOrOfTwoBinaryGridsRepresentedAsQuadTrees
 * @Description: 558. 四叉树交集
 * @Author: zhaooo
 * @Date: 2022/7/15/9:24
 */
public class LogicalOrOfTwoBinaryGridsRepresentedAsQuadTrees {
    public Node intersect(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf && quadTree2.isLeaf) {
            return new Node(quadTree1.val | quadTree2.val, true, null, null, null, null);
        }
        // 有一个为值为1的节点，则交集后也全为1
        if ((quadTree1.isLeaf && quadTree1.val) || (quadTree2.isLeaf && quadTree2.val)) {
            return new Node(true, true, null, null, null, null);
        }
        Node node = new Node();
        node.val = false;
        node.isLeaf = false;
        node.topLeft = intersect(quadTree1.isLeaf ? quadTree1 : quadTree1.topLeft, quadTree2.isLeaf ? quadTree2 : quadTree2.topLeft);
        node.topRight = intersect(quadTree1.isLeaf ? quadTree1 : quadTree1.topRight, quadTree2.isLeaf ? quadTree2 : quadTree2.topRight);
        node.bottomLeft = intersect(quadTree1.isLeaf ? quadTree1 : quadTree1.bottomLeft, quadTree2.isLeaf ? quadTree2 : quadTree2.bottomLeft);
        node.bottomRight = intersect(quadTree1.isLeaf ? quadTree1 : quadTree1.bottomRight, quadTree2.isLeaf ? quadTree2 : quadTree2.bottomRight);
        boolean tLVal = node.topLeft.val;
        // 如果4个子节点是值相同的叶子节点就合并为一个叶节点
        if (node.topLeft.isLeaf && node.topRight.isLeaf && node.bottomLeft.isLeaf && node.bottomRight.isLeaf
                && node.topRight.val == tLVal && node.bottomLeft.val == tLVal && node.bottomRight.val == tLVal) {
            node = new Node(tLVal, true, null, null, null, null);
        }
        return node;
    }

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {
        }

        public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }
}
