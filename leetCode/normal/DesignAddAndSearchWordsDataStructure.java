package normal;

/**
 * @ClassName: DesignAddAndSearchWordsDataStructure
 * @Description: 211. 添加与搜索单词 - 数据结构设计
 * @Author: zhaooo
 * @Date: 2023/8/9/9:42
 */
public class DesignAddAndSearchWordsDataStructure {

    class WordDictionary {
        Trie trie;

        public WordDictionary() {
            trie = new Trie();
        }

        public void addWord(String word) {
            trie.add(word);
        }

        public boolean search(String word) {
            return trie.search(trie, word.toCharArray(), 0);
        }

        class Trie {
            Trie[] children;
            boolean isEnd;

            Trie() {
                this.children = new Trie[26];
                isEnd = false;
            }

            void add(String word) {
                Trie root = this;
                char[] chars = word.toCharArray();
                for (char aChar : chars) {
                    if (root.children[aChar - 'a'] == null) {
                        root.children[aChar - 'a'] = new Trie();
                    }
                    root = root.children[aChar - 'a'];
                }
                root.isEnd = true;
            }

            boolean search(Trie root, char[] chars, int i) {
                for (; i < chars.length; i++) {
                    if (chars[i] == '.') {
                        for (Trie child : root.children) {
                            if (child != null && search(child, chars, i + 1)) {
                                return true;
                            }
                        }
                        return false;
                    }
                    int idx = chars[i] - 'a';
                    if (root.children[idx] == null) {
                        return false;
                    }
                    root = root.children[idx];
                }
                return root.isEnd;
            }
        }
    }
}
