package my.cm;

import java.util.List;
import java.util.Random;

public class FinanceRecordService {
	
	public static void getStatuses(List<Student> students) {
		System.out.println("Finance Record Service is RUNNING");

		Random random = new Random();
		students.stream().forEach(student -> {
			Util.sleep(10);
			student.financeStatus = random.nextInt(5) + 1;
		});
		
		System.out.println("Finance Record Service has COMPLETED");
	}

}
