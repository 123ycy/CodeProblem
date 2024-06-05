package normal;

import java.util.*;

/**
 * @ClassName: GroupAnagrams
 * @Description: 49. 字母异位词分组
 * @Author: zhaooo
 * @Date: 2023/10/31 12:12
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }
}
