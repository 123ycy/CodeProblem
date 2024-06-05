package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RestoreIPAddresses
 * @Description: 93. 复原 IP 地址
 * @Author: zhaooo
 * @Date: 2024/02/21 11:17
 */
public class RestoreIPAddresses {
    List<String> ans = new ArrayList<>();
    int[] nums;

    public List<String> restoreIpAddresses(String s) {
        nums = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            nums[i] = s.charAt(i) - '0';
        }
        dfs(new int[4], 0, 0);
        return ans;
    }

    void dfs(int[] ip, int dots, int idx) {
        if (dots >= 4 || idx >= nums.length) {
            if (dots == 4 && idx == nums.length) {
                ans.add(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
            }
            return;
        }
        // 前导0
        if (nums[idx] == 0) {
            ip[dots] = 0;
            dfs(ip, dots + 1, idx + 1);
            return;
        }
        int tmp = 0;
        while (idx < nums.length) {
            tmp *= 10;
            tmp += nums[idx];
            if (tmp > 255) {
                break;
            }
            ip[dots] = tmp;
            dfs(ip, dots + 1, idx + 1);
            // 不用回溯 因为循环会不断覆盖sum[dots]
            idx++;
        }
    }
}
