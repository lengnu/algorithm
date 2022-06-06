package class08;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 23:23
 * @description 基数排序, 不能出现负数
 */
public class Code03_RadixSort {
    /**
     * 基数排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr);
    }

    public static void radixSort(int[] arr, int base) {
        //10进制的桶
        final int radix = base;
        int N = arr.length;
        int[] help = new int[N];
        int max = Arrays.stream(arr).max().getAsInt();
        int digit = maxBits(max, radix);
        //对每一位进行排序
        for (int d = 1; d <= digit; d++) {
            int[] bucket = new int[radix];
            for (int i = 0; i < arr.length; i++) {
                //获取当前数字 radix进制下 右边第i位的值
                int j = getDigit(arr[i], d, radix);
                bucket[j]++;
            }

            //累加
            for (int i = 1; i < radix; i++) {
                bucket[i] += bucket[i - 1];
            }

            //出桶
            for (int i = N - 1; i >= 0; i--) {
                int j = getDigit(arr[i], d, radix);
                help[--bucket[j]] = arr[i];
            }

            for (int i = 0; i < N; i++) {
                arr[i] = help[i];
            }
        }
    }

    public static void radixSort(int[] arr) {
        radixSort(arr, 8);
    }

    /**
     * 求当前数字base进制的最大位数
     *
     * @param number
     */
    public static int maxBits(int number, int base) {
        int result = 0;
        while (number != 0) {
            result++;
            number /= base;
        }
        return result;
    }

    /**
     * 获取当前数字base进制下右边第d位的值
     *
     * @param number
     * @param d
     * @param base
     * @return
     */
    public static int getDigit(int number, int d, int base) {
        return (number / (int) (Math.pow(base, d - 1))) % base;
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
            arr[i] = (int) (Math.random() * (maxLen + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxValue = 3000;
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
