package normal;

/**
 * @ClassName: CompareVersionNumbers
 * @Description: 165. 比较版本号
 * @Author: zhaooo
 * @Date: 2024/02/28 13:45
 */
public class CompareVersionNumbers {
    public int compareVersion(String version1, String version2) {
        String[] strs1 = version1.split("\\.");
        String[] strs2 = version2.split("\\.");
        int i = 0, j = 0;
        while (i < strs1.length || j < strs2.length) {
            int v1 = i < strs1.length ? Integer.parseInt(strs1[i++]) : 0;
            int v2 = j < strs2.length ? Integer.parseInt(strs2[j++]) : 0;
            if (v1 > v2) {
                return 1;
            } else if (v2 > v1) {
                return -1;
            }
        }
        return 0;
    }
}
