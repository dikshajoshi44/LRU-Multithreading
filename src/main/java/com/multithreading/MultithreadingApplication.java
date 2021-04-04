package com.multithreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication

//class ChefOlivia implements Runnable {
//	@Override
//	public void run(){
//		System.out.println("Olivia started & waiting for sausage to thaw...");
//		try{
//			Thread.sleep(3000);
//			System.out.println("Olivia is in waiting state");
//		}catch(InterruptedException e){
//			e.printStackTrace();
//		}
//
//		System.out.println("Olivia is done cutting sausage.");
//	}
//}



class Shopper extends Thread{

//	static AtomicInteger countGarlic = new AtomicInteger(0);
//	static Lock pencil = new ReentrantLock();

//	static int countGarlic;
//
//	private static synchronized void countGarlicMethod(){
//		countGarlic++;
//	}

	ReentrantLock lock = new ReentrantLock();
	static int garlicCount;
	static int onionCount;

	public void addGarlic(){
		System.out.println("reentered lock " + lock.getHoldCount());
		lock.lock();
		garlicCount++;
		lock.unlock();
	}

	public void addOnlinion(){
		lock.lock();
		onionCount++;
		addGarlic();
		lock.unlock();
	}

	public void run(){









//		for(int i = 0; i < 1000; i++){
//			addGarlic();
//			addOnlinion();
//		}

//		for(int i = 0; i < 10; i++){
//			//Critical section
//
//			countGarlicMethod();
//
////			countGarlic.incrementAndGet();
//
//
////			pencil.lock();
////			countGarlic++;
////			pencil.unlock();
////			try {
////				Thread.sleep(300);
////			}catch (InterruptedException e){
////				e.printStackTrace();
////			}
//
//		}

	}
}

public class MultithreadingApplication {

	public static void main(String[] args) throws InterruptedException{

		Thread barron = new Shopper();
		Thread olivia = new Shopper();

		barron.start();
		olivia.start();

		barron.join();
		olivia.join();

		System.out.println("We should buy : " + Shopper.garlicCount);
		System.out.println("We should buy : " + Shopper.onionCount);

//		Thread olivia = new Shopper();
//		Thread barren = new Shopper();
//
//		olivia.start();
//		barren.start();
//
//		olivia.join();
//		barren.join();
//
//		System.out.println("We should buy : " + Shopper.countGarlic);




//		System.out.println("Barron started & requesting Olivia's help.");
//
//		Thread olivia = new Thread(new ChefOlivia());
//		System.out.println("Olivia's stats: " + olivia.getState());
//
//		System.out.println("Barron tells Olivia to start.");
//		olivia.start();
//		System.out.println("Olivia's stats: " + olivia.getState());
//
//		System.out.println("Barron continues cooking soup.");
//		Thread.sleep(500);
//		System.out.println("Olivia's stats: " + olivia.getState());
//
//		System.out.println("Barron patiently waits for Olivia to finish and join...");
//		olivia.join();
//		System.out.println("Olivia's stats: " + olivia.getState());
//
//		System.out.println("Barron and Olivia are both done!");
	}

}
