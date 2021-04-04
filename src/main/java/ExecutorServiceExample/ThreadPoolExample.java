package ExecutorServiceExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class VegetableSoup extends Thread{
    public void run(){
        try {
            // every 10 second it sleeps

//            Thread.sleep(10 * 60);
            System.out.println(Thread.currentThread().getName() + " " + System.currentTimeMillis() + " is chopping vegetables");
        }catch(Exception ex){ ex.printStackTrace();}

    }
}

public class ThreadPoolExample {

    public static void main(String args[]){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

        for(int  i = 0; i < 10 ; i++){
//            pool.submit(new VegetableSoup());
            pool.schedule(new VegetableSoup(), 10, TimeUnit.SECONDS);
        }

        pool.shutdown();
    }
}
