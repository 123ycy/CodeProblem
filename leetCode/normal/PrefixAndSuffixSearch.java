package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PrefixAndSuffixSearch
 * @Description: 745. 前缀和后缀搜索
 * @Author: zhaooo
 * @Date: 2022/7/14/9:08
 */
public class PrefixAndSuffixSearch {
    class WordFilter {
        int len;
        Trie prefixTree;
        Trie suffixTree;

        public WordFilter(String[] words) {
            len = words.length;
            prefixTree = new Trie(len);
            suffixTree = new Trie(len);
            for (int i = 0; i < len; i++) {
                prefixTree.insert(words[i], i, true);
                suffixTree.insert(words[i], i, false);
            }
        }

        public int f(String pref, String suff) {
            BitMap prefixSet, suffixSet;
            if ((prefixSet = prefixTree.search(pref, true)) != null
                    && (suffixSet = suffixTree.search(suff, false)) != null) {
                for (int i = len - 1; i >= 0; i--) {
                    if (prefixSet.exits(i) && suffixSet.exits(i)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        class Trie {
            int len;
            // 使用数组会超时
            // 以当前节点为前缀或者后缀的节点下标
            BitMap set;
            Trie[] children;

            public Trie(int len) {
                this.len = len;
                set = new BitMap(len);
                children = new Trie[26];
            }

            public void insert(String word, int index, boolean isPrefix) {
                Trie trie = this;
                // 根据isPrefix初始化，前后缀最长为7
                for (int i = isPrefix ? 0 : word.length() - 1; (isPrefix && i < Math.min(word.length(), 7))
                        || (!isPrefix && i >= Math.max(word.length() - 7, 0)); i += isPrefix ? 1 : -1) {
                    // 添加下标
                    trie.set.addValue(index);
                    int charIndex = word.charAt(i) - 'a';
                    trie = trie.children[charIndex] == null ? (trie.children[charIndex] = new Trie(len)) : trie.children[charIndex];
                }
                trie.set.addValue(index);
            }

            public BitMap search(String word, boolean isPrefix) {
                Trie node = this;
                for (int i = isPrefix ? 0 : word.length() - 1; (isPrefix && i < Math.min(word.length(), 7))
                        || (!isPrefix && i >= Math.max(word.length() - 7, 0)); i += isPrefix ? 1 : -1) {
                    int index = word.charAt(i) - 'a';
                    if (node.children[index] == null) {
                        return null;
                    }
                    node = node.children[index];
                }
                return node.set;
            }
        }

        class BitMap {
            int[] map;

            public BitMap(int n) {
                // 2^5=32
                map = new int[(n >> 5) + 1];
            }

            public void addValue(int n) {
                int row = n >> 5;
                // (n & 31) 取最后5位即0-31
                map[row] |= 1 << (n & 31);
            }

            public boolean exits(int n) {
                int row = n >> 5;
                return (map[row] & (1 << (n & 31))) != 0;
            }
        }
    }

    class WordFilter2 {
        class TrieNode {
            TrieNode[] tns = new TrieNode[26];
            // 优化，不用保存所有下标，只保存包含该前/后缀的下标
            List<Integer> idxs = new ArrayList<>();
        }

        void add(TrieNode p, String s, int idx, boolean isTurn) {
            int n = s.length();
            p.idxs.add(idx);
            for (int i = isTurn ? n - 1 : 0; i >= 0 && i < n; i += isTurn ? -1 : 1) {
                int u = s.charAt(i) - 'a';
                if (p.tns[u] == null) {
                    p.tns[u] = new TrieNode();
                }
                p = p.tns[u];
                p.idxs.add(idx);
            }
        }

        int query(String a, String b) {
            int n = a.length(), m = b.length();
            TrieNode p = tr1;
            for (int i = 0; i < n; i++) {
                int u = a.charAt(i) - 'a';
                if (p.tns[u] == null) {
                    return -1;
                }
                p = p.tns[u];
            }
            List<Integer> l1 = p.idxs;
            p = tr2;
            for (int i = m - 1; i >= 0; i--) {
                int u = b.charAt(i) - 'a';
                if (p.tns[u] == null) {
                    return -1;
                }
                p = p.tns[u];
            }
            List<Integer> l2 = p.idxs;
            n = l1.size();
            m = l2.size();
            // 双指针查找
            for (int i = n - 1, j = m - 1; i >= 0 && j >= 0; ) {
                if (l1.get(i) > l2.get(j)) {
                    i--;
                } else if (l1.get(i) < l2.get(j)) {
                    j--;
                } else {
                    return l1.get(i);
                }
            }
            return -1;
        }

        TrieNode tr1 = new TrieNode(), tr2 = new TrieNode();

        public WordFilter2(String[] ss) {
            int n = ss.length;
            for (int i = 0; i < n; i++) {
                add(tr1, ss[i], i, false);
                add(tr2, ss[i], i, true);
            }
        }

        public int f(String a, String b) {
            return query(a, b);
        }
    }
}
