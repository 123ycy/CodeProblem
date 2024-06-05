package normal;

/**
 * @ClassName: DuplicateZeros
 * @Description: 1089. 复写零
 * @Author: zhaooo
 * @Date: 2022/6/17/10:43
 */
public class DuplicateZeros {
    /**
     * 双指针
     * @param arr
     */
    public void duplicateZeros(int[] arr) {
        int count = 0;
        int i = 0, j = arr.length - 1;
        for (; i + count < arr.length; i++) {
            if (arr[i] == 0) {
                count++;
            }
        }
        if (count == 0) {
            return;
        }
        i--;
        // 此时最末尾的0不复制
        if (arr[i] == 0 && i + count == arr.length) {
            arr[j--] = arr[i--];
        }
        for (; j > i; i--, j--) {
            arr[j] = arr[i];
            if (arr[i] == 0) {
                arr[--j] = 0;
            }
        }
    }

    /**
     * 栈+双指针
     * @param arr
     */
    public void duplicateZeros2(int[] arr) {
        int n = arr.length;
        int top = 0;
        int i = -1;
        while (top < n) {
            i++;
            if (arr[i] != 0) {
                top++;
            } else {
                top += 2;
            }
        }
        int j = n - 1;
        if (top == n + 1) {
            arr[j] = 0;
            j--;
            i--;
        }
        while (j >= 0) {
            arr[j] = arr[i];
            j--;
            if (arr[i] == 0) {
                arr[j] = arr[i];
                j--;
            }
            i--;
        }
    }
}
