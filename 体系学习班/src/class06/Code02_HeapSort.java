package class06;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-28 20:29
 * @description 堆排序
 */
public class Code02_HeapSort {
    /**
     * 堆排序
     * @param arr
     */
    public static void sort(int[] arr){
        heapSort(arr);
    }

    private static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int heapSize = arr.length;
        //建大根堆,从上往下O(N*logN)
//        for (int i = 1; i < heapSize; i++) {
//            //heapInsert过程 O(logN)
//           heapInsert(arr,i);
//        }
//
        //从下往上建立堆O(N)
        for (int i = heapSize - 1;i >= 0;i--){
            heapify(arr,i,heapSize);
        }

        //开始逐次将最大值放在数组末尾
        while (heapSize > 1) {
            swap(arr, 0, --heapSize);
            heapify(arr,0,heapSize);
        }
    }

    /**
     * heapInsert过程,O(logN)
     */
    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * heapify过程,O(logN)
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ?
                    left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = 2 * index + 1;
        }
    }

    /**
     * 交换数组中两个元素
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 生成随机长度数组
     *
     * @param maxVale
     * @param maxLen
     * @return
     */
    private static int[] generateRandomArray(int maxVale, int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = randomNumber(maxVale);
        }
        return arr;
    }

    /**
     * 返回[-max,max]中随机一个元素
     *
     * @param maxValue
     * @return
     */
    private static int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrCopyOne);
            sort(arrOriginal);
            if (!Arrays.equals(arrCopyOne,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
