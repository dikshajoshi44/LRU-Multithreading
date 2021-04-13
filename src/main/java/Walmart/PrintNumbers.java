package Walmart;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Even extends Thread{
    PrintSequence printSeq;

    public Even(PrintSequence printSeq){
        this.printSeq = printSeq;
    }

    public void run(){

        try {
            printSeq.printEven();
        }catch(Exception ex){
            System.out.println(ex.fillInStackTrace());
        }

    }
}
class Odd extends Thread{
    PrintSequence printSeq;

    public Odd(PrintSequence printSeq){
        this.printSeq = printSeq;
    }
    public void run(){

        try {
            printSeq.printOdd();
        }catch(Exception ex){
            System.out.println(ex.fillInStackTrace());
        }

    }
}

class Zero extends Thread{
    PrintSequence printSeq;

    public Zero(PrintSequence printSeq){
        this.printSeq = printSeq;
    }
    public void run(){

        try {
            printSeq.printZero();
        }catch(Exception ex){
            System.out.println(ex.fillInStackTrace());
        }

    }
}

class PrintSequence{

    private int number = 1;
    private int numberOfThreads;
    private int totalNumbersInSequence;
    private boolean zeroIsPrinted = false;
    private boolean evenIsPrinted = false;
    private boolean oddIsPrinted = false;
    private Lock lock = new ReentrantLock();
    private Condition oddCondition = lock.newCondition();
    private Condition evenCondition = lock.newCondition();
    private Condition zeroCondition = lock.newCondition();
//    Condition conditionOdd = lock.newCondition();

    public PrintSequence(int numberOfThreads, int totalNumbersInSequence){
        this.numberOfThreads = numberOfThreads;
        this.totalNumbersInSequence = totalNumbersInSequence;
    }

    public void printOdd() throws InterruptedException{


        while(number < 20){
            lock.lock();
            try {
                while(!zeroIsPrinted || isOdd(number)) {
                    oddCondition.await();
                }

                System.out.println("thread name and number is " + Thread.currentThread().getId() + " " + number);
                number++;
                oddIsPrinted = true;
                zeroIsPrinted = false;
                evenCondition.signalAll();
                zeroCondition.signalAll();

            }catch(Exception ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }
    }

    public void printEven(){

        while(number < 20){
            lock.lock();
            try {
                while(!zeroIsPrinted || isEven(number)) {
                    evenCondition.await();
                }

                System.out.println("thread name and number is " + Thread.currentThread().getId() + " " + number);
                number++;
                evenIsPrinted = true;
                zeroIsPrinted = false;
                oddCondition.signal();
                zeroCondition.signal();

            }catch(Exception ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }
    }

    public void printZero(){


        while(number < 20){
            lock.lock();
            try {
                while(zeroIsPrinted) {
                    zeroCondition.await();
                }

                System.out.println("thread name and number is " + Thread.currentThread().getId() + " " + 0);
                zeroIsPrinted = true;
                evenCondition.signal();
                oddCondition.signal();

            }catch(Exception ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }
    }

    public boolean isEven(int element){
        return (element % 2 == 0);
    }

    public boolean isOdd(int element){
        return (element % 2 != 0);
    }



//    public void printNumbers(int result){
//        synchronized (PrintSequence.class){
//
//            if(result == 0){
//                try {
//                    wait();
//                }catch(Exception ex){ex.printStackTrace();}
//            }
//
//
//            while(this.number < this.totalNumbersInSequence){
//                while(this.number % this.numberOfThreads != result){
//                    try {
//                        wait();
//                    }catch(Exception ex){ex.printStackTrace();}
//                }
//
//                if(result == 1){
//                    System.out.println("thread name" + Thread.currentThread().getName() + " " + 0);
//                }
//                System.out.println("thread name" + Thread.currentThread().getName() + " " + this.number);
//                this.number++;
//
//                notifyAll();
//            }
//        }
//    }

//    int numberCount;
//    int limit;
//    Lock lock = new ReentrantLock();
//    Condition evenCondition = lock.newCondition();
//    Condition oddCondition = lock.newCondition();
//    Condition zeroCondition = lock.newCondition();
//
//
//    public PrintSequence(int numberCount){
//        this.numberCount = numberCount;
//        this.limit = 3;
//    }
//
//    public void printEven() {
//
//        while (numberCount <= 100)
//            lock.lock();
//            try {
//                while (limit == 0) {
//                    evenCondition.await();
//                    oddCondition.await();
//                }
//                while(limit == 1){
//                    oddCondition.await();
//                    zeroCondition.await();
//                }
//                while(limit == 2){
//                    evenCondition.await();
//                    zeroCondition.await();
//                }
//
//                if(limit == 0)
//                    System.out.println("numberCount variable " + 0);
//                else{
//                    System.out.println("numberCount variable " + numberCount);
//                }
//                numberCount++;
//                limit++;
//
//                if(limit == 1) evenCondition.signal();
//                if(limit == 2) oddCondition.signal();
//
//                if(limit == 3) limit = 0;
//
//            } catch (InterruptedException ex) {
//                System.out.println("execption");
//            } finally {
//                lock.unlock();
//            }
//    }
//
//    public void printOdd(){
//
//    }
//
//    public void printZero(){
//
//    }

}

public class PrintNumbers {

    public static void main(String[] args){

        PrintSequence printSeq = new PrintSequence(3, 20);

        Zero zeroThread = new Zero(printSeq);
        Even evenThread = new Even(printSeq);
        Odd oddThread = new Odd(printSeq);

        zeroThread.start();
        evenThread.start();
        oddThread.start();

    }

}
