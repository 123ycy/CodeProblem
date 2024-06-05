package normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: EncodeAndDecodeTinyurl
 * @Description: 535. TinyURL的加密与解密
 * @Author: zhaooo
 * @Date: 2022/6/29/8:38
 */
public class EncodeAndDecodeTinyurl {
    /**
     * 自增
     */
    public class Codec {
        private Map<Integer, String> dataBase = new HashMap<>();
        private int id;

        public String encode(String longUrl) {
            id++;
            dataBase.put(id, longUrl);
            return "http://tinyurl.com/" + id;
        }

        public String decode(String shortUrl) {
            int p = shortUrl.lastIndexOf('/') + 1;
            int key = Integer.parseInt(shortUrl.substring(p));
            return dataBase.get(key);
        }
    }

    /**
     * 哈希生成
     *
     * hash(longUrl) = (for item in longUrl: sum += item * k1) mod k2
     *
     * 发生哈希冲突时，我们采用线性探测再散列的方法，将 key 加一，直到没有冲突。
     * 相同的 longUrl 的哈希值相同，因此哈希冲突会频繁发生。为了避免这一点，
     * 我们使用一个额外的哈希表记录从 longUrl 到 key 的映射。
     */
    public class Codec2 {
        /**
         * 质数
         */
        static final int K1 = 1117;
        /**
         * 质数
         */
        static final int K2 = 1000000007;
        private Map<Integer, String> dataBase = new HashMap<>();
        private Map<String, Integer> urlToKey = new HashMap<>();

        public String encode(String longUrl) {
            if (urlToKey.containsKey(longUrl)) {
                return "http://tinyurl.com/" + urlToKey.get(longUrl);
            }
            // 散列结果
            int key = 0;
            // 基数
            long base = 1;
            for (int i = 0; i < longUrl.length(); i++) {
                char c = longUrl.charAt(i);
                key = (int) ((key + (long) c * base) % K2);
                base = (base * K1) % K2;
            }
            // 线性探测
            while (dataBase.containsKey(key)) {
                key = (key + 1) % K2;
            }
            dataBase.put(key, longUrl);
            urlToKey.put(longUrl, key);
            return "http://tinyurl.com/" + key;
        }

        public String decode(String shortUrl) {
            int p = shortUrl.lastIndexOf('/') + 1;
            int key = Integer.parseInt(shortUrl.substring(p));
            return dataBase.get(key);
        }
    }

}
