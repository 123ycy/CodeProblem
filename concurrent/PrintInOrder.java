import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: PrintInOrder
 * @Description: 1114. 按序打印
 * @Author: zhaooo
 * @Date: 2024/01/06 17:08
 */
public class PrintInOrder {

    // AtomicInteger 原子类
    public static class Foo1 {
        AtomicInteger atomic = new AtomicInteger(0);

        public Foo1() {

        }

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            atomic.incrementAndGet();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            while (atomic.get() != 1) {
            }
            printSecond.run();
            atomic.incrementAndGet();
        }

        public void third(Runnable printThird) throws InterruptedException {
            while (atomic.get() != 2) {
            }
            printThird.run();
        }
    }

    // volatile
    public static class Foo2 {
        // volatile保证可见性
        // i每次改变就立即从缓存写回内存 否则可能超时
        volatile int i = 0;

        public Foo2() {

        }

        public void first(Runnable printFirst) {
            printFirst.run();
            i++;
        }

        public void second(Runnable printSecond) {
            while (i != 1) {
            }
            printSecond.run();
            i++;
        }

        public void third(Runnable printThird) {
            while (i != 2) {
            }
            printThird.run();
        }
    }

    // 通过synchronized关键字实现
    public static class Foo3 {
        // 控制变量
        private int flag = 0;
        // 定义Object对象为锁
        private final Object lock = new Object();

        public Foo3() {
        }

        public void first(Runnable printFirst) throws InterruptedException {
            synchronized (lock) {
                // wait() 方法通常会放在一个循环中，以防止虚假唤醒（spurious wakeups）的发生，虚假唤醒是指在没有调用 notify() 或 notifyAll() 的情况下，线程被唤醒的情况
                while (flag != 0) {
                    lock.wait();
                    // 唤醒后的执行点
                }
                printFirst.run();
                // 定义成员变量为 1
                flag = 1;
                // 唤醒其余所有的线程
                lock.notifyAll();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            synchronized (lock) {
                // 如果成员变量不为1则让二号等待
                while (flag != 1) {
                    lock.wait();
                }
                printSecond.run();
                flag = 2;
                lock.notifyAll();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            synchronized (lock) {
                while (flag != 2) {
                    lock.wait();
                }
                printThird.run();
                flag = 0;
                lock.notifyAll();
            }
        }
    }

    // CountDownLatch
    public static class Foo4 {

        private final CountDownLatch firstDone;
        private final CountDownLatch secondDone;

        public Foo4() {
            firstDone = new CountDownLatch(1);
            secondDone = new CountDownLatch(1);
        }

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            firstDone.countDown();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            firstDone.await();
            printSecond.run();
            secondDone.countDown();
        }

        public void third(Runnable printThird) throws InterruptedException {
            secondDone.await();
            printThird.run();
        }
    }

    public static class Foo5 {
        // 声明两个 Semaphore变量
        private final Semaphore spa, spb;

        public Foo5() {
            // 初始化Semaphore为0的原因：如果这个Semaphore为零，如果另一线程调用(acquire)这个Semaphore就会产生阻塞，便可以控制second和third线程的执行
            spa = new Semaphore(0);
            spb = new Semaphore(0);
        }

        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            // 只有等first线程释放Semaphore后使Semaphore值为1,另外一个线程才可以调用（acquire）
            spa.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            spa.acquire();
            printSecond.run();
            spb.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            spb.acquire();
            printThird.run();
        }
    }
}
