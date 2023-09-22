package my.cm;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {

	public static void main(String... args) {

		long startTime = System.currentTimeMillis();
		List<Student> students = StudentRecordService.getStudents();

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
			FinanceRecordService.getStatuses(students);
		}, executorService);

		CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
			LibraryRecordService.getStatuses(students);
		}, executorService);

		CompletableFuture<Void> allOf = CompletableFuture.allOf(task1, task2);

		try {
			allOf.get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		executorService.shutdown();

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		System.out.println("Execution time: " + executionTime + " milliseconds");

		//students.forEach(System.out::println);
	}

}
