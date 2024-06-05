import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @ClassName: ProducerAndConsumer
 * @Description: 生产者消费者模型
 * @Author: zhaooo
 * @Date: 2024/03/31 16:28
 */
public class ProducerAndConsumer {

    // 缓冲区初始化为0
    static Semaphore full = new Semaphore(0);
    // 空闲缓冲区，假如为5
    static Semaphore empty = new Semaphore(4);
    // 互斥锁
    static Semaphore mutex = new Semaphore(1);
    // 用一个list来模拟生产和消费过程，因为需要频繁增删，所以用链表
    static LinkedList<Object> list = new LinkedList<>();

    static class producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void produce() throws InterruptedException {
            // 消耗一个空缓冲区
            empty.acquire();
            // 进入临界区
            mutex.acquire();
            // 生产数据
            Thread.sleep(2000);
            list.add(new Object());
            System.out.println(Thread.currentThread().getName() + " produce one product and the list size is " + list.size());
            // 离开临界区，释放互斥信号量
            mutex.release();
            // 提供产品，产品缓冲区+1
            full.release();
        }
    }

    static class consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void consumer() throws InterruptedException {
            // 获取满缓冲区
            full.acquire();
            // 进入临界区
            mutex.acquire();
            // 消费数据
            Thread.sleep(2000);
            list.remove();
            // 离开临界区，释放互斥信号量
            System.out.println(Thread.currentThread().getName() + " consume one product and the list size is " + list.size());
            mutex.release();
            // 提供产品，产品缓冲区+1
            empty.release();
        }
    }

    public static void main(String[] args) {
        new Thread(new producer()).start();
        new Thread(new producer()).start();
        new Thread(new consumer()).start();
    }
}