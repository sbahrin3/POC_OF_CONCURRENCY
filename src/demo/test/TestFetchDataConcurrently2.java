package demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import demo.entity.User;
import lebah.db.entity.Persistence;

public class TestFetchDataConcurrently2 {
	
	static long startTime, endTime, duration;

	public static void main(String...args) throws Exception {

		Persistence db = Persistence.db();
		
		logBegin("Fetching database records with Parallel Programming.");
		
		String query = "select u from User u order by u.fullName";
		List<User> users = new ArrayList<>();
		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			List<CompletableFuture<List<User>>> futures = new ArrayList<>();
			int pageSize = 2000;
			for ( int page = 1; page < 6; page++ )
				futures.add(listData(executor, db, query, page, pageSize));
			
			users = futures.stream().map(CompletableFuture::join).flatMap(List::stream).toList();
			
		}
		
		logEnd(users);

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
