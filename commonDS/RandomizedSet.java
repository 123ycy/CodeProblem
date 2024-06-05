import java.util.*;

/**
 * @ClassName: RandomizedSet
 * @Description: O(1) 时间插入、删除和获取随机元素
 * @Author: zhaooo
 * @Date: 2023/10/30 14:17
 */
public class RandomizedSet {
    List<Integer> list;
    Map<Integer, Integer> map;
    Random random;
    int size;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
        size = 0;
    }

    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        list.add(val);
        map.put(val, size);
        size++;
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        // 交换最后一个元素和要删除元素的位置，再删除最后一个元素
        int lastVal = list.get(size - 1);
        int valIndex = map.get(val);
        list.set(valIndex, lastVal);
        list.remove(size - 1);
        // 保持map中数据的一致
        map.put(lastVal, valIndex);
        map.remove(val);
        size--;
        return true;
    }

    public int getRandom() {
        return list.get(random.nextInt(size));
    }
}
