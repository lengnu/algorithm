package class03;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-23 13:20
 * @description TODO
 */
public class Code04_RingArrayToQueue {
    /**
     * 循环数组数组实现队列
     */
    public static class ArrayToQueue{
        public final int limit;
        public int pushIndex;
        public int popIndex;
        public int[] data;
        public int size;

        public ArrayToQueue(int limit) {
            this.limit = limit;
            popIndex = 0;
            pushIndex = 0;
            size = 0;
            data = new int[limit];
        }


        /**
         * 元素入队列
         * @param value
         */
        public void add(int value){
            if (isFull()){
                throw new RuntimeException("队列已满");
            }
            data[pushIndex++] = value;
            pushIndex %= limit;
            size++;
        }

        /**
         * 元素出队列
         * @return
         */
        public int offer(){
            if (isEmpty()){
                throw new RuntimeException("队列为空");
            }
            int value = data[popIndex++];
            popIndex %= limit;
            size--;
            return value;
        }

        /**
         * 判断队列是否为空
         * @return
         */
        public boolean isEmpty(){
            return size == 0;
        }

        /**
         * 判断队列是否已满
         * @return
         */
        public boolean isFull(){
            return size == limit;
        }
    }
}
