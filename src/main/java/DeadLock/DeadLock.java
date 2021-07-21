package DeadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {

    Lock chopstickA;
    Lock chopstickB;
    int sushiCount = 200_000;

    public Philosopher(Lock chopstickA, Lock chopstickB) {
        this.chopstickA = chopstickA;
        this.chopstickB = chopstickB;
    }

    public void run(){

        while(sushiCount >=0 ){
            chopstickA.lock();
            chopstickB.lock();

            if(sushiCount > 0){
                sushiCount--;
                System.out.println(Thread.currentThread().getName()+" ........took " + sushiCount);
            }

            chopstickA.unlock();
            chopstickB.unlock();

        }
    }



}
public class DeadLock {
    public static void main(String[] args) {
        Lock chopstickA = new ReentrantLock();
        Lock chopstickB = new ReentrantLock();
        Lock chopstickC = new ReentrantLock();

        (new Philosopher(chopstickA, chopstickB)).start();
        (new Philosopher(chopstickB, chopstickC)).start();
        (new Philosopher(chopstickA, chopstickC)).start();
    }

}
