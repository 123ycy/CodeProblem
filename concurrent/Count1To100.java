import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: concurrency.Count1To100
 * @Description: 3个线程从0数到100
 * @Author: zhaooo
 * @Date: 2024/03/18 19:37
 */
public class Count1To100 {
    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(new Task(0)).start();
        new Thread(new Task(1)).start();
        new Thread(new Task(2)).start();
    }

    // 无锁编程
    static class Task implements Runnable {
        int idx;

        Task(int idx) {
            this.idx = idx;
        }

        @Override
        public void run() {
            while (true) {
                int k = i.get();
                if (k > 100) {
                    break;
                }
                if ((k % 3) == idx) {
                    System.out.println(Thread.currentThread().getName() + "  " + k);
                    i.getAndIncrement();
                }
            }
        }
    }
}
