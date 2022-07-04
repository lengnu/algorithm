package class14;

import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * @BelongsProject: Algorithm-System
 * @BelongsPackage: class14
 * @Author: duwei
 * @Date: 2022/7/4 18:30
 * @Description: 分割金条的最小代价
 */
public class Code02_LessMoneySplitGold {
    /**
     * 哈夫曼编码求分割金条的最小代价
     *
     * @param costs
     * @return
     */
    public static int minCost(int[] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int cost : costs) {
            heap.add(cost);
        }
        int totalCost = 0;
        while (heap.size() > 1) {
            int cur = heap.poll() + heap.poll();
            totalCost += cur;
            heap.add(cur);
        }
        return totalCost;
    }


    /**
     * 暴力递归求分割金条最小代价，两两合并，枚举所有可能
     *
     * @param costs
     * @return
     */
    public static int minCost1(int[] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        return process(costs, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }


    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int size = (int) (Math.random() * ( maxSize)) + 1;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * (1 + maxValue));
        }
        return arr;
    }

    public static void main(String[] args) {
        int times = 10;
        int maxSize = 5;
        int maxValue = 500;
        System.out.println("start...");
        for (int i = 0; i < times; i++) {
            int[] costs = generateRandomArray(maxSize, maxValue);
            if (minCost(costs) != minCost1(costs)) {
                System.out.println("出错了");
                System.exit(1);
            }
        }
        System.out.println("finish...");
    }

}
