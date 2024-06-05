package normal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: ReverseWordsInAString
 * @Description: 151. 反转字符串中的单词
 * @Author: zhaooo
 * @Date: 2023/09/19 19:52
 */
public class ReverseWordsInAString {
    public String reverseWords(String s) {
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.trim().split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }
}
