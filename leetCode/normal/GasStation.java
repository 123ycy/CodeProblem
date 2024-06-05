package normal;

/**
 * @ClassName: GasStation
 * @Description: 134. 加油站
 * @Author: zhaooo
 * @Date: 2023/7/5/22:10
 */
public class GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int[] dif = new int[len];
        // lenOf0表示minIdx后面跟的0的个数
        int sum = 0, minIdx = 0, min = Integer.MAX_VALUE, lenOf0 = 0;
        for (int i = 0; i < len; i++) {
            dif[i] = gas[i] - cost[i];
            sum += dif[i];
            // sum < min表示
            if (sum < min) {
                minIdx = i;
                min = sum;
                lenOf0 = 0;
            }
            // gas[i]==0时 不能从该点出发
            if (gas[i] == 0 && (lenOf0 + minIdx) == i) {
                lenOf0++;
            }
        }
        if (sum < 0) {
            return -1;
        }
        // Math.max(lenOf0, 1)表示如果minIdx后面有0，则不能从minIdx+1出发
        return (minIdx + Math.max(lenOf0, 1)) % len;
    }

    /**
     * 优化上面代码
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int n = gas.length;
        int sum = 0;
        int minSum = Integer.MAX_VALUE, minSumIndex = 0;
        for (int i = 0; i < n; i++) {
            sum += gas[i] - cost[i];
            if (sum < minSum) {
                minSumIndex = i;
                minSum = sum;
            }
        }
        if (sum < 0) {
            return -1;
        } else if (minSum >= 0) {
            // minSum >= 0时表示从起点出发可以环游，return 0即可
            return 0;
        } else {
            return (minSumIndex + 1) % n;
        }
    }
}
