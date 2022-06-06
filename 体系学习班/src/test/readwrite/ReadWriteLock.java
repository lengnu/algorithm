package test.readwrite;

import javax.jws.Oneway;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-02 23:52
 * @description TODO
 */
public class ReadWriteLock {
    public static void main(String[] args) {
        MyCache cache = new MyCache();
        for (int i = 0; i <5; i++) {
            final int num = i;
            new Thread(() -> {
                cache.put(String.valueOf(num),num);
            },"写线程" + i).start();
        }

        for (int i = 0; i <5; i++) {
            int finalI = i;
            new Thread(() -> cache.get(finalI + ""),"读线程" + String.valueOf(i)).start();
        }

    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    //放数据
    public void put(String key, Object value) {
        //加上写锁
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "正在进行写操作" + key);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "已经写完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }

    }

    //取数据
    public Object get(String key) {
        //读锁
        lock.readLock().lock();
        Object result = null;
        System.out.println(Thread.currentThread().getName() + "正在进行读取操作" + key);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "已经取完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
        return result;

    }
}