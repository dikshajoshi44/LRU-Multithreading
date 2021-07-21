package PracticeEvenOdd;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// working code
// o1o2o3o4o5o6o7o8
class Zero extends Thread{

    PrintNumbers printNumbers;

    public Zero(PrintNumbers printNumbers){
        this.printNumbers = printNumbers;
    }
    public void run(){
        this.printNumbers.printZero();
    }
}

class Even extends Thread{

    PrintNumbers printNumbers;

    public Even(PrintNumbers printNumbers){
        this.printNumbers = printNumbers;
    }
    public void run(){
        this.printNumbers.printEven();
    }
}

class Odd extends Thread{

    PrintNumbers printNumbers;

    public Odd(PrintNumbers printNumbers){
        this.printNumbers = printNumbers;
    }
    public void run(){
        this.printNumbers.printOdd();
    }
}

class PrintNumbers {

    int number = 1;
    boolean zeroPrinted = false;
    boolean evenPrinted = false;
    boolean oddPrinted = false;
    Lock lock;
    Condition evenCondition;
    Condition oddCondition;
    Condition zeroCondition;

    public PrintNumbers(){
        lock = new ReentrantLock();
        zeroCondition = lock.newCondition();
        evenCondition = lock.newCondition();
        oddCondition = lock.newCondition();

    }

    public void printZero(){

        while(number < 20) {
            lock.lock();

            try {
                while(zeroPrinted){
                    zeroCondition.await();
                }

                System.out.println("print the number " + 0);
                zeroPrinted = true;
                evenCondition.signal();
                oddCondition.signal();

            }catch(InterruptedException ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }


    }

    public void printEven(){
        while(number < 20) {
            lock.lock();

            try {
                while(!zeroPrinted || isOdd(number)){
                    evenCondition.await();
                }

                System.out.println("print the number " + number);
                number++;
                zeroPrinted = false;
                zeroCondition.signal();


            }catch(InterruptedException ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }


    }

    public void printOdd(){

        while(number < 20) {
            lock.lock();

            try {
                while(!zeroPrinted || isEven(number)){
                    oddCondition.await();
                }

                System.out.println("print the number " + number);
                zeroPrinted = false;
                number++;
                zeroCondition.signal();

            }catch(InterruptedException ex){
                System.out.println(ex.fillInStackTrace());
            }finally {
                lock.unlock();
            }
        }


    }

    public boolean isEven(int number){
        return (number % 2 == 0);
    }

    public boolean isOdd(int number){
        return (number % 2 != 0);
    }
}

public class PrintEvenOddNumber {
    public static void main(String[] args) {

        PrintNumbers printNumbers = new PrintNumbers();

        Zero zeroThread = new Zero(printNumbers);
        Even evenThread = new Even(printNumbers);
        Odd oddThread = new Odd(printNumbers);

        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}
