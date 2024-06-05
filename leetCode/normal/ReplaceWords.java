package normal;

import java.util.List;

/**
 * @ClassName: ReplaceWords
 * @Description: 648. 单词替换 
 * @Author: zhaooo
 * @Date: 2023/12/21 13:44
 */
public class ReplaceWords {
    /**
     * 利用前缀树
     * @param dictionary
     * @param sentence
     * @return
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        String[] words = sentence.split(" ");
        Trie trie = new Trie();
        for (String s : dictionary) {
            trie.insert(s);
        }
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(trie.search(word));
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    static class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie node = this;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new Trie();
                }
                node = node.children[c - 'a'];
            }
            node.isEnd = true;
        }

        public String search(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                if (node.children[word.charAt(i) - 'a'] == null) {
                    return word;
                }
                node = node.children[word.charAt(i) - 'a'];
                if (node.isEnd) {
                    return word.substring(0, i + 1);
                }
            }
            return word;
        }
    }
}
