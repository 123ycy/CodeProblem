package normal;

import java.util.Random;

/**
 * @ClassName: ImplementRand10UsingRand7
 * @Description: 470. 用 Rand7() 实现 Rand10()
 * @Author: zhaooo
 * @Date: 2024/05/03 16:39
 */
public class ImplementRand10UsingRand7 {
    Random random = new Random();

    /**
     * (randX() - 1)*Y + randY() 可以等概率的生成[1, X * Y]范围的随机数 超出范围的直接拒绝
     * https://leetcode.cn/problems/implement-rand10-using-rand7/solutions/979495/mo-neng-gou-zao-fa-du-li-sui-ji-shi-jian-9xpz/
     * @return
     */
    public int rand10() {
        int num1, num2;
        do {
            num1 = rand7();
        } while (num1 > 2);
        do {
            num2 = rand7();
        } while (num2 > 5);
        return (num1 - 1) * 5 + num2;
    }

    public int rand7() {
        return random.nextInt(7) + 1;
    }
}
