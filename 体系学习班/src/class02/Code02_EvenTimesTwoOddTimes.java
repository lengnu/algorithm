package class02;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:54
 * @description 一个数组中两个数出现了奇数次，其余出现了偶数次，找到这两个数
 */
public class Code02_EvenTimesTwoOddTimes {
    public static int[] printTwoOddTimesNum(int[] arr){
        int eor = 0;
        for (int num : arr){
            eor ^= num;
        }

        //找到分割不同数最右边的1
        int right = eor & (~eor + 1);

        int one = 0;
        for (int num : arr){
            if ((num & right) != 0){
                one ^= num;
            }
        }

        int two = one ^ eor;
        return new int[]{one,two};
    }

}
