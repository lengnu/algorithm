package class04;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-24 21:01
 * @description 归并排序
 */
public class Code01_MergeSort {

    /**
     * 归并排序递归版本
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归归并排序
     *
     * @param arr
     * @param L
     * @param R
     */
    private static void mergeSort(int[] arr, int L, int R) {
        //注意，如果写L==R数组长度不能为0
        //否则，就要写成L >= R
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        //对左边排序
        mergeSort(arr, L, mid);
        //对右边排序
        mergeSort(arr, mid + 1, R);
        //归并
        merge(arr, L, mid, R);
    }

    /**
     * 归并过程，不是递归
     *
     * @param arr
     * @param L
     * @param R
     * @param mid
     */
    private static void merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        //存储新数组的下标
        int k = 0;
        //mid是左边结束的位置
        int l = L;
        int r = mid + 1;
        while (l <= mid && r <= R) {
            if (arr[l] <= arr[r]) {
                help[k++] = arr[l++];
            } else {
                help[k++] = arr[r++];
            }
        }
        //左边没有放完
        while (l <= mid) {
            help[k++] = arr[l++];
        }
        //右边没有放完
        while (r <= R) {
            help[k++] = arr[r++];
        }
        //将排序好的数组返回原数组
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
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

    /**
     * 迭代版本归并排序
     * 每次原则一个步长，从1开始(步长代表一个左组或右组的长度)
     */
    public static void sortNonRecursive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        int mergeSize = 1;
        while (mergeSize < N) {
            //代表每每次调整补偿左边最开始的位置
            int L = 0;
            while (L < N) {
                //左组为[L,M]
                int M = L + mergeSize - 1;
                //左组都不够了，直接跳出本次merge，开始下一轮，必要的一行代码
                if (M > N - 1){
                    break;
                }
                //右组边界
                int R = Math.min(N - 1, M + mergeSize);
                //归并
                merge(arr, L, M, R);
                //下一个左组长度
                L = R + 1;
            }
            //防止Integer.MAX_VALUE溢出，不影响结果
            if (mergeSize > N / 2) {
                break;
            }
            //每次步长加倍
            mergeSize <<= 1;
        }
    }

    public static void main(String[] args) {
        int maxValue = 30;
        int maxLen = 10;
        int times = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] arrOriginal = generateRandomArray(maxValue, maxLen);
            int[] arrCopyOne = new int[arrOriginal.length];
            int[] arrCopyTwo = new int[arrOriginal.length];
            System.arraycopy(arrOriginal, 0, arrCopyOne, 0, arrOriginal.length);
            System.arraycopy(arrOriginal, 0, arrCopyTwo, 0, arrOriginal.length);
            Arrays.sort(arrOriginal);
            sort(arrCopyOne);
            sortNonRecursive(arrCopyTwo);
            if (!Arrays.equals(arrCopyOne, arrOriginal) || !Arrays.equals(arrCopyTwo,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }

}
