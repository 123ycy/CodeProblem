package normal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: ImplementQueueUsingStacks
 * @Description: 232. 用栈实现队列
 * @Author: zhaooo
 * @Date: 2024/02/21 10:59
 */
public class ImplementQueueUsingStacks {
    class MyQueue {
        Deque<Integer> inStack;
        Deque<Integer> outStack;

        public MyQueue() {
            inStack = new ArrayDeque<>();
            outStack = new ArrayDeque<>();
        }

        public void push(int x) {
            inStack.addLast(x);
        }

        public int pop() {
            if (outStack.isEmpty()) {
                in2out();
            }
            return outStack.removeLast();
        }

        public int peek() {
            if (outStack.isEmpty()) {
                in2out();
            }
            return outStack.getLast();
        }

        public boolean empty() {
            return inStack.isEmpty() && outStack.isEmpty();
        }

        private void in2out() {
            while (!inStack.isEmpty()) {
                outStack.addLast(inStack.removeLast());
            }
        }
    }
}
