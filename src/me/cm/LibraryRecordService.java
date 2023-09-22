package my.cm;

import java.util.List;
import java.util.Random;

public class LibraryRecordService {
	
	public static void getStatuses(List<Student> students) {
		System.out.println("Library Record Service is RUNNING");
		Random random = new Random();
		students.stream().forEach(student -> {
			Util.sleep(10);
			student.libraryStatus = random.nextInt(5) + 1;
		});
		System.out.println("Library Record Service has COMPLETED");
	}

}
