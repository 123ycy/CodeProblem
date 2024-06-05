package normal;

import dependency.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: MostFrequentSubtreeSum
 * @Description: 508. 出现次数最多的子树元素和
 * @Author: zhaooo
 * @Date: 2022/6/19/15:15
 */
public class MostFrequentSubtreeSum {
    Map<Integer, Integer> map = new HashMap<>();
    int max = 0;
    List<Integer> res = new ArrayList<>();

    public int[] findFrequentTreeSum(TreeNode root) {
        sum(root);
        int[] l = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            l[i] = res.get(i);
        }
        return l;
    }

    public int sum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int sum = node.val + sum(node.left) + sum(node.right);
        int times = map.getOrDefault(sum, 0) + 1;
        if (times > max) {
            res.clear();
            max = times;
        }
        if (times == max) {
            res.add(sum);
        }
        map.put(sum, times);
        return sum;
    }
}
