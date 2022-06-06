package test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 11:44
 * @description TODO
 */
public class SaleTicks {
    public static void main(String[] args) {
        Ticks ticks = new Ticks();
        Thread thread1 = new Thread(() -> {
            while (true) {
                ticks.sale();
            }
        }
                , "售票员 1 ");

        Thread thread2 =  new Thread(() -> {
            while (true){
                ticks.sale();
            }
        }
                , "售票员 2 ");

        Thread thread3 =  new Thread(() -> {
            while (true){
                ticks.sale();
            }
        }
                , "售票员 3 ");
        thread1.start();
        thread2.start();;
        thread3.start();

    }
}

class Ticks {
    public int count = 20;
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (count > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了第 " + count-- +
                        " 张票，还剩余" + count + "张票!");
            }
        }finally {
            lock.unlock();
        }


    }
}
