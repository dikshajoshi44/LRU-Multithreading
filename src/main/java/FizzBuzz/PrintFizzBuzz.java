package FizzBuzz;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bazinga extends Thread {
    private int number;
    FizzBuzz fizzBuzz;

    public Bazinga(int number, FizzBuzz fizzBuzz) {
        this.number = number;
        this.fizzBuzz = fizzBuzz;
    }

    public void run() {

        while(true) {
//            Random random = new Random(4);
//            System.out.println("...... " + random.nextInt(4));
            if (this.number == 1) {
                this.fizzBuzz.printFizz();
            } else if (this.number == 2) {
                this.fizzBuzz.printBuzz();
            } else if (this.number == 3) {
                this.fizzBuzz.printFizzBuzz();
            } else {
                this.fizzBuzz.printNumber();
            }
        }
    }
}

class FizzBuzz {

    private int currentNumber = 1;
    private int limit;
    Lock lock ;
    Condition fizzCondition;
    Condition numberCondition;
    Condition buzzCondition;
    Condition fizzBuzzCondition;

    public FizzBuzz(int number) {
        limit = number;
        lock = new ReentrantLock();
        fizzCondition = lock.newCondition();
        numberCondition = lock.newCondition();
        buzzCondition = lock.newCondition();
        fizzBuzzCondition = lock.newCondition();
    }

    public void printNumber() {

        while (currentNumber < limit) {
            lock.lock();

            try {
                while (divisibleby3(currentNumber) || divisibleby5(currentNumber)) {
                    numberCondition.await();
                }

                System.out.println("print the thread value " + currentNumber);

                currentNumber++;
                buzzCondition.signal();
                fizzCondition.signal();
                fizzBuzzCondition.signal();

            } catch(InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printFizz() {

        while (currentNumber < limit) {
            lock.lock();

            try {
                while (!divisibleby3(currentNumber) || divisibleby5(currentNumber)) {
                    fizzCondition.await();
                }

                System.out.println("print the thread value " + "fizz");

                currentNumber++;
                buzzCondition.signal();
                fizzBuzzCondition.signal();
                numberCondition.signal();

            } catch(InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printBuzz() {

        while (currentNumber < limit) {
            lock.lock();

            try {
                while (divisibleby3(currentNumber) || !divisibleby5(currentNumber)) {
                    buzzCondition.await();
                }

                System.out.println("print the thread value " + "buzz");

                currentNumber++;
                fizzCondition.signal();
                fizzBuzzCondition.signal();
                numberCondition.signal();

            } catch(InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printFizzBuzz() {
        while (currentNumber < limit) {
            lock.lock();

            try {
                while (!(divisibleby3(currentNumber) && divisibleby5(currentNumber))) {
                    fizzBuzzCondition.await();
                }

                System.out.println("print the thread value " + "fizzBuzz");

                currentNumber++;
                buzzCondition.signal();
                fizzCondition.signal();
                numberCondition.signal();

            } catch(InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean divisibleby3(int number){
        return (number % 3 == 0);
    }

    public boolean divisibleby5(int number){
        return (number % 5 == 0);
    }
}


public class PrintFizzBuzz {

    public static void main(String[] args) {


        FizzBuzz fizzBuzz = new FizzBuzz(15);

        ScheduledExecutorService service = Executors.newScheduledThreadPool(7);

        Bazinga thread1 = new Bazinga(1, fizzBuzz);
        thread1.setDaemon(true);

        service.schedule(thread1, 10, TimeUnit.MILLISECONDS);
//        service.scheduleAtFixedRate();
//        service.scheduleWithFixedDelay();

        service.execute(new Bazinga(2, fizzBuzz));
        service.execute(new Bazinga(3, fizzBuzz));
        service.execute(new Bazinga(4, fizzBuzz));

        service.shutdown();

//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

//        thread1.join();
//        thread2.join();
//        thread3.join();
//        thread4.join();


    }
}
