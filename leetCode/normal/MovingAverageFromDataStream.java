package normal;

/**
 * @ClassName: MovingAverageFromDataStream
 * @Description: 364. 数据流中的移动平均值
 * LCR 041. 数据流中的移动平均值
 * @Author: zhaooo
 * @Date: 2023/11/22 16:00
 */
public class MovingAverageFromDataStream {
    class MovingAverage {
        int sum;
        int length;
        int front;
        int rear;
        int[] queue;

        /**
         * Initialize your data structure here.
         */
        public MovingAverage(int size) {
            queue = new int[size];
            sum = 0;
            length = 0;
            front = 0;
            rear = 0;
        }

        public double next(int val) {
            if (length == queue.length) {
                sum -= queue[front++];
                front %= queue.length;
                length--;
            }
            queue[rear++] = val;
            rear %= queue.length;
            sum += val;
            length++;
            return (double) sum / length;
        }
    }

    class MovingAverage2 {
        int len;
        int size;
        int sum;
        int[] num;

        /**
         * Initialize your data structure here.
         */
        public MovingAverage2(int size) {
            len = 0;
            this.size = size;
            sum = 0;
            num = new int[size];
        }

        public double next(int val) {
            sum -= num[len % size];
            num[len++ % size] = val;
            sum += val;
            return (double) sum / Math.min(len, size);
        }
    }
}
