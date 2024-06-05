/**
 * @ClassName: UnionFind
 * @Description: 并查集
 * @Author: zhaooo
 * @Date: 2022/7/7/14:04
 */
public class UnionFind {
    int[] father;
    int[] rank;

    public UnionFind(int n) {
        father = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
    }

    /**
     * 查找
     * 路径压缩，查找的过程中修改了父节点，使高度降低
     * @param x
     * @return
     */
    public int find(int x) {
        if (x == father[x]) {
            return x;
        } else {
            father[x] = find(father[x]);
            return father[x];
        }
        // 以上可以修改为一行代码
        //return x == father[x] ? x : (father[x] = find(father[x]));
    }

    /**
     * 合并
     * 按秩合并，简单的集合合并到复杂的集合
     * @param i
     * @param j
     */
    public void union(int i, int j) {
        //先找到两个根节点
        int x = find(i), y = find(j);
        if (rank[x] <= rank[y]) {
            father[x] = y;
        } else {
            father[y] = x;
        }
        //如果深度相同且根节点不同，则新的根节点的深度+1
        if (rank[x] == rank[y] && x != y) {
            rank[y]++;
        }
    }
}
