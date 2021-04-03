package ExecutorServiceExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class VegetableSoup extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName() + " is chopping vegetables");
    }
}

public class ThreadPoolExample {

    public static void main(String args[]){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for(int  i = 0; i < 100 ; i++){
            pool.submit(new VegetableSoup());
        }

        pool.shutdown();

    }
}
