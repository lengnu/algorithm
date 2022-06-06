package test;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 23:30
 * @description TODO
 */
//6两汽车停在三两停车位
public class SemaPhoreTest {
    public static void main(String[] args) {
        //许可证个数
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println();
                    System.out.println(Thread.currentThread().getName() + "停在了车位上");
                    TimeUnit.SECONDS.sleep(new Random(5).nextInt());
                    System.out.println(Thread.currentThread().getName() + "------离开了车位上");
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }

            },String.valueOf(i)).start();

        }
    }
}
