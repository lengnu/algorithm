package class10;

import jdk.nashorn.internal.ir.IfNode;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-27 15:54
 * @description 折纸问题
 */
public class Code08_PaperFolding {
    /**
     * 按顺序打印折痕，共折n次
     * @param n
     */
    public static void print(int n){
        if (n == 0){
            return;
        }
        printProcess(n,1,true);
    }

    public static void printProcess(int n,int cur,boolean isLeft){
        if (cur > n){
            return;
        }
        //打印左树
        printProcess(n,cur + 1,true);
        System.out.print((isLeft ? "凹" : "凸") + "\t");
        //打印右树
        printProcess(n,cur + 1,false);
    }

    public static void main(String[] args) {
        print(3);
    }
}
