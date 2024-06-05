package normal;

/**
 * @ClassName: PrimeArrangements
 * @Description: 1175. 质数排列
 * @Author: zhaooo
 * @Date: 2022/6/30/15:37
 */
public class PrimeArrangements {
    private static final int MOD = 1000000007;
    private static final int[] PRIMES = new int[101];
    private static final long[] FACTORIAL = new long[101];

    static {
        PRIMES[0] = PRIMES[1] = 0;
        FACTORIAL[0] = FACTORIAL[1] = 1L;
        for (int i = 2; i <= 100; i++) {
            FACTORIAL[i] = FACTORIAL[i - 1] * i % MOD;
            PRIMES[i] = PRIMES[i - 1] + (isPrime(i) ? 1 : 0);
        }
    }

    /**
     * 欧式筛+数学
     * @param x
     * @return
     */
    private static boolean isPrime(int x) {
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int numPrimeArrangements(int n) {
        return (int) (FACTORIAL[PRIMES[n]] * FACTORIAL[n - PRIMES[n]] % MOD);
    }
}
