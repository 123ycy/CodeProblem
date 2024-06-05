package normal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: ShortEncodingOfWords
 * @Description: 820. 单词的压缩编码
 * @Author: zhaooo
 * @Date: 2023/12/21 14:10
 */
public class ShortEncodingOfWords {

    /**
     * set去重
     * @param words
     * @return
     */
    public int minimumLengthEncoding(String[] words) {
        Set<String> good = new HashSet<String>(Arrays.asList(words));
        for (String word : words) {
            // 去除[0,word.length()-1]的子串
            for (int k = 1; k < word.length(); ++k) {
                good.remove(word.substring(k));
            }
        }

        int ans = 0;
        for (String word : good) {
            ans += word.length() + 1;
        }
        return ans;
    }

    int ret = 0;

    /**
     * 后缀树解决
     * @param words
     * @return
     */
    public int minimumLengthEncoding2(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        dfs(trie, 0);
        return ret;
    }

    // len是当前trie对应的word长度
    void dfs(Trie trie, int len) {
        boolean max = true;
        for (Trie child : trie.children) {
            // 有子节点表明不是包含当前trie对应的word长度的最长的单词
            if (child != null) {
                max = false;
                dfs(child, len + 1);
            }
        }
        if (max) {
            // word.len+#
            ret += len + 1;
        }
    }

    // 后缀trie
    static class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie node = this;
            for (int i = word.length() - 1; i >= 0; i--) {
                if (node.children[word.charAt(i) - 'a'] == null) {
                    node.children[word.charAt(i) - 'a'] = new Trie();
                }
                node = node.children[word.charAt(i) - 'a'];
            }
            node.isEnd = true;
        }
    }
}
