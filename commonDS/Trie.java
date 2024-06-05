/**
 * @ClassName: Trie
 * @Description: 前缀树
 * @Author: zhaooo
 * @Date: 2022/7/8/18:32
 */
class Trie {
    // 本身是根节点，不用root
    // 也可添加成员变量root，在构造方法中初始化
    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        // 从根节点开始
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            // 不存在该节点就创建
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            // 找到对应节点
            node = node.children[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        // 返回是否是一个完整的单词
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        // 查找是否有该前缀
        return searchPrefix(prefix) != null;
    }

    // 查找prefix的尾节点
    private Trie searchPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}

