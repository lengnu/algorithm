package class14;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @BelongsProject: Algorithm-System
 * @BelongsPackage: class14
 * @Author: duwei
 * @Date: 2022/7/4 19:17
 * @Description: 返回最大收益
 */
public class Code03_IPO {

    public static class Program {
        int cost;
        int profit;

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    /**
     * 返回能做项目的最大收益
     *
     * @param costs   项目花费
     * @param profits 项目利润
     * @param k       能做项目数
     * @param m       初始资金
     * @return
     */
    public static int maxProfit(int[] costs, int[] profits, int k, int m) {
        if (costs == null || costs.length == 0 || profits == null || profits.length == 0 || k <= 0 || (costs.length != profits.length)) {
            return 0;
        }
        int N = costs.length;
        Program[] programs = new Program[N];
        for (int i = 0; i < N; i++) {
            programs[i] = new Program(costs[i], profits[i]);
        }
        PriorityQueue<Program> minCostQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        PriorityQueue<Program> maxProfitQueue = new PriorityQueue<>((o1, o2) -> o2.profit - o1.profit);
        int totalProfit = m;
        for (int i = 0; i < k; i++) {
            while (!minCostQueue.isEmpty() && minCostQueue.peek().cost <= totalProfit) {
                maxProfitQueue.add(minCostQueue.poll());
            }
            //如果该堆内为null，则表明一个也做不了或者全部做完了
            if (maxProfitQueue.isEmpty()) {
                return totalProfit;
            }
            totalProfit += maxProfitQueue.poll().profit;
        }
        return totalProfit;
    }

}
