package class13;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-07-03 16:56
 * @description 字符串字典序最小排序
 */
public class Code05_LowestLexicography {
    /**
     * 贪心策略进行排序然后拼接
     * @param arr
     * @return
     */
    public static String lowestLexicography(String[] arr){
        if (arr == null || arr.length == 0){
            return "";
        }
        Arrays.sort(arr,(a,b) -> (a + b).compareTo(b + a));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
        }
        return builder.toString();
    }

    /**
     * 暴力枚举所有可能拼接
     * @param arr
     * @return
     */
    public static String lowestLexicography2(String[] arr){
        if (arr == null || arr.length == 0){
            return "";
        }
        TreeSet<String> stringTreeSet = new TreeSet<>();
        process(arr,0,stringTreeSet);
        return stringTreeSet.first();
    }

    public static void process(String[] arr,int index,TreeSet<String> pre){
        if (index == arr.length){
            pre.add(String.join("",arr));
            return ;
        }
        for (int i = index; i < arr.length; i++) {
            swap(arr,index,i);
            process(arr,index + 1,pre);
            swap(arr,index,i);
        }
    }

    private static void swap(String[] arr,int i,int j){
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static String[] generateRandomStringArray(int maxArrayLength,int maxStringLength){
        int len = ((int) (Math.random() *  maxArrayLength) + 1);
        String[] arr = new String[len];
        for (int i = 0; i < len; i++) {
            arr[i] = generateRandomString(maxStringLength);
        }
        return arr;
    }

    private static String generateRandomString(int maxLen){
        int len = ((int) (Math.random() *  maxLen) + 1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(generateRandomChar());
        }
        return builder.toString();
    }

    private static char generateRandomChar(){
        return (char)((int)(Math.random() * 26) + 'a');
    }

    public static void main(String[] args) {
       int times = 100;
       int maxArrayLength = 10;
       int maxStringLength  = 5;
        System.out.println("start");
        for (int i = 0; i < times; i++) {
            String[] array = generateRandomStringArray(maxArrayLength, maxStringLength);
            String ans1 = lowestLexicography(array);
            String ans2 = lowestLexicography2(array);
            if (!ans1.equals(ans2)){
                System.out.println("出错了...");
                System.out.println(ans1 );
                System.out.println(ans2);
                System.out.println(Arrays.toString(array));
                System.exit(1);
            }
        }
        System.out.println("finish");
    }

}
