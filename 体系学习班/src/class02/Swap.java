package class02;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-05-22 20:23
 * @description 不使用额外变量交换两个数
 */
public class Swap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.format("a = %d,b = %d",a,b);
    }
}
