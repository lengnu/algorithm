package test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-31 19:21
 * @description TODO
 */
public class test {

    static class Solution {
        public static int lengthOfLongestSubstring(String s) {
            if(s.length() < 2){
                return s.length();
            }
            char[] str = s.toCharArray();
            int N = str.length;
            int max = 1;
            HashMap<Character,Integer> map = new HashMap<>();
            map.put(str[0],0);
            for(int i = 1;i < N;i++){
                if(!map.containsKey(str[i])){
                    max = Math.max(max,i + 1);
                }else{
                    max = Math.max(max,i - map.get(str[i]));
                }
                map.put(str[i],i);
            }
            return max;

        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String str = "bdsadasdsadadddsadaewregfdoisfhsiadfuasfdfsafabbbba";
        System.out.println(Solution.lengthOfLongestSubstring(str));
        FutureTask<String> futureTask = new FutureTask<>(UUID.randomUUID()::toString);
        futureTask.get();
    }
}
