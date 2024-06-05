package normal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: WordSearchIi
 * @Description: 212. 单词搜索 II
 * @Author: zhaooo
 * @Date: 2023/08/10 15:33
 */
public class WordSearchII {
    int m, n;

    /**
     * 暴力 超时
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords(char[][] board, String[] words) {
        m = board.length;
        n = board[0].length;
        List<String> ret = new ArrayList<>();
        List<Integer>[] map = new List[26];
        for (int i = 0; i < map.length; i++) {
            map[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                map[board[i][j] - 'a'].add((i << 4) | j);
            }
        }
        for (String word : words) {
            int i = word.charAt(0) - 'a';
            for (Integer xy : map[i]) {
                if (dfs(word, 0, xy >> 4, xy & 0xf, board, new int[m][n])) {
                    ret.add(word);
                    break;
                }
            }
        }
        return ret;
    }

    boolean dfs(String word, int idx, int i, int j, char[][] board, int[][] visited) {
        if (idx == word.length()) {
            return true;
        }
        if (i < 0 || i >= m || j < 0 || j >= board[0].length || visited[i][j] == 1) {
            return false;
        }
        if (board[i][j] != word.charAt(idx)) {
            return false;
        }
        visited[i][j] = 1;
        boolean res = dfs(word, idx + 1, i - 1, j, board, visited)
                || dfs(word, idx + 1, i, j - 1, board, visited)
                || dfs(word, idx + 1, i + 1, j, board, visited)
                || dfs(word, idx + 1, i, j + 1, board, visited);
        visited[i][j] = 0;
        return res;
    }

    /**
     * trie+dfs
     */
    class TrieNode {
        // 最后一个节点才有s即相当于普通TrieNde的isEnd
        String s;
        TrieNode[] tns = new TrieNode[26];
    }

    void insert(String s) {
        TrieNode p = root;
        for (int i = 0; i < s.length(); i++) {
            int u = s.charAt(i) - 'a';
            if (p.tns[u] == null) {
                p.tns[u] = new TrieNode();
            }
            p = p.tns[u];
        }
        p.s = s;
    }

    Set<String> set = new HashSet<>();
    char[][] board;
    // int n, m;
    TrieNode root = new TrieNode();
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    boolean[][] vis = new boolean[15][15];

    public List<String> findWords2(char[][] _board, String[] words) {
        board = _board;
        m = board.length;
        n = board[0].length;
        // 将单词加入trie
        for (String w : words) insert(w);
        // dfs board的每个节点
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int u = board[i][j] - 'a';
                if (root.tns[u] != null) {
                    vis[i][j] = true;
                    dfs(i, j, root.tns[u]);
                    vis[i][j] = false;
                }
            }
        }
        return new ArrayList<>(set);
    }

    void dfs(int i, int j, TrieNode node) {
        // 访问到一个完整单词
        if (node.s != null) set.add(node.s);
        for (int[] d : dirs) {
            int dx = i + d[0], dy = j + d[1];
            if (dx < 0 || dx >= m || dy < 0 || dy >= n) continue;
            if (vis[dx][dy]) continue;
            int u = board[dx][dy] - 'a';
            if (node.tns[u] != null) {
                vis[dx][dy] = true;
                dfs(dx, dy, node.tns[u]);
                vis[dx][dy] = false;
            }
        }
    }
}
