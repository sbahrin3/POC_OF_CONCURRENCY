package my.cm;

import java.util.List;

public class Test1 {
	
	public static void main(String... args) {
		
		long startTime = System.currentTimeMillis();
		
		List<Student> students = StudentRecordService.getStudents();
		
		FinanceRecordService.getStatuses(students);
		
		LibraryRecordService.getStatuses(students);

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		System.out.println("Execution time: " + executionTime + " milliseconds");
		
		
		//students.forEach(System.out::println);
	}

}
