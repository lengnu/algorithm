package class06;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-27 22:57
 * @description 大根堆
 */
public class Code01_Heap<T> {
    private T[] data;
    private int limit;
    private int heapSize;
    private Comparator<T> comparator;

    public Code01_Heap(int limit, Comparator<T> comparator) {
        this.limit = limit;
        heapSize = 0;
        data = (T[]) new Object[limit];
        this.comparator = comparator;
    }

    /**
     * 堆上浮操作
     *
     * @param index 上浮的开始位置
     */
    private void heapInsert(int index) {
        while (comparator.compare(data[index], data[(index - 1) / 2]) > 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 堆下沉操作
     *
     * @param index
     * @param heapSize
     */
    private void heapify(int index, int heapSize) {
        //左孩子
        int left = 2 * index + 1;
        while (left < heapSize) {
            //左右孩子较大的
            int largest = left + 1 < heapSize && comparator.compare(data[left + 1], data[(left)]) > 0 ?
                    left + 1 : left;
            //孩子较大的与父亲比较
            largest = comparator.compare(data[largest], data[index]) > 0 ? largest : index;
            if (largest == index) {
                break;
            }
            swap(largest, index);
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 给堆中添加一个元素
     *
     * @param value
     */
    public void add(T value) {
        if (heapSize == limit) {
            throw new RuntimeException("当前堆已满");
        }
        data[heapSize] = value;
        heapInsert(heapSize++);
    }

    /**
     * 堆中弹出一个元素
     */
    public T pop() {
        if (heapSize == 0) {
            throw new RuntimeException("当前堆为空");
        }
        T result = data[0];
        swap(0, --heapSize);
        heapify(0, heapSize);
        return result;
    }

    /**
     * 交换数组中两个位置
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Code01_Heap<Integer> code01Heap = new Code01_Heap<>(10000, Comparator.comparingInt(o -> o));
        //默认小根堆，需要传入自定义参数
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt((o) -> -o));
        System.out.println("测试开始");
        for (int i = 0;i < 10000;i++){
            int num = (int) (Math.random() * 10000);
            code01Heap.add(num);
            queue.add(num);
        }
        for (int i = 0; i < 10000; i++) {
            if (!code01Heap.pop().equals(queue.poll())){
                System.out.println("出错了");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
