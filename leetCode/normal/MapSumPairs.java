package normal;

import java.util.*;

/**
 * @ClassName: MapSumPairs
 * @Description: 677. 键值映射
 * @Author: zhaooo
 * @Date: 2023/12/22 09:50
 */
public class MapSumPairs {
    class MapSum {

        Map<String, Integer> map;
        Trie trie;

        public MapSum() {
            map = new HashMap<>();
            trie = new Trie();
        }

        public void insert(String key, int val) {
            if (map.containsKey(key)) {
                map.put(key, val);
                return;
            }
            map.put(key, val);
            Trie tmp = trie;
            for (int i = 0; i < key.length(); i++) {
                if (tmp.children[key.charAt(i) - 'a'] == null) {
                    tmp.children[key.charAt(i) - 'a'] = new Trie();
                }
                tmp = tmp.children[key.charAt(i) - 'a'];
                tmp.keys.add(key);
            }
            tmp.isEnd = true;
        }

        public int sum(String prefix) {
            Trie tmp = trie;
            for (int i = 0; i < prefix.length(); i++) {
                if (tmp.children[prefix.charAt(i) - 'a'] == null) {
                    return 0;
                }
                tmp = tmp.children[prefix.charAt(i) - 'a'];
            }
            return tmp.keys.stream().mapToInt(map::get).sum();
        }
    }


    class Trie {
        boolean isEnd;
        Trie[] children;
        Set<String> keys;

        Trie() {
            isEnd = false;
            children = new Trie[26];
            keys = new HashSet<>();
        }
    }
}
