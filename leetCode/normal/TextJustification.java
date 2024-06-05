package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TextJustification
 * @Description: 68. 文本左右对齐
 * @Author: zhaooo
 * @Date: 2023/7/6/9:26
 */
public class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < words.length; ) {
            int rowLen = 0;
            int start = i;
            while (rowLen < maxWidth) {
                // 这里已经加了单词的长度
                if ((rowLen += words[i].length()) <= maxWidth) {
                    // i在这里更新，此时i指向下一个单词位置
                    // i == words.length，即最后一排
                    if (++i >= words.length) {
                        char[] cs = new char[maxWidth];
                        // csIdx指向当前操作位置
                        int csIdx = 0;
                        // 添加单词到cs
                        for (int j = start; j < i; j++) {
                            int wordLen = words[j].length();
                            System.arraycopy(words[j].toCharArray(), 0, cs, csIdx, wordLen);
                            // csIdx移动wordLen+1，并且修改单词后为空格
                            // 如果单词刚好填满最后一个不用添加空格
                            if ((csIdx += wordLen) != maxWidth) {
                                cs[++csIdx] = ' ';
                            }
                        }
                        // cs末尾补充空格
                        for (; csIdx < maxWidth; csIdx++) {
                            cs[csIdx] = ' ';
                        }
                        result.add(new String(cs));
                    }
                    // 添加空格
                    rowLen += 1;
                    // rowLen += words[i].length() > maxWidth，即该排所有单词确定
                } else {
                    // 删除超出的单词长度和前一个单词的后面空格
                    rowLen -= (words[i].length() + 1);
                    // 每个单词之间至少有几个单词，+1是因为之前在每个单词之间已经添加一个空格了
                    int spaceCount = ((maxWidth - rowLen) / (i - start - 1)) + 1;
                    // 要添加在前面几个每个单词之间的空格
                    int remainder = (maxWidth - rowLen) % (i - start - 1);
                    char[] cs = new char[maxWidth];
                    int csIdx = 0;
                    for (int j = start; j < i; j++) {
                        int wordLen = words[j].length();
                        System.arraycopy(words[j].toCharArray(), 0, cs, csIdx, wordLen);
                        csIdx += wordLen;
                        for (int k = 0; k < spaceCount; k++) {
                            cs[csIdx++] = ' ';
                        }
                        if (j - start < remainder) {
                            cs[csIdx++] = ' ';
                        }
                    }
                    result.add(new String(cs));
                }
            }
        }
        return result;
    }
}
