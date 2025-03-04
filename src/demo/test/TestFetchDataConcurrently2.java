package demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import demo.entity.User;
import lebah.db.entity.Persistence;

public class TestFetchDataConcurrently2 {

	public static void main(String[] args) throws Exception {
		Persistence db = Persistence.db();

		// Define concurrency settings
		int totalRecords = 10_000;
		int batchSize = 2_000;
		int numThreads = (int) Math.ceil((double) totalRecords / batchSize);

		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			System.out.println("Fetching with concurrency method.");
			System.out.println("Begin benchmark.");
			long startTime = System.nanoTime();

			String query = "SELECT u FROM User u ORDER BY u.fullName";

			List<CompletableFuture<List<User>>> futures = new ArrayList<>();

			for (int page = 1; page <= numThreads; page++) {
				int currentPage = page;
				futures.add(listData(executor, db, query, currentPage, batchSize));
			}

			List<User> allUsers = futures.stream()
					.map(CompletableFuture::join)
					.flatMap(List::stream)
					.toList();


			long endTime = System.nanoTime();
			System.out.println("End benchmark.");

			double elapsedTimeMs = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds

			System.out.println("Total records = " + allUsers.size());
			System.out.println("Elapsed Time = " + elapsedTimeMs + " ms");
		}
	}

	// Make sure listData() method uses generics properly
	public static CompletableFuture<List<User>> listData(ExecutorService executor, Persistence db, String query, int page, int size) {
		return CompletableFuture.supplyAsync(() -> db.listDataByPage(query, page, size), executor);
	}

}
