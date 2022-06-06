package class04;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-25 21:39
 * @description 求小和
 */
public class Code02_SmallSum {

    /**
     * 两层for循环来求小和
     *
     * @param arr
     * @return
     */
    public static int smallSumByFor(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    sum += arr[j];
                }
            }
        }
        return sum;
    }

    /**
     * 求数组的小和
     *
     * @param arr
     * @return
     */
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return mergeSort(arr, 0, arr.length - 1);
    }

    private static int mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return mergeSort(arr, L, mid) +
                mergeSort(arr, mid + 1, R) +
                merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int index = 0;
        int sum = 0;
        int l = L;
        int r = M + 1;
        while (l <= M && r <= R) {
            //相等时先放右边，因为左边还有对右边下一个位置产生小和
            //因为相等不知道右边几个数比左边大
            sum += arr[l] < arr[r] ? (arr[l] * (R - r + 1)) : 0;
            help[index++] = arr[l] < arr[r] ? arr[l++] : arr[r++];
        }

        while (l <= M) {
            help[index++] = arr[l++];
        }

        while (r <= R) {
            help[index++] = arr[r++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return sum;
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
            if (smallSum(arrOriginal) != smallSumByFor(arrCopyOne)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
