import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CountPrimes
 * @Description: 质数个数
 * 返回所有小于n的质数的个数
 * @Author: zhaooo
 * @Date: 2022/6/30/22:14
 */
public class CountPrimes {
    /**
     * 埃式筛
     * 如果i是质数,那么i的x倍也是质数
     * @param n
     * @return
     */
    int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }
        boolean[] notPrime = new boolean[n];
        int res = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i]) {
                continue;
            }
            for (int j = 2; j * i < n; j++) {
                notPrime[j * i] = true;
            }
            res++;
        }
        return res;
    }

    /**
     * 线性筛
     * 不属于面试范畴
     * 核心点在于：如果 x 可以被prime_i整除,对于y = x * primes_i+1 而言
     * 一定会在后面遍历到 (prime_i / x) * primes_i+1 时被标记
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode.cn/problems/count-primes/solution/ji-shu-zhi-shu-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param n
     * @return
     */
    public int countPrimes2(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] notPrime = new boolean[n];
        for (int i = 2; i < n; ++i) {
            if (!notPrime[i]) {
                primes.add(i);
            }
            for (int j = 0; j < primes.size() && i * primes.get(j) < n; ++j) {
                notPrime[i * primes.get(j)] = true;
                // 每个合数只会被其 最小的质因数标记
                // 如果 i % prime_j == 0, 则 i = k * prime_j, 再向下就不能保证 每个合数只会被其最小的质因数标记
                if (i % primes.get(j) == 0) {
                    break;
                }
            }
        }
        return primes.size();
    }
}
