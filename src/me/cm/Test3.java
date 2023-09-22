package my.cm;

import java.util.List;

public class Test3 {
	
	public static void main(String... args) {
		
		long startTime = System.currentTimeMillis();
		
		List<Student> students = StudentRecordService.getStudents();
		
		AsyncTasks asyncTasks = new AsyncTasks();
		
		asyncTasks.addTask( () -> { FinanceRecordService.getStatuses(students); } );
		asyncTasks.addTask( () -> { LibraryRecordService.getStatuses(students); } );
		
		asyncTasks.execute();
		
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		System.out.println("Execution time: " + executionTime + " milliseconds");
		
		
	}

}
