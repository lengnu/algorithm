package class08;

import java.util.Arrays;
import java.util.Map;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 22:36
 * @description 计数排序
 */
public class Code02_CountSort {
    /**
     * 计数排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        countSort(arr);
    }

    private static void countSort(int[] arr) {
        int N = arr.length;
        int[] maxAndMin = findMaxAndMin(arr);
        int[] bucket = new int[maxAndMin[1] - maxAndMin[0] + 1];
        for (int i = 0; i < N; i++) {
            //将负数 转换到 正数的下标
            // 将所有数字限定到 min - max发明为
            bucket[arr[i] - maxAndMin[0]]++;
        }

        //生产累计数组，保证排序稳定性
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] += bucket[i - 1];
        }

        //开始元素倒出桶
        int[] newTable = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            newTable[--bucket[arr[i] - maxAndMin[0]]] = arr[i];
        }

        System.arraycopy(newTable, 0, arr, 0, N);
    }
    
    /**
     * 找到数组的最大值和最小值
     *
     * @param arr
     * @return
     */
    private static int[] findMaxAndMin(int[] arr) {
        int N = arr.length;
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < N; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        return new int[]{min, max};
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
        int maxValue = 300;
        int maxLen = 100;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            Arrays.sort(arrOriginal);
            sort(arrCopyOne);
            if (!Arrays.equals(arrCopyOne, arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }


}
