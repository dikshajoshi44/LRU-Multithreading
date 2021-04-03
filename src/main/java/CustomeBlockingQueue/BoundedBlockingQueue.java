package CustomeBlockingQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Producer extends Thread{

    private final CustomBlockingQueue blockingQueue;

    public Producer(String name, CustomBlockingQueue CustomBlockingQueue){
        this.setName(name);
        this.blockingQueue = CustomBlockingQueue;
    }

    public void run(){

            for(int i = 0; i <= 10; i++){
                try {
                    this.blockingQueue.put(i, this.getName());
                    Thread.sleep(100);
                }catch(Exception ex){ ex.printStackTrace();}

            }
    }

}

class Consumer extends Thread{

    private final CustomBlockingQueue blockingQueue;

    public Consumer(String name, CustomBlockingQueue CustomBlockingQueue){
        this.setName(name);
        this.blockingQueue = CustomBlockingQueue;
    }

    public void run(){

        while(true){
            try{
                this.blockingQueue.take(this.getName());
//                Thread.sleep(100);
            }catch(Exception ex){ ex.printStackTrace();}
        }
    }

}

class CustomBlockingQueue{

    private final Integer capacity;
    private final Lock lock;
    private final Condition conditionProducer;
    private final Condition conditionConsumer;
    private final Integer[] queueArray;

    private int count;
    private int putIndex = 0, takeIndex = 0;

    public CustomBlockingQueue(Integer capacity){
        this.lock = new ReentrantLock();
        this.capacity = capacity;
        this.conditionProducer = lock.newCondition();
        this.conditionConsumer = lock.newCondition();
        this.queueArray = new Integer[capacity];
    }

    public void put(Integer item, String threadName) throws InterruptedException{

        lock.lock();
        try {

            while (count == capacity) conditionProducer.await();

            queueArray[putIndex] = item;
            System.out.println("Producing - " + threadName + " " + item);
            count++;
            putIndex++;

            if (putIndex == queueArray.length) {
                putIndex = 0;
            }

            conditionConsumer.signal();

        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Integer take(String threadName) throws InterruptedException{

        lock.lock();
        try{

            while(count == 0) conditionConsumer.await();

            Integer item = queueArray[takeIndex];
            takeIndex++;
            count--;
            System.out.println("Consuming - " + threadName + " " + item);

            if(takeIndex == queueArray.length){
                takeIndex = 0;
            }

            conditionConsumer.signal();
            return item;

        } finally {
            lock.unlock();
        }
    }
}

class BlockingQueueDemo{

    public static void main(String agrs[]) throws InterruptedException {

        CustomBlockingQueue blockingQueue = new CustomBlockingQueue(10);

        Consumer consumerThread3 = new Consumer("consume3r", blockingQueue);
        Producer producerThread1 = new Producer("producer1", blockingQueue);
        Consumer consumerThread1 = new Consumer("consumer1", blockingQueue);
        Consumer consumerThread2 = new Consumer("consume2r", blockingQueue);
        Consumer consumerThread4 = new Consumer("consume4r", blockingQueue);

        producerThread1.start();
        consumerThread1.start();
        consumerThread2.start();
//        consumerThread3.start();
        consumerThread4.start();

        producerThread1.join();
        consumerThread1.join();
        consumerThread2.join();
        consumerThread4.join();


    }

}
