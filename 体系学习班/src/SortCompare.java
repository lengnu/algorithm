import class04.Code01_MergeSort;
import class05.Code02_PartitionAndQuickSort;
import class06.Code02_HeapSort;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-05 22:13
 * @description 各种排序速度比较
 */
public class SortCompare {
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
        int maxValue = 3000;
        int maxLen = 100000;
        int times = 10000;
        System.out.println("测试开始");
        long timeQuick = 0;
        long timeMerge = 0;
        long timeHeap = 0;
        long timeSystem = 0;
        long start = 0;
        long end = 0;
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrQuick = new int[arrOriginal.length];
            int[] arrMerge = new int[arrOriginal.length];
            int[] arrHeap = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrQuick, 0, arrOriginal.length);
            System.arraycopy(arrOriginal, 0, arrMerge, 0, arrOriginal.length);
            System.arraycopy(arrOriginal, 0, arrHeap, 0, arrOriginal.length);
            //快排
            start = System.currentTimeMillis();
            Code02_PartitionAndQuickSort.sort3(arrQuick);
            end = System.currentTimeMillis();
            timeQuick += (end - start);

            //堆排
            start = System.currentTimeMillis();
            Code02_HeapSort.sort(arrHeap);
            end = System.currentTimeMillis();
            timeHeap += (end - start);

            //归并排序
            start = System.currentTimeMillis();
            Code01_MergeSort.sort(arrMerge);
            end = System.currentTimeMillis();
            timeMerge += (end - start);

            //系统排序
            start = System.currentTimeMillis();
            Arrays.sort(arrOriginal);
            end = System.currentTimeMillis();
            timeSystem += (end - start);

            if (!Arrays.equals(arrHeap, arrOriginal) ||
                    !Arrays.equals(arrOriginal, arrMerge) ||
                    !Arrays.equals(arrOriginal, arrQuick)
            ) {
                System.out.println("出错了...");
                System.out.println(Arrays.toString(arrOriginal));
                System.out.println(Arrays.toString(arrQuick));
                System.out.println(Arrays.toString(arrHeap));
                System.out.println(Arrays.toString(arrMerge));
                System.exit(1);
            }
        }
        System.out.println("快速排序平均时间：" + (timeQuick ));
        System.out.println("堆排序平均时间：" + (timeHeap ));
        System.out.println("归并排序平均时间：" + (timeMerge ));
        System.out.println("系统排序平均时间：" + (timeSystem ));
        System.out.println("测试结束");
    }
}
