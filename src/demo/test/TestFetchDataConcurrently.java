package demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import demo.entity.User;
import lebah.db.entity.Persistence;

public class TestFetchDataConcurrently {
	
	static long startTime, endTime, duration;

	public static void main(String...args) throws Exception {
		


		Persistence db = Persistence.db();
		
		logBegin("Fetching database records with Parallel Programming.");
		
		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

			
			String query = "select u from User u order by u.fullName";

			CompletableFuture<List<User>> listFuture1 = listData(executor, db, query, 1, 2000);
			CompletableFuture<List<User>> listFuture2 = listData(executor, db, query, 2, 2000);
			CompletableFuture<List<User>> listFuture3 = listData(executor, db, query, 3, 2000);
			CompletableFuture<List<User>> listFuture4 = listData(executor, db, query, 4, 2000);
			CompletableFuture<List<User>> listFuture5 = listData(executor, db, query, 5, 2000);
			
			
			CompletableFuture<Void> allTasks = CompletableFuture.allOf(listFuture1, listFuture2, listFuture3, listFuture4, listFuture5);		
			allTasks.join();

			List<User> list1 = listFuture1.join();
			List<User> list2 = listFuture2.join();
			List<User> list3 = listFuture3.join();
			List<User> list4 = listFuture4.join();
			List<User> list5 = listFuture5.join();



			List<User> users = new ArrayList<>();
			users.addAll((List<User>) list1);
			users.addAll((List<User>) list2);
			users.addAll((List<User>) list3);
			users.addAll((List<User>) list4);
			users.addAll((List<User>) list5);
			
			logEnd(users);

		}

	}

	public static CompletableFuture<List<User>> listData(ExecutorService executor, Persistence db, String query, int pageNo, int max) {
		CompletableFuture<List<User>> listFuture = CompletableFuture.supplyAsync(() -> {
			return db.listDataByPage(query, pageNo, max);
		}, executor);
		return listFuture;
	}
	
	
	static void logBegin(String msg) {
		System.out.println(msg);
		System.out.println("Begin benchmark.");
		startTime = System.nanoTime();
	}
	
	static void logEnd(List<User> users) {
		long endTime = System.nanoTime();
		long duration = endTime - startTime; // Calculate elapsed time
		double elapsedTimeMs = duration / 1_000_000.0; // Convert to milliseconds
		System.out.println("End benchmark.");
		System.out.println("Total records = " + users.size());
		System.out.println("Elapsed Time = " + elapsedTimeMs);
	}

}
