package class02;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:31
 * @description 找出数组中出现了奇数次的元素（其余都偶数次）
 */
public class Code01_EvenTimesOddTimes {

    /**
     * 打印数组中出现次数为奇数次的元素，数组为null返回系统最大
     * @param arr
     * @return
     */
    public static int printOddTimesNum(int[] arr){
        if (arr == null || arr.length == 0){
            return Integer.MAX_VALUE;
        }

        int eor = 0;
        for (int num : arr){
            eor ^= num;
        }
        return eor;
    }

}
