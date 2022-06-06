package test;

import javafx.beans.binding.When;

import java.util.UUID;

/**
 * @author duwei
 * @version 1.0.0
 * @create 2022-06-04 20:24
 * @description TODO
 */
public class Print {
    public static void main(String[] args) {
        Service service = new Service();
        new Thread(() ->{
            while (true){
                service.printA();
            }
        }).start();
        new Thread(() ->{
            while (true){
                service.printB();
            }
        }).start();
        new Thread(() ->{
            while (true){
                service.printC();
            }
        }).start();

    }
}

class Service {
    private int flag = 0;

    public void printA() {
        synchronized (this) {
            while (flag != 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


                System.out.println("AAAAAAAAAAAAAAAAA");

            flag++;
            this.notifyAll();
        }
    }

    public void printB() {
        synchronized (this) {
            while (flag != 1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


                System.out.println("BBBBBBBBBBBBBBB");

            flag++;
            this.notifyAll();
        }
    }

    public void printC() {
        synchronized (this) {
            while (flag != 2) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


                System.out.println("CCCCCCCCCCCCCC");

            flag = 0;
            this.notifyAll();
        }
    }

}
