package com.multithreading;


class Producer extends Thread{
    public void run(){
        System.out.println("hello my name is ****" + Thread.currentThread().getName());
    }

}
public class TestThread {
    public static void main(String[] args){

        Thread thread1 = new Producer();
        thread1.start();
        System.out.println("hello my name is 000000" + Thread.currentThread().getName());

    }
}
