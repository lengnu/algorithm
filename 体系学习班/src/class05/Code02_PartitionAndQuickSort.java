package class05;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-26 22:51
 * @description TODO
 */
public class Code02_PartitionAndQuickSort {

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * L-R以R为目标，小于等于arr[R]的放左边，大于放右边，返回R应该划分的位置
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }

        int lessEqual = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, ++lessEqual, index++);
            } else {
                index++;
            }
        }
        //把目标和大于区域的第一个值进行交换，返回目标值的位置
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    /**
     * 荷兰国旗问题
     * L-R以R为目标，小于arr[R]的放左边，等于放中间，大于放右边
     * 返回等于区域的范围
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }

        int lessLeft = L - 1;
        //最后一个数先不参与
        int rightBig = R;
        int index = L;
        while (index < rightBig) {
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++lessLeft);
            } else if (arr[index] > arr[R]) {
                swap(arr, index, --rightBig);
            } else {
                index++;
            }
        }
        //把目标和大于区域的第一个值进行交换，返回目标值的位置
        swap(arr, rightBig, R);
        return new int[]{lessLeft + 1, rightBig};
    }

    /**
     * 快排1.0,只划分小于等于  大于
     * @param arr
     */
    public static void sort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort1(arr, 0, arr.length - 1);
    }

    /**
     * 小于等于放左，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int index = partition(arr,0,R);
        quickSort1(arr, L, index - 1);
        quickSort1(arr, index + 1, R);
    }

    /**
     * 快排2.0 划分小于、等于、大于区域
     * @param arr
     */
    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort2(arr, 0, arr.length - 1);
    }

    /**
     * 小于放左，等于放中间，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalArea = netherLandsFlag(arr, L, R);
        quickSort2(arr, L, equalArea[0] - 1);
        quickSort2(arr, equalArea[0] + 1, R);
    }

    /**
     * 快排3.0 划分小于、等于、大于区域,并且随机选择一个数和最右位置进行交换
     * @param arr
     */
    public static void sort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort3(arr, 0, arr.length - 1);
    }

    /**
     * 3.0 小于放左，等于放中间，大于放右边
     *
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        //随机选择一个位置和最右边交换在进行partition过程
        swap(arr, (int) ((Math.random() * (R - L + 1)) + L),R );
        int[] equalArea = netherLandsFlag(arr, L, R);
        quickSort3(arr, L, equalArea[0] - 1);
        quickSort3(arr, equalArea[0] + 1, R);
    }

    /**
     * 快排3.0迭代版本
     * @param arr
     */
    public static void sort4(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }

        swap(arr,arr.length - 1,(int) (Math.random() * (arr.length)));
        int[] quickEquals = netherLandsFlag(arr, 0, arr.length - 1);
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0,quickEquals[0] -1});
        stack.push(new int[]{quickEquals[1] + 1,arr.length - 1});
        while (!stack.isEmpty()){
            int[] curEquals = stack.pop();
            if (curEquals[0] < curEquals[1]){
                int L = curEquals[0];
                int R = curEquals[1];
                swap(arr,R,(int) (Math.random() * (R - L + 1) + L));
                quickEquals = netherLandsFlag(arr, L, R);
                stack.push(new int[]{L,quickEquals[0] - 1});
                stack.push(new int[]{quickEquals[1] + 1,R});
            }
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
            sort4(arrOriginal);
            if (!Arrays.equals(arrCopyOne,arrOriginal)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
