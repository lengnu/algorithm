package class05;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-26 21:53
 * @description TODO
 */
public class Code01_CountOfRangeSum {
    /**
     * 三层for循环求解
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum1(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 两层for循环，前缀和
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum2(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 0;
        int N = arr.length;
        int[] prefixSum = new int[N];
        prefixSum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }

        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = prefixSum[j] - prefixSum[i] + arr[i];
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 归并排序求解
     *
     * @param arr
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum3(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[] prefixSum = new int[N];
        prefixSum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }
        return count(prefixSum, 0, arr.length - 1, lower, upper);
    }

    /**
     * 求原始数组L-R范围内多少累加和在lower-upper范围内
     * 换种思路：求解prefixSum中每一个位置包括自己左边达标的数量
     *
     * @param prefixSum
     * @param L
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    public static int count(int[] prefixSum, int L, int R, int lower, int upper) {
        if (L == R) {
            return (prefixSum[L] <= upper && prefixSum[L] >= lower) ? 1 : 0;
        }
        int mid = L + ((R - L) >> 1);
        return count(prefixSum, L, mid, lower, upper) +
                count(prefixSum, mid + 1, R, lower, upper) +
                merge(prefixSum, L, mid, R, lower, upper);
    }

    public static int merge(int[] prefixSum, int L, int M, int R, int lower, int upper) {
        int[] help = new int[R - L + 1];
        int count = 0;
        int index = 0;
        int windowL = L;
        int windowR = L;
        //merge过程左边窗口不会退
        //对于右边每一个位置求左组
        for (int i = M + 1; i <= R; i++) {
            long min = prefixSum[i] - upper;
            long max = prefixSum[i] - lower;
            while (windowR <= M && prefixSum[windowR] <= max) {
                windowR++;
            }
            while (windowL <= M && prefixSum[windowL] < min) {
                windowL++;
            }
            count += windowR - windowL;
        }

        int l = L;
        int r = M + 1;
        while (l <= M && r <= R) {
            if (prefixSum[l] <= prefixSum[r]) {
                help[index++] = prefixSum[l++];
            } else {
                help[index++] = prefixSum[r++];
            }
        }
        //左边没有放完
        while (l <= M) {
            help[index++] = prefixSum[l++];
        }
        //右边没有放完
        while (r <= R) {
            help[index++] = prefixSum[r++];
        }
        //将排序好的数组返回原数组
        for (int i = 0; i < help.length; i++) {
            prefixSum[L + i] = help[i];
        }
        return count;
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
            if (countRangeSum3(arrOriginal,0,100) != countRangeSum2(arrCopyOne,0,100)) {
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }
}
