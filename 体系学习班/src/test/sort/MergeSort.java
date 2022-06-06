package test.sort;

import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-05 22:41
 * @description TODO
 */
public class MergeSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr,0,arr.length - 1);
    }

    public static void mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = ((R - L) >> 1) + L;
        mergeSort(arr, L, mid);
        mergeSort(arr, mid + 1, R);
        merge(arr,L,mid,R);
    }

    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int pl = L;
        int pr = M + 1;
        int i  = 0;
        while (pl <= M && pr <= R){
            help[i++] = arr[pl] <= arr[pr] ? arr[pl++] : arr[pr++];
        }

        while (pl <= M){
            help[i++] = arr[pl++];
        }

        while (pr <= R){
            help[i++] = arr[pr++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
    }

    public static void swap(int[] arr, int i, int j) {
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

