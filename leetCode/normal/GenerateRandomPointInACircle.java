package normal;

import java.util.Random;

/**
 * @ClassName: GenerateRandomPointInACircle
 * @Description: 478. 在圆内随机生成点
 * @Author: zhaooo
 * @Date: 2022/6/6/11:24
 */
public class GenerateRandomPointInACircle {
    /**
     * 分布函数
     * https://leetcode.cn/problems/generate-random-point-in-a-circle/solution/zai-yuan-nei-sui-ji-sheng-cheng-dian-by-qp342/
     * 角度固定后，半径的随机并不应该是均匀的。这样会造成越靠近圆中心越密集，圆边缘稀疏。
     */
    class Solution {
        private Random random;
        private double size, x, y;

        public Solution(double radius, double x_center, double y_center) {
            x = x_center;
            y = y_center;
            size = Math.PI * radius * radius;
            random = new Random();
        }

        public double[] randPoint() {
            double theta = random.nextDouble() * 2 * Math.PI, r = Math.sqrt(random.nextDouble() * size / Math.PI);
            return new double[]{x + Math.cos(theta) * r, y + Math.sin(theta) * r};
        }
    }
}
