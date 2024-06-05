/**
 * @ClassName: BitMap
 * @Description: bitMap
 * 使用bit来保存大量数据
 * @Author: zhaooo
 * @Date: 2022/7/9/16:47
 */
public class BitMap {
    int[] map;

    public BitMap(int n) {
        // n>>5 = n/32
        map = new int[(n >> 5) + 1];
    }

    public void addValue(int n) {
        int row = n >> 5;
        //相当于 n % 32 求十进制数在数组a[i]中的下标
        // 31 = 11111(binary)
        map[row] |= 1 << (n & 31);
    }

    public boolean exits(int n) {
        int row = n >> 5;
        // 判断所在的bit为是否为1
        return (map[row] & (1 << (n & 31))) != 0;
    }
}
