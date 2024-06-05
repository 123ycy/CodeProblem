package normal;

/**
 * @ClassName: ValidBoomerang
 * @Description: 1037. 有效的回旋镖
 * @Author: zhaooo
 * @Date: 2022/6/8/15:28
 */
public class ValidBoomerang {
    /**
     * 向量叉乘
     * @param points
     * @return
     */
    public boolean isBoomerang(int[][] points) {
        int[] v1 = new int[]{points[0][0] - points[1][0], points[0][1] - points[1][1]};
        int[] v2 = new int[]{points[1][0] - points[2][0], points[1][1] - points[2][1]};
        return v1[0] * v2[1] - v1[1] * v2[0] != 0;
    }
}
