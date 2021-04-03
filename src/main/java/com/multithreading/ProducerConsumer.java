package com.multithreading;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


class SoupConsumer extends Thread{
    private BlockingQueue servingLine;

    public SoupConsumer(BlockingQueue servingLine){
        this.servingLine = servingLine;
    }

    public void run(){
        while(true){
            try{
                String bowl = (String)servingLine.take();
                if(bowl.equalsIgnoreCase("no soup for you!"))
                    break;
                System.out.println("ate bowl " + bowl);
                Thread.sleep(300); //time taken to eat the bowl

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
}

class SoupProducer extends Thread{
    private BlockingQueue servingLine;

    public SoupProducer(BlockingQueue servingLine){
        this.servingLine = servingLine;
    }

    public void run(){
        for(int i = 0;  i < 20; i++){
            try {
                servingLine.add("Bowl" + "#" + i);
                System.out.println("served bowl, remaining capacity" + servingLine.remainingCapacity());
                Thread.sleep(200); // time to serve a bowl
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        // have to add twice once for each to make them stop.
        servingLine.add("no soup for you!");
        servingLine.add("no soup for you!");
    }

}


public class ProducerConsumer {
    public static void main(String[] args){
        BlockingQueue servingLine = new ArrayBlockingQueue<String>(5);
        new SoupConsumer(servingLine).start();
        new SoupConsumer(servingLine).start();
        new SoupProducer(servingLine).start();
    }
}
