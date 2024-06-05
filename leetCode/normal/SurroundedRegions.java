package normal;

/**
 * @ClassName: SurroundedRegions
 * @Description: 130. 被围绕的区域
 * @Author: zhaooo
 * @Date: 2023/07/29 13:25
 */
public class SurroundedRegions {
    /**
     * 递归
     *
     * @param board
     */
    public void solve(char[][] board) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs(board, visited, i, 0);
            if (board[i][n - 1] == 'O') dfs(board, visited, i, n - 1);
        }
        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'O') dfs(board, visited, 0, i);
            if (board[m - 1][i] == 'O') dfs(board, visited, m - 1, i);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && !visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    void dfs(char[][] board, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] || board[i][j] == 'X') {
            return;
        }
        visited[i][j] = true;
        dfs(board, visited, i - 1, j);
        dfs(board, visited, i + 1, j);
        dfs(board, visited, i, j - 1);
        dfs(board, visited, i, j + 1);
    }

    /**
     * 并查集
     *
     * @param board
     */
    int m, n;

    public void solve2(char[][] board) {
        if (board == null || board.length == 0)
            return;

        m = board.length;
        n = board[0].length;

        // 用一个虚拟节点, 边界上的O 的父节点都是这个虚拟节点
        UnionFind uf = new UnionFind(m * n + 1);
        int dummyNode = m * n;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    // 遇到O进行并查集操作合并
                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        // 边界上的O,把它和dummyNode 合并成一个连通区域.
                        uf.union(node(i, j), dummyNode);
                    } else {
                        // 和上下左右合并成一个连通区域.
                        if (board[i - 1][j] == 'O')
                            uf.union(node(i, j), node(i - 1, j));
                        if (i < m - 1 && board[i + 1][j] == 'O')
                            uf.union(node(i, j), node(i + 1, j));
                        if (board[i][j - 1] == 'O')
                            uf.union(node(i, j), node(i, j - 1));
                        if (j < n - 1 && board[i][j + 1] == 'O')
                            uf.union(node(i, j), node(i, j + 1));
                    }
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (uf.isConnected(node(i, j), dummyNode)) {
                    // 和dummyNode 在一个连通区域的,那么就是O；
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }
    }

    int node(int i, int j) {
        return i * n + j;
    }

    class UnionFind {
        int[] parents;

        public UnionFind(int num) {
            parents = new int[num];
            for (int i = 0; i < num; i++) {
                parents[i] = i;
            }
        }

        void union(int node1, int node2) {
            int root1 = find(node1);
            int root2 = find(node2);
            if (root1 != root2) {
                parents[root2] = root1;
            }
        }

        int find(int node) {
            while (parents[node] != node) {
                parents[node] = parents[parents[node]];
                node = parents[node];
            }
            return node;
        }

        boolean isConnected(int node1, int node2) {
            return find(node1) == find(node2);
        }
    }
}
