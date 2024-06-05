package normal;

import java.util.*;

/**
 * @ClassName: FlattenNestedListIterator
 * @Description: 341. 扁平化嵌套列表迭代器
 * @Author: zhaooo
 * @Date: 2023/04/04 20:45
 */
public class FlattenNestedListIterator {
    /**
     * 队列
     * 初始化时DFS进行处理，将元素都放至队列
     */
    public class NestedIterator implements Iterator<Integer> {
        Deque<Integer> queue = new ArrayDeque<>();

        public NestedIterator(List<NestedInteger> nestedList) {
            dfs(nestedList);
        }

        @Override
        public Integer next() {
            return hasNext() ? queue.pollFirst() : Integer.valueOf(-1);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        void dfs(List<NestedInteger> list) {
            for (NestedInteger item : list) {
                if (item.isInteger()) {
                    queue.addLast(item.getInteger());
                } else {
                    dfs(item.getList());
                }
            }
        }
    }

    /**
     * 栈
     * 将所有的NestedInteger逆序放到栈中，当需要展开的时候才进行展开
     */
    public class NestedIterator2 implements Iterator<Integer> {

        Deque<NestedInteger> stack = new ArrayDeque<>();

        public NestedIterator2(List<NestedInteger> list) {
            for (int i = list.size() - 1; i >= 0; i--) {
                // 重点是要保存所有未访问元素
                // 只是对stack进行操作，所以要保留所有信息，就算一个NestedInteger有多层嵌套，保存顶层相当于保存所有嵌套
                NestedInteger item = list.get(i);
                stack.addLast(item);
            }
        }

        @Override
        public Integer next() {
            return hasNext() ? stack.pollLast().getInteger() : -1;
        }

        @Override
        public boolean hasNext() {
            // 为空表示所有元素都已经访问
            if (stack.isEmpty()) {
                return false;
            } else {
                NestedInteger item = stack.peekLast();
                // 栈顶是一个int
                if (item.isInteger()) {
                    return true;
                } else {
                    // 反之
                    item = stack.pollLast();
                    List<NestedInteger> list = item.getList();
                    // 将该栈顶对应的list的所有元素入栈
                    for (int i = list.size() - 1; i >= 0; i--) {
                        stack.addLast(list.get(i));
                    }
                    // 继续hasNext(),直到栈顶是一个int
                    return hasNext();
                }
            }
        }
    }


    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
}
