package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: AsteroidCollision
 * @Description: 735. 小行星碰撞
 * @Author: zhaooo
 * @Date: 2023/11/10 10:12
 */
public class AsteroidCollision {
    /**
     * 栈
     *
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> deque = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            if (asteroid > 0) {
                deque.addLast(asteroid);
            } else {
                if (deque.isEmpty()) {
                    deque.addLast(asteroid);
                    continue;
                }
                while (!deque.isEmpty()) {
                    // 同向
                    if ((deque.getLast() ^ asteroid) >= 0) {
                        deque.addLast(asteroid);
                        break;
                        // asteroid < 0;  deque.removeLast() > 0
                    } else {
                        int last = deque.removeLast();
                        // 两者相等
                        if (asteroid + last == 0) {
                            break;
                        }
                        // 当前asteroid更小
                        if (asteroid + last > 0) {
                            deque.addLast(last);
                            break;
                        }
                        // asteroid到栈底
                        if (asteroid + last < 0 && deque.isEmpty()) {
                            deque.addLast(asteroid);
                            break;
                        }
                    }
                }
            }
        }
        int[] ans = new int[deque.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = deque.removeFirst();
        }
        return ans;
    }

    /**
     * 官方题解
     *
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision2(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int aster : asteroids) {
            boolean alive = true;
            while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0) {
                // aster 是否存在
                alive = stack.peek() < -aster;
                // 栈顶行星爆炸
                if (stack.peek() <= -aster) {
                    stack.pop();
                }
            }
            if (alive) {
                stack.push(aster);
            }
        }
        int size = stack.size();
        int[] ans = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            ans[i] = stack.pop();
        }
        return ans;
    }
}
