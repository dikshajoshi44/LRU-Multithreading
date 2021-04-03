package com.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task extends Thread{
    public void run(){
        System.out.println("Hello my name is thread " + Thread.currentThread().getName());
    }
}

public class ExecutorServiceExample {

    public static void main(String[] args){
        ExecutorService pool = Executors.newFixedThreadPool(9);

        ExecutorService service = Executors.newCachedThreadPool();

        //i want 100 tasks to be done by 9 threads

        int numOfProcessors =  Runtime.getRuntime().availableProcessors();
        System.out.println("Number of processors are " + numOfProcessors);

        for(int i = 0; i < 100; i++){
            pool.submit(new Task());
        }
    }

}
