package test.sort;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.Arrays;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-05 22:32
 * @description TODO
 */
public class HeapSort {
    public static void sort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        heapSort(arr);
    }

    //大根堆
    public static void heapSort(int[] arr){
        int N = arr.length;
        for (int i = N - 1;i >= 0;i--){
            heapify(arr,i,N);
        }
        int heapSize = N;
        for (int i = N - 1;i > 0;i--){
            swap(arr,0,i);
            heapify(arr,0,--heapSize);
        }
    }


    public static void heapify(int[] arr,int index,int heapSize){
        int left = 2 * index + 1;
        while (left < heapSize){
            int biggest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            biggest = arr[biggest] > arr[index] ? biggest : index;
            if (biggest == index){
                break;
            }
            swap(arr,index,biggest);
            index = biggest;
            left = 2 * index + 1;
        }

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
