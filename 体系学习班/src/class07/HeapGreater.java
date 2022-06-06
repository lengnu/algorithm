package class07;

import java.util.*;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-29 11:56
 * @description 加强堆
 */
public class HeapGreater<T> {
    private List<T> data;
    private int heapSize;
    /**
     * 反向索引表，记录元素在堆中的位置
     */
    private Map<T, Integer> indexMap;
    private Comparator<T> comparator;

    public HeapGreater(Comparator<T> comparator) {
        this.comparator = comparator;
        this.data = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
    }

    /**
     * 给堆中加入一个元素
     * @param value
     */
    public void add(T value){
        data.add(value);
        indexMap.put(value,heapSize);
        heapInsert(heapSize++);
    }

    /**
     * 堆中弹出一个元素
     * @return
     */
    public T poll(){
        T value = data.get(0);
        swap(0,heapSize - 1);
        indexMap.remove(value);
        data.remove(--heapSize);
        heapify(0);
        return value;
    }

    /**
     * 返回堆第一个元素不弹出
     * @return
     */
    public T peek(){
        return data.get(0);
    }

    /**
     * 堆大小
     * @return
     */
    public int size(){
        return heapSize;
    }

    /**
     * 堆是否为空
     * @return
     */
    public boolean isEmpty(){
        return heapSize == 0;
    }

    /**
     * 移除堆中某个元素
     * @param value
     * @return  返回删除元素在堆中的位置
     */
    public int remove(T value){
        T replace = data.get(heapSize - 1);
        int index = indexMap.get(value);
        indexMap.remove(value);
        data.remove(--heapSize);
        if (value != replace) {
            data.set(index,replace);
            indexMap.put(replace,index);
            heapInsert(index);
            heapify(index);
        }
        return index;
    }

    /**
     * 堆上浮操作
     * @param index 从index上浮
     */
    private void heapInsert(int index){
        while (comparator.compare(data.get(index),data.get((index - 1) / 2)) > 0){
            swap(index,(index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 堆下沉操作
     * @param index     从index位置下沉
     */
    private void heapify(int index){
        int left = 2 * index + 1;
        while (left < heapSize){
            int largest = left + 1 < heapSize &&
                    comparator.compare(data.get(left + 1),data.get(left)) > 0 ?
                    left + 1 : left;
            largest = comparator.compare(data.get(index),data.get(largest)) > 0 ?  index : largest;
            if (largest == index){
                break;
            }
            swap(index,largest);
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 交换堆中两个位置的元素，并将反向索引表中的记录更新
     * @param i
     * @param j
     */
    private void swap(int i,int j){
        T value1 = data.get(i);
        T value2 = data.get(j);
        //更新反向索引表
        indexMap.put(value1,j);
        indexMap.put(value2,i);
        //互换堆中两个元素
        data.set(i,value2);
        data.set(j,value1);
    }

    /**
     * 堆中是否包含某个元素
     * @param value
     * @return
     */
    public boolean containsKey(T value){
        return indexMap.containsKey(value);
    }

    /**
     * 修改堆中某个元素
     * @param value
     */
    public void resign(T value){
        heapify(indexMap.get(value));
        heapInsert(indexMap.get(value));
    }

    /**
     * 获取堆中所有元素
     * @return
     */
    public List<T> getAllElements(){
        List<T> result = new ArrayList<>();
        for (T datum : data) {
            result.add(datum);
        }
        return result;
    }


    public static void main(String[] args) {
        HeapGreater<Integer> code01Heap = new HeapGreater<>( Comparator.comparingInt(o -> o));
        //默认小根堆，需要传入自定义参数
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt((o) -> -o));
        System.out.println("测试开始");
        for (int i = 0;i < 10000;i++){
            int num = (int) (Math.random() * 10000);
            code01Heap.add(num);
            queue.add(num);
        }
        for (int i = 0; i < 10000; i++) {
            if (!code01Heap.poll().equals(queue.poll())){
                System.out.println("出错了");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
