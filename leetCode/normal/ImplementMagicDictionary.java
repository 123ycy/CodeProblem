package normal;

/**
 * @ClassName: ImplementMagicDictionary
 * @Description: 676. 实现一个魔法字典
 * @Author: zhaooo
 * @Date: 2022/7/11/13:34
 */
public class ImplementMagicDictionary {
    class MagicDictionary {

        Trie root;

        public MagicDictionary() {
            root = new Trie();
        }

        public void buildDict(String[] dictionary) {
            for (String s : dictionary) {
                root.insert(s);
            }
        }

        public boolean search(String searchWord) {
            return dfs(root, searchWord, 0, 0);
        }

        // trie表示这轮dfs的开始节点，对应matchLen
        // matchLen表示已经匹配的长度
        // count表示不同的字符个数
        public boolean dfs(Trie trie, String word, int matchLen, int count) {
            if (matchLen == word.length()) {
                return count == 1 && trie.isEnd;
            }
            if (count == 1) {
                // 下一个节点
                Trie tmp = trie.children[word.charAt(matchLen) - 'a'];
                return tmp != null && dfs(tmp, word, matchLen + 1, count);
            }
            for (int i = 0; i < trie.children.length; i++) {
                Trie tmp = trie.children[i];
                // 如果当前tmp和对应char不相同count为1，相同为0
                if (tmp != null && dfs(tmp, word, matchLen + 1, i == (word.charAt(matchLen) - 'a') ? 0 : 1)) {
                    return true;
                }
            }
            return false;
        }
    }

    class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie trie = this;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                trie = trie.children[index] == null ? (trie.children[index] = new Trie()) : trie.children[index];
            }
            trie.isEnd = true;
        }
    }
}
