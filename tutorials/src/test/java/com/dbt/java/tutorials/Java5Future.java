package com.dbt.java.tutorials;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;

public class Java5Future {	
	@Rule
    public TestRulesSetter pr = new TestRulesSetter(System.out);
	
	@Test
	public void doCompletableFuture1() throws Exception, ExecutionException {
		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		completableFuture.complete("Future's Result");
		String result = completableFuture.get();
		assertThat(result).isEqualToIgnoringCase("Future's Result");
	}
	
	@Test
	public void doCompletableFuture2() throws Exception, ExecutionException {		
		// Run a task specified by a Runnable Object asynchronously.
		CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
	        // Simulate a long-running Job
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            throw new IllegalStateException(e);
	        }
	        System.out.println("I'll run in a separate thread than the main thread.");
		});

		// Block and wait for the future to complete
		Void void1 = future.get();		
		System.out.println("Void="+void1);	
	}
	
	@Test
	public void doCompletableFuture3() throws Exception, ExecutionException {		
		// Run a task specified by a Runnable Object asynchronously.
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->{
	        // Simulate a long-running Job
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            throw new IllegalStateException(e);
	        }
	        return "Result of the asynchronous computation";
		});

		// Block and get the result of the Future
		String result = future.get();
		System.out.println(result);		
	}
	
	@Test
	public void doCompletableWithExecutor() throws Exception, ExecutionException {		
		Executor executor = Executors.newFixedThreadPool(10);
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(1);
		    } catch (InterruptedException e) {
		        throw new IllegalStateException(e);
		    }
		    return "Result of the asynchronous with executor";
		}, executor);

		// Block and get the result of the Future
		String result = future.get();
		System.out.println(result);		
	}
	
	@Test
	public void doCompletableWithCallback() throws Exception, ExecutionException {		
		// Create a CompletableFuture
		CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
		   try {
		       TimeUnit.SECONDS.sleep(2);
		   } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		   }
		   return "Rajeev";
		});

		// Attach a callback to the Future using thenApply()
		CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
		   return "Hello " + name;
		});

		// Block and get the result of the future.
		System.out.println(greetingFuture.get()); // Hello Rajeev
	}	
	
	@Test
	public void doCompletableWithCallback2() throws Exception, ExecutionException {		
		// Create a CompletableFuture
		CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
		   try {
		       TimeUnit.SECONDS.sleep(2);
		   } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		   }
		   return "Rajeev";
		});

		// Attach a callback to the Future using thenApply()
		whatsYourNameFuture.thenAccept(name -> {
			// Block and get the result of the future.
			System.out.println("Hello " + name);
		});
	}	
	
	private CompletableFuture<Integer> getUsersDetail(String userId){
		return CompletableFuture.supplyAsync(() -> {
			//UserService.getUserDetails(userId);
			return 10;
		});	
	}
	
	private CompletableFuture<Double> getCreditRating(Integer id){
		return CompletableFuture.supplyAsync(() -> {
			//CreditRatingService.getCreditRating(id);
			return id*10.0;
		});	
	}

	@Test
	public void doCompletableWithCombined() throws Exception, ExecutionException {
		CompletableFuture<CompletableFuture<Double>> result  = getUsersDetail("Hello").thenApply(id -> getCreditRating(id));
		assertThat(result.get().get()).isEqualTo(100.0);
	}
	

	@Test
	public void doCompletableWithCombined1() throws Exception, ExecutionException {
		CompletableFuture<Double> result  = getUsersDetail("Hello").thenCompose(id -> getCreditRating(id));
		assertThat(result.get()).isEqualTo(100.0);
	}
	
	
	@Test
	public void doCompletableWithCombined2() throws Exception, ExecutionException {
		System.out.println("Retrieving weight.");
		CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(1);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    return 65.0;
		});

		System.out.println("Retrieving height.");
		CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(1);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    return 177.8;
		});

		System.out.println("Calculating BMI.");
		CompletableFuture<Double> combinedFuture = weightInKgFuture
		        .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
		    Double heightInMeter = heightInCm/100;
		    return weightInKg/(heightInMeter*heightInMeter);
		});

		System.out.println("Your BMI is - " + combinedFuture.get());
	}
	
	private CompletableFuture<String> downloadWebPage(String pageLink) {
		return CompletableFuture.supplyAsync(() -> {
			// Code to download and return the web page's content
			return "web content of " + pageLink;
		});
	} 
	
	@Test
	public void doCompletableWithCombineAll() throws Exception, ExecutionException {
		List<String> webPageLinks = Arrays.asList("1", "2"); // A list of 100 web page links

		// Download contents of all the web pages asynchronously
		List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
		        .map(webPageLink -> downloadWebPage(webPageLink))
		        .collect(Collectors.toList());

		// Create a combined Future using allOf()
		//Returns a new CompletableFuture that is completed when all of the given CompletableFutures complete
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
		        pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
		);
		
		// When all the Futures are completed, call `future.join()` to get their results and collect the results in a list
		// The join() method is similar to get(). The only difference is that it throws an unchecked exception 
		// if the underlying CompletableFuture completes exceptionally.
		CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
		   return pageContentFutures.stream()
		           .map(pageContentFuture -> pageContentFuture.join())
		           .collect(Collectors.toList());
		});
	
		assertThat(allPageContentsFuture.get()).hasSize(webPageLinks.size());
	}
	
	@Test
	public void doCompletableWithErrors() throws Exception, ExecutionException {
		CompletableFuture<Void> resultFuture = CompletableFuture.supplyAsync(() -> {
			// Code which might throw an exception
			return "Some result";
		}).thenApply(result -> {
			return "processed result";
		}).thenApply(result -> {
			return "result after further processing";
		}).thenAccept(result -> {
			//// do something with the final result
			//return result;
		});
		
		resultFuture.get();
			
		Integer age = -1;

		CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
		    if(age < 0) {
		        throw new IllegalArgumentException("Age can not be negative");
		    }
		    if(age > 18) {
		        return "Adult";
		    } else {
		        return "Child";
		    }
		}).exceptionally(ex -> {
		    System.out.println("Oops! We have an exception - " + ex.getMessage());
		    return "Unknown!";
		});

		System.out.println("Maturity : " + maturityFuture.get()); 
		
	}
	
	@Test
	public void doCompletableWithHandler() throws Exception, ExecutionException {
		Integer age = -1;

		CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
		    if(age < 0) {
		        throw new IllegalArgumentException("Age can not be negative");
		    }
		    if(age > 18) {
		        return "Adult";
		    } else {
		        return "Child";
		    }
		}).handle((res, ex) -> {
		    if(ex != null) {
		        System.out.println("Oops! We have an exception - " + ex.getMessage());
		        return "Unknown!";
		    }
		    return res;
		});

		System.out.println("Maturity : " + maturityFuture.get());
	}
	
}
