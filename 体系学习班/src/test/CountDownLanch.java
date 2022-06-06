package test;

import java.util.concurrent.CountDownLatch;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 23:13
 * @description TODO
 */
public class CountDownLanch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "号同学离开教室了...");
                downLatch.countDown();
            },String.valueOf(i)).start();
        }

        downLatch.await();
        System.out.println(Thread.currentThread().getName() + "班长锁门走人了..");
    }
}
