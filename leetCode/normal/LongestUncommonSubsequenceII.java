package normal;

import java.util.*;

/**
 * @ClassName: LongestUncommonSubsequenceII
 * @Description: 522. 最长特殊序列 II
 * @Author: zhaooo
 * @Date: 2022/6/27/13:31
 */
public class LongestUncommonSubsequenceII {
    /**
     * hashSet
     * @param strs
     * @return
     */
    public int findLUSlength(String[] strs) {
        int maxLen = 10;
        // list[i]中为长度为i的字符串集合
        List<String>[] lists = new ArrayList[maxLen];
        for (int i = 0; i < maxLen; i++) {
            lists[i] = new ArrayList<>();
        }
        for (String s : strs) {
            lists[s.length() - 1].add(s);
        }
        // 出现一次的字符串
        Set<String> oneTimes = new HashSet<>();
        // 最长的字符串集合中出现多次的字符串
        Set<String> moreTimes = new HashSet<>();
        // 遍历到长度为j时对应出现多次的字符串集合
        Set<String> tmpMoreTimes = new HashSet<>();
        // 从最长的字符串集合开始遍历
        for (int i = maxLen - 1; i >= 0; i--) {
            if (lists[i].size() == 0) {
                continue;
            }
            // 最长的字符串集合
            for (String s : lists[i]) {
                if (!oneTimes.contains(s) && !moreTimes.contains(s)) {
                    oneTimes.add(s);
                } else {
                    oneTimes.remove(s);
                    moreTimes.add(s);
                }
            }
            // 最长的字符串中有只出现一次的直接返回
            if (oneTimes.size() > 0) {
                return i + 1;
            }
            // 从长度小于最长的字符串集合中遍历
            for (int j = i - 1; j >= 0; j--) {
                if (lists[j].size() == 0) {
                    continue;
                }
                oneTimes.clear();
                tmpMoreTimes.clear();
                for (String s : lists[j]) {
                    if (!oneTimes.contains(s) && !tmpMoreTimes.contains(s)) {
                        oneTimes.add(s);
                    } else {
                        oneTimes.remove(s);
                        tmpMoreTimes.add(s);
                    }
                }
                // 长度为j的字符串s2
                for (String s2 : oneTimes) {
                    boolean flag = true;
                    // 如果不是最长字符串的子序列，那肯定不是其他字符串的子序列
                    for (String s1 : moreTimes) {
                        if (isSubSequence(s1, s2)) {
                            flag = false;
                            break;
                        }
                    }
                    // 不是所有最长字符串的子序列
                    if (flag) {
                        return j + 1;
                    }
                }
            }
            return -1;
        }
        return -1;
    }

    /**
     * 枚举每个字符串
     * @param strs
     * @return
     */
    public int findLUSlength2(String[] strs) {
        int n = strs.length;
        int ans = -1;
        for (int i = 0; i < n; ++i) {
            boolean check = true;
            for (int j = 0; j < n; ++j) {
                if (i != j && isSubSequence(strs[i], strs[j])) {
                    check = false;
                    break;
                }
            }
            if (check) {
                ans = Math.max(ans, strs[i].length());
            }
        }
        return ans;
    }

    public boolean isSubSequence(String s1, String s2) {
        char[] cs1 = s1.toCharArray();
        char[] cs2 = s2.toCharArray();
        int i = 0, j = 0;
        while (i < cs1.length && j < cs2.length) {
            if (cs1[i] == cs2[j]) {
                j++;
            }
            i++;
        }
        return j == cs2.length;
    }
}
