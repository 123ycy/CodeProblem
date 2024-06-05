package normal;

import java.util.*;

/**
 * @ClassName: RectangleAreaII
 * @Description: 850. 矩形面积 II
 * @Author: zhaooo
 * @Date: 2022/9/16/9:59
 */
public class RectangleAreaII {
    final int MOD = (int) 1e9 + 7;
    final int IN = 1;
    final int OUT = -1;

    /**
     * 暴力+扫描线
     * https://leetcode.cn/problems/rectangle-area-ii/solution/gong-shui-san-xie-by-ac_oier-9r36/
     * @param rectangles
     * @return
     */
    public int rectangleArea(int[][] rectangles) {
        List<Integer> list = new ArrayList<>();
        // 添加所有x坐标
        for (int[] rect : rectangles) {
            list.add(rect[0]);
            list.add(rect[2]);
        }
        // 由小到大排序所有边（即x坐标）
        Collections.sort(list);
        long ans = 0;
        for (int i = 1; i < list.size(); i++) {
            // 令相邻x坐标距离为len，如果len=0跳过循环
            int a = list.get(i - 1), b = list.get(i), len = b - a;
            if (len == 0) {
                continue;
            }
            List<int[]> lines = new ArrayList<>();
            // 当矩形覆盖当前x区间，则将y坐标记录下来（即从该矩形中切割出此x的范围）
            for (int[] info : rectangles) {
                if (info[0] <= a && b <= info[2]) {
                    lines.add(new int[]{info[1], info[3]});
                }
            }
            // 对所有的y坐标对（line），按照y1,y2,从小到大排序
            lines.sort((l1, l2) -> l1[0] != l2[0] ? l1[0] - l2[0] : l1[1] - l2[1]);
            // 定义tot存储当前x区间下，y区间的并集，l，r为上一个y区间端点
            long tot = 0, l = -1, r = -1;
            for (int[] cur : lines) {
                if (cur[0] > r) {
                    // 如果和上次的区间不相交，则将上次区间计入总和，同时更新l,r
                    tot += r - l;
                    l = cur[0];
                    r = cur[1];
                    // 相交则扩大区间大小
                } else if (cur[1] > r) {
                    r = cur[1];
                }
            }
            tot += r - l;
            ans += tot * len;
            ans %= MOD;
        }
        return (int) ans;
    }

    /**
     * 离散化 + 扫描线 + 使用简单数组实时维护
     * https://leetcode.cn/problems/rectangle-area-ii/solution/ju-xing-mian-ji-ii-by-leetcode-solution-ulqz/
     * @param rectangles
     * @return
     */
    public int rectangleArea2(int[][] rectangles) {
        int n = rectangles.length;
        Set<Integer> set = new HashSet<Integer>();
        for (int[] rect : rectangles) {
            // 下边界
            set.add(rect[1]);
            // 上边界
            set.add(rect[3]);
        }
        List<Integer> hbound = new ArrayList<>(set);
        Collections.sort(hbound);
        int m = hbound.size();
        // 「思路与算法部分」的 length 数组并不需要显式地存储下来
        // length[i] 可以通过 hbound[i+1] - hbound[i] 得到
        // seg[i] 对应 length[i]
        int[] seg = new int[m - 1];
        List<int[]> sweep = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            // 左边界
            sweep.add(new int[]{rectangles[i][0], i, IN});
            // 右边界
            sweep.add(new int[]{rectangles[i][2], i, OUT});
        }
        sweep.sort((a, b) -> {
            // 先按x轴排序
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                // 最后按入边出边排序
                return a[2] - b[2];
            }
        });

        long ans = 0;
        for (int i = 0; i < sweep.size(); ++i) {
            int j = i;
            // [i,j]都是x轴相同的边
            while (j + 1 < sweep.size() && sweep.get(i)[0] == sweep.get(j + 1)[0]) {
                ++j;
            }
            // i是最后一条边
            if (j + 1 == sweep.size()) {
                break;
            }
            // 一次性地处理掉一批横坐标相同的左右边界
            for (int k = i; k <= j; ++k) {
                int[] arr = sweep.get(k);
                int idx = arr[1], diff = arr[2];
                int left = rectangles[idx][1], right = rectangles[idx][3];
                // 倒序遍历hbound
                for (int x = 0; x < m - 1; ++x) {
                    // 把在该线内部的seg的值修改
                    if (left <= hbound.get(x) && hbound.get(x + 1) <= right) {
                        // diff对应 1为入边（in）-1为出边（out）
                        seg[x] += diff;
                    }
                }
            }
            // 此x轴对应y轴覆盖的长度
            int cover = 0;
            for (int k = 0; k < m - 1; ++k) {
                if (seg[k] > 0) {
                    cover += (hbound.get(k + 1) - hbound.get(k));
                }
            }
            // sweep是按照x轴的顺序 j对应当前x轴坐标的最后一根线
            ans += (long) cover * (sweep.get(j + 1)[0] - sweep.get(j)[0]);
            i = j;
        }
        return (int) (ans % MOD);
    }

    /**
     * 离散化 + 扫描线 + 线段树
     * https://leetcode.cn/problems/rectangle-area-ii/solution/by-capital-worker-7efv/
     * @param rectangles
     * @return
     */
    public int rectangleArea3(int[][] rectangles) {
        // 用TreeSet将纵坐标去重并用于后续离散化
        TreeSet<Integer> ySet = new TreeSet<>();
        // 用正反两个HashMap存储离散化后坐标和实际纵坐标的关系
        Map<Integer, Integer> y2Index = new HashMap<>();
        Map<Integer, Integer> index2y = new HashMap<>();
        List<int[]> xList = new ArrayList<>();
        for (int[] rect : rectangles) {
            //记录入边 结构是 x -> y1 y2 1/-1
            xList.add(new int[]{rect[0], rect[1], rect[3], IN});
            //记录出边 结构是 x -> y1 y2 1/-1
            xList.add(new int[]{rect[2], rect[1], rect[3], OUT});
            ySet.add(rect[1]);
            ySet.add(rect[3]);
        }
        // 将纵坐标离散化，从1开始
        int count = 1;
        for (int y : ySet) {
            // 记录实际坐标和离散化坐标的关系
            y2Index.put(y, count);
            // 记录离散化坐标和实际坐标的关系
            index2y.put(count, y);
            count++;
        }
        // 按照x轴从小到大排序
        xList.sort(Comparator.comparingInt(x -> x[0]));
        // 开线段树的根节点
        SegmentTree segmentTree = new SegmentTree();
        long ans = 0;
        int n = xList.size();
        for (int i = 0; i < n - 1; i++) {
            int[] cur = xList.get(i);
            // 分别取出当前边的上下两个坐标
            int left = y2Index.get(cur[1]);
            int right = y2Index.get(cur[2]);
            // 将当前的边的区间更新到线段树上
            //简单讲讲大家不曾涉及到的：为什么在更新时right要转变为right - 1？
            //主要是为了避免数据丢失。如果直接传递left，right。比如[0,2],按照线段树划分下去后，最终会落在[0,1]区间结点和[2,2]结点。
            // 问题来了，[2,2]结点的计算结果为0，由于是相同key。
            //而如果传入时，先将right-1则可避免边界值出现相同的情况。比如[0,3],先减后传入则会得到上述一样的情况。
            // 但是在计算[2,2]时，其结果就不会为0了，因为我们需要对right就行还原为3，最终得出正确结果。
            segmentTree.update(left, right - 1, cur[3], index2y);
            ans += (long) segmentTree.query() * (xList.get(i + 1)[0] - cur[0]);
        }
        return (int) (ans % MOD);
    }

    // 离散化 线段树
    class SegmentTree {
        private static final int maxNode = 201;

        public SegmentTree() {
            root = new Node();
        }

        private Node root;

        // 用于扫描线问题的线段树类
        private class Node {
            public Node left;
            public Node right;
            public int coverLen;
            public int cover;
        }

        public void update(int left, int right, int value, Map<Integer, Integer> index2y) {
            update(root, 1, maxNode, left, right, value, index2y);
        }

        public int query() {
            return root.coverLen;
        }

        private void update(Node root, int start, int end, int left, int right, int value, Map<Integer, Integer> index2y) {
            // 先为当前节点动态开左右节点
            createNode(root);
            if (left <= start && end <= right) {
                root.cover += value;
                //pushUp更新当前节点的区间长度
                pushUp(root, start, end, index2y);
                return;
            }
            int mid = start + (end - start) / 2;
            // 分别更新左右区间
            if (left <= mid) {
                update(root.left, start, mid, left, right, value, index2y);
            }
            if (mid < right) {
                update(root.right, mid + 1, end, left, right, value, index2y);
            }
            // ushUp更新当前节点的区间长度
            pushUp(root, start, end, index2y);
        }

        // 更新线段树节点对应的区间长度
        private void pushUp(Node root, int start, int end, Map<Integer, Integer> index2y) {
            // root.cover是更新到整个区间上所有边的in或者out标记之和，
            // 如果cover > 0，则当前区间被完全覆盖，通过index2y找到实际的覆盖长度
            if (root.cover > 0) {
                root.coverLen = index2y.get(end + 1) - index2y.get(start);
            } else if (start != end) {
                // cover == 0，则表示该区间没有被完全覆盖，直接相加左右子树的覆盖长度
                root.coverLen = root.left.coverLen + root.right.coverLen;
            } else {
                // 如果 start == end，不代表任何区间，长度是0
                root.coverLen = 0;
            }
        }

        // 动态开左右节点
        private void createNode(Node root) {
            if (root.left == null) {
                root.left = new Node();
            }
            if (root.right == null) {
                root.right = new Node();
            }
        }
    }
}