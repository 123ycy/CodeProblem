package normal;

import java.util.*;

/**
 * @ClassName: TopKFrequentElements
 * @Description: 347. 前 K 个高频元素
 * @Author: zhaooo
 * @Date: 2023/12/09 13:03
 */
public class TopKFrequentElements {

    /**
     * 堆
     *
     * @param nums
     * @param k
     * @return
     */
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            // 保存较大元素的小顶堆
            PriorityQueue<Integer> queue = new PriorityQueue<>(k, Comparator.comparingInt(map::get));
            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
            map.forEach((num, count) -> {
                if (queue.size() < k) {
                    queue.offer(num);
                } else if (map.get(queue.peek()) < count) {
                    queue.poll();
                    queue.offer(num);
                }
            });
            return queue.stream().mapToInt(Integer::intValue).toArray();
        }

    /**
     * 快排 + Entry
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> occurrences = new HashMap<>();
        // 统计每个元素出现的次数
        for (int num : nums) {
            occurrences.put(num, occurrences.getOrDefault(num, 0) + 1);
        }
        // 使用Map.Entry初始化List
        List<Map.Entry<Integer, Integer>> values = new ArrayList<>(occurrences.entrySet());
        int[] ret = new int[k];
        quickSort(values, 0, values.size() - 1, ret, 0, k);
        return ret;
    }

    // 在[start, end]内快排
    // List<Map.Entry<num, 出现次数>>
    public void quickSort(List<Map.Entry<Integer, Integer>> values, int start, int end, int[] ret, int retIndex, int k) {
        int picked = (int) (Math.random() * (end - start + 1)) + start;
        // 交换，使用变量保存pivot，空出start
        Collections.swap(values, picked, start);
        int pivot = values.get(start).getValue();
        int index = start;
        // 从start+1开始，将大于pivot的元素放到左侧
        for (int i = start + 1; i <= end; i++) {
            if (values.get(i).getValue() >= pivot) {
                Collections.swap(values, index + 1, i);
                index++;
                // [start+1, index]都是大于pivot的
            }
        }
        // 排序后[pivot, 大于pivot, 小于pivot] [start ,[start+1, index], [index+1, end]]
        // 将pivot放到index位置 [大于pivot, pivot, 小于pivot]
        Collections.swap(values, start, index);
        // 大于pivot的个数大于k，继续排序
        if (k < index - start + 1) {
            quickSort(values, start, index - 1, ret, retIndex, k);
        } else {
            // 已经确定的元素不再排序
            for (int i = start; i <= index; i++) {
                ret[retIndex++] = values.get(i).getKey();
            }
            // 前k个没确定完全，排序[index+1, end]来获取剩下的k - (index - start + 1)较大元素
            if (k > index - start + 1) {
                quickSort(values, index + 1, end, ret, retIndex, k - (index - start + 1));
            }
        }
    }
}
