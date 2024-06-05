package normal;

/**
 * @ClassName: ValidateStackSequences
 * @Description: 946. 验证栈序列 
 * @Author: zhaooo
 * @Date: 2022/8/31/14:50
 */
public class ValidateStackSequences {
    /**
     * 模拟
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // top指向stack将要插入的位置
        // i为pushed的指针
        // j为popped的指针
        int size = popped.length, top = 0, i = 0, j = 0;
        int[] stack = new int[size];
        while (i < size && j < size) {
            stack[top++] = pushed[i++];
            while (top > 0 && stack[top - 1] == popped[j]) {
                top--;
                j++;
            }
        }
        return top == 0;
    }

    /**
     * 使用pushed作为栈
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences2(int[] pushed, int[] popped) {
        int n = pushed.length, top = 0;
        for (int i = 0, j = 0; i < n; i++) {
            pushed[top++] = pushed[i];
            while (top > 0 && pushed[top - 1] == popped[j]) {
                ++j;
                top--;
            }
        }
        return top == 0;
    }
}
