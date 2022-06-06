package class02;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:44
 * @description TODO
 */
public class Code03_KM {
    /**
     * 找出数组种出现k次的那个元素,采用int[]32位数组实现，空间复杂度O(1)
     * @param arr
     * @param k
     * @param m
     * @return
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] counts = new int[32];

        //统计所有数字对应bit位的次数
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                counts[i] += (num >> i) & 1;
            }
        }

        //遍历数组找到出现k次的元素
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if ((counts[i] % m) != 0) {
                result |= (1 << i);
            }
        }

        return result;
    }

    /**
     * 采用HashMap找到出现k次的数
     * @param arr
     * @param k
     * @param m
     * @return
     */
    public static int onlyKTimesByHashMap(int[] arr,int k,int m){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr){
            if (!map.containsKey(num)){
                map.put(num,0);
            }
            map.put(num,map.get(num) + 1);
        }

        for (int num : map.keySet()){
            if (map.get(num) == k){
                return num;
            }
        }
        return 0;

    }

    /**
     * 返回满足要求的数组
     *
     * @param maxKinds 一共多少种数的范围
     * @param range    数字的范围
     * @param k        出现k次
     * @param m        出现m次
     * @return
     */
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        //1.出现k次的数字
        int kTimesNum = randomNumber(range);

        //2.一共多少种数,至少需要两种
        int numKinds = (int) ((Math.random() * maxKinds) + 2);

        //3.数组的长度
        int[] arr = new int[k + m * (numKinds - 1)];

        //4.填充出现k次的数
        int index = 0;
        for (; index < k; index++) {
            arr[index] = kTimesNum;
        }

        //5.填充剩余的数字
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(kTimesNum);

        while (numKinds != 0) {
            int curNum = 0;
            //保证随机的数字没有出现过
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            //剩余待填的种类的数减1
            numKinds--;

            //填充剩余的数字
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        return arr;

    }

    /**
     * 返回[-range,range]范围内的随机整数
     *
     * @param range
     * @return
     */
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void main(String[] args) {
        int kinds = 50;
        int range = 300;
        int testTimes = 10000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * max) + 1;  //1 ~ 9
            int b = (int) (Math.random() * max) + 1;  //1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds,range,k,m);
            int resultOne = onlyKTimes(arr, k, m);
            int resultTwo = onlyKTimesByHashMap(arr, k, m);
            if (resultOne != resultTwo){
                System.out.println("出错了");
                return;
            }
        }
        System.out.println("测试结束");
    }
}
