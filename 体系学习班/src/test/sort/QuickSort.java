package test.sort;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-05 22:46
 * @description TODO
 */
public class QuickSort {
    public static void sort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        quickSort(arr,0,arr.length - 1);
    }

    public static void quickSort(int[] arr,int L,int R){
        if (L >= R){
            return;
        }

        int[] partition = partition(arr, L, R);
        quickSort(arr,L,partition[0] - 1);
        quickSort(arr,partition[1] + 1,R);

    }

    public static int[] partition(int[] arr,int L,int R){
        if (L > R){
            return new int[]{-1,-1};
        }
        if (L == R){
            return new int[]{L,R};
        }
        swap(arr,R,(L + (int) (Math.random() * (R - L + 1))));
        int less = L - 1;
        int more = R;
        int index = L;
        while (index < more){
            if (arr[index] < arr[R]){
                swap(arr,index++,++less);
            }else if (arr[index] > arr[R]){
                swap(arr,index,--more);
            }else {
                index++;
            }
        }
        swap(arr,R,more);
        return new int[]{less + 1,more};
    }

    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,1,-4,55,-1342,43,34};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
