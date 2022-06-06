package class06;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-28 21:14
 * @description TODO
 */
public class Code03_SortArrayDistanceLessK {

    /**
     * k < arr.length
     * @param arr
     * @param k
     */
    public static void sortArrayDistanceLessK(int[] arr,int k){
        if (k == 0){
            return;
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (;index < Math.min(arr.length - 1,k - 1);index++){
            heap.add(arr[index]);
        }

        int i = 0;
        for (;index < arr.length;index++,i++){
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }

        System.out.println(Arrays.toString(arr));

    }

}
