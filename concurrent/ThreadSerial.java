import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: concurrency.ThreadSerial
 * @Description: 3个线程按照顺序执行
 * @Author: zhaooo
 * @Date: 2024/03/18 19:30
 */
public class ThreadSerial {
    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(1);
        new Thread(new myWork(latch1, latch2)).start();
        new Thread(new myWork(latch2, latch3)).start();
        new Thread(new myWork(latch3, latch3)).start();
        latch1.countDown();
    }

    static class myWork implements Runnable {
        CountDownLatch latch1;
        CountDownLatch latch2;

        myWork(CountDownLatch latch1, CountDownLatch latch2) {
            this.latch1 = latch1;
            this.latch2 = latch2;
        }

        @Override
        public void run() {
            try {
                latch1.await();
                System.out.println(Thread.currentThread().getName() + " doing something");
                latch2.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
