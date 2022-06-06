package test;

import javafx.geometry.VPos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 20:10
 * @description TODO
 */
public class Transalate {
    public static void main(String[] args) {
        Box<String> box = new Box();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    box.add(String.valueOf(UUID.randomUUID().toString()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"生产者" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    box.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"消费者" + i).start();
        }
    }
}
class Box<T>{
    private List<T> box;
    private final static int LIMIT = 1;

    {
        box = new ArrayList<>();
    }
    public void add(T value) throws InterruptedException {
        synchronized (box){
            while (box.size() == LIMIT){
                try {
                    box.wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t开始生产...");
            box.add(value);
            Thread.sleep(300);
            System.out.println(Thread.currentThread().getName() + "\t生产完毕...");
            box.notifyAll();
        }
    }

    public void remove() throws InterruptedException {
        synchronized (box){
            while (box.size() == 0){
                try {
                    box.wait();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + "\t开始消费...");
            box.remove(box.size() - 1);
            Thread.sleep(300);
            box.notifyAll();
            System.out.println(Thread.currentThread().getName() + "\t消费完毕...");
        }
    }
}
