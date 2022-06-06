package class05;

import java.util.Stack;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-27 22:51
 * @description TODO
 */
public class Code03_QuickNonRecursive {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
}
