package class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-29 11:49
 * @description 最大线段重合问题
 */
public class Code01_CoverMax {
    /**
     * 暴力循环求解
     * @param lines
     * @return
     */
    public static int maxCover1(int[][] lines){
        if (lines == null || lines.length == 0){
            return 0;
        }

        int startMin = lines[0][0];
        int endMax = lines[0][1];
        int N = lines.length;
        for (int i = 1;i < N;i++){
            startMin = Math.min(startMin,lines[i][0]);
            endMax = Math.max(endMax,lines[i][1]);
        }

        int max = 0;
        for (double point = startMin + 0.5;point < endMax;point += 1){
            int count = 0;
            for (int i = 0;i < N;i++){
                if (lines[i][0] < point && lines[i][1] > point){
                    count++;
                }
            }
            max = Math.max(count,max);
        }
        return max;
    }

    /**
     * 采用堆求解
     * @param lines
     * @return
     */
    public static int maxCover2(int[][] lines){
        if (lines == null || lines.length == 0){
            return 0;
        }

        //按照开始位置排序
        Arrays.sort(lines, Comparator.comparingInt(o -> o[0]));
        //小根堆
        HeapGreater<Integer> heapGreater = new HeapGreater<>((o1, o2) -> o2 - o1);
        //PriorityQueue<Integer> heapGreater = new PriorityQueue<>();
        int max = 0;
        for (int i = 0;i < lines.length;i++){
            while (!heapGreater.isEmpty() && heapGreater.peek() <= lines[i][0]){
                heapGreater.poll();
            }
            heapGreater.add(lines[i][1]);
            max = Math.max(max,heapGreater.size());
        }
        return max;
    }

    /**
     * 生成N条L---R范围内的随机线段
     * @param N
     * @param L
     * @param R
     * @return
     */
    public static int[][] generateLines(int N,int L,int R){
        int[][] lines = new int[N][];
        for (int i = 0;i < N;i++){
            int[] line = new int[2];
            int start = generateRandomNumber(L,R);
            int end = generateRandomNumber(L,R);
            while (end <= start){
                start = generateRandomNumber(L,R);
                end = generateRandomNumber(L,R);
            }
            line[0] = start;
            line[1] = end;
            lines[i] = line;
        }
        return lines;
    }

    public static int generateRandomNumber(int lower,int upper){
        return (int) (Math.random() * (upper - lower + 1 ) + lower);
    }

    public static void main(String[] args) {
        int N = 1000;
        int L = 0;
        int R = 100;
        int times = 100;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[][] lines = generateLines(N,L,R);
            int count1 = maxCover1(lines);
            int count2 = maxCover2(lines);
            if (count1 != count2){
                System.out.println(count1 + "\t" + count2);
                System.out.println("出错了...");
                System.exit(1);
            }
        }
        System.out.println("测试结束");
    }

}
