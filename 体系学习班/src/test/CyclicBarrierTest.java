package test;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 23:21
 * @description TODO
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> System.out.println("集起七颗龙珠，开始召唤神龙.."));
        for (int i = 0; i < 7; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + "星龙珠收集到了");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}
