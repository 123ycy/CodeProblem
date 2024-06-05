/**
 * @ClassName: BinaryIndexedTree
 * @Description: 树状数组
 * @Author: zhaooo
 * @Date: 2024/03/31 17:19
 */
public class BinaryIndexedTree {
    int N;
    int[] tr;

    BinaryIndexedTree(int n) {
        N = n;
        tr = new int[N + 1];
    }

    // 参数转为二进制后,最后一个1的位置所代表的数值
    // 可以理解为tr中下标为x的元素所代表的区间的长度
    int lowBit(int i) {
        return i & -i;
    }

    void add(int i, int x) {
        // tr[i]正上方的序列就是tr[i+lowBit(i)]
        while (i <= N) {
            // 修改tr[i]
            tr[i] += x;
            // 以及修改tr[i]的正上方的序列
            i += lowBit(i);
        }
    }

    int query(int i) {
        int res = 0;
        // 不断加上当前tr[i]长度的区间的和
        while (i > 0) {
            res += tr[i];
            // 减去当前tr[i]长度的区间
            i -= lowBit(i);
        }
        return res;
    }
}
