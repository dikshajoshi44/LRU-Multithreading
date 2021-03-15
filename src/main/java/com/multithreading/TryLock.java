package com.multithreading;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Shopper2 extends Thread{
    private int itemsToAdd = 0;
    private static int itemsOnNotePad = 0; //items on shared notepad
    private static Lock pencil = new ReentrantLock();

    public Shopper2(String name){
        this.setName(name);
    }

    public void run(){
        while(itemsOnNotePad <= 20){
            if(itemsToAdd > 0 && pencil.tryLock()){

                itemsOnNotePad+= itemsToAdd;
                System.out.println(this.getName() + " added " + itemsToAdd + "items to notepad");
                itemsToAdd = 0;
                pencil.unlock();

                try{
                    Thread.sleep(100);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }else{
                try {
                    Thread.sleep(100);
                    itemsToAdd++;
                    System.out.println(this.getName() + " found something to buy");
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
public class TryLock {

    public static void main(String[] args) throws InterruptedException {

        Thread barron = new Shopper2("Barron");
        Thread olivia = new Shopper2("Olivia");
        long start = System.currentTimeMillis();

        barron.start();
        olivia.start();

        barron.join();
        olivia.join();

        long finish = System.currentTimeMillis();

        System.out.println("Elapsed time" + (float) (finish-start)/1000 + "second");
    }
}
