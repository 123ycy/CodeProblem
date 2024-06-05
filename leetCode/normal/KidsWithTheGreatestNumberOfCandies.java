package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: KidsWithTheGreatestNumberOfCandies
 * @Description: 1431. 拥有最多糖果的孩子
 * @Author: zhaooo
 * @Date: 2023/09/19 19:26
 */
public class KidsWithTheGreatestNumberOfCandies {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> res = new ArrayList<>(candies.length);
        int max = 0;
        for (int candy : candies) {
            max = Math.max(candy, max);
        }
        for (int candy : candies) {
            res.add(candy + extraCandies >= max);
        }
        return res;
    }
}
