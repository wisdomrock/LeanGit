package com.dbt.java.tutorials;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;

public class JavaThread {
	@Rule
    public TestRulesSetter pr = new TestRulesSetter(System.out);
	
	//@Test
	public void runThread() throws Exception {
		Thread a1 = new Thread() {
			@Override
			public void run() {
				System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass());
			}	
		};
		a1.start();
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Method="+this.getClass().getEnclosingMethod());
			}	
		};
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(r1);
		
		System.out.println("End of Main");
		
		Future<String> futureCall = executorService.submit(()->"RESULT");
		
		//Waits if necessary for the computation to complete, and then retrieves its result.
		System.out.println("Future result: " + futureCall.get());
		
		executorService.shutdown();		
	}
	
	//@Test
	public void jointThreads() throws Exception {
		Thread t1 = new Thread(){
			@Override
			public void run() {
		        try {
		            TimeUnit.SECONDS.sleep(5);
		        } catch (InterruptedException e) {
		            throw new IllegalStateException(e);
		        }
		        System.out.println("I'll run in a separatly");
			}
		};
		t1.start();
		t1.join();
		System.out.println("Exiting from main thread");
	}
	
	@Test
	public void waitThreads() throws Exception {
		Thread t1 = new Thread(){
			@Override
			public void run() {
				System.out.println("I started");
		        try {
		            TimeUnit.SECONDS.sleep(5);
		        } catch (InterruptedException e) {
		            throw new IllegalStateException(e);
		        }
		        System.out.println("I'll run in a separate thread than the main thread.");
			}
		};
		t1.start();
		synchronized (t1) {
			System.out.println("I am waitting");
			t1.wait();
			System.out.println("Running main thread");
		}
		TimeUnit.SECONDS.sleep(10);
		System.out.println("Exiting from main thread");
	}
}
