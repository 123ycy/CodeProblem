package normal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RelativeSortArray
 * @Description: 1122. 数组的相对排序
 * @Author: zhaooo
 * @Date: 2023/12/27 17:01
 */
public class RelativeSortArray {
    Map<Integer, Integer> map;

    /**
     * 快速排序
     * @param arr1
     * @param arr2
     * @return
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        map = new HashMap<>(arr2.length);
        for (int i = 0; i < arr2.length; i++) {
            map.put(arr2[i], i);
        }
        quickSort(arr1, 0, arr1.length - 1);
        return arr1;
    }

    /**
     * 利用工具类
     * @param arr1
     * @param arr2
     * @return
     */
    public int[] relativeSortArray2(int[] arr1, int[] arr2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr2.length; i++) {
            map.put(arr2[i], i);
        }
        Integer[] newArr = Arrays.stream(arr1).boxed().toArray(Integer[]::new);
        Arrays.sort(newArr, Comparator.comparingInt(o -> map.getOrDefault(o, (int) (o + 1e4))));
        return Arrays.stream(newArr).mapToInt(Integer::intValue).toArray();
    }

    void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            int pivotPos = partition(arr, l, r);
            quickSort(arr, l, pivotPos - 1);
            quickSort(arr, pivotPos + 1, r);
        }
    }

    int partition(int[] arr, int l, int r) {
        int pivot = arr[l];
        // arr[i] + 1000 保证当arr[i]不在arr2中时，一定在数组后面，且可以相互排序
        int compareVal = map.getOrDefault(pivot, pivot + ((int) 1e4));
        while (l < r) {
            while (l < r && map.getOrDefault(arr[r], arr[r] + (int) 1e4) >= compareVal) {
                r--;
            }
            arr[l] = arr[r];
            while (l < r && map.getOrDefault(arr[l], arr[l] + (int) 1e4) <= compareVal) {
                l++;
            }
            arr[r] = arr[l];
        }
        arr[l] = pivot;
        return l;
    }
}
