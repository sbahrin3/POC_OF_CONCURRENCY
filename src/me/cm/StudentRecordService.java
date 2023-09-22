package my.cm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StudentRecordService {	
	
	public static List<Student> getStudents() {
		
		List<Student> students = new ArrayList<Student>();
		IntStream.range(1, 100).forEach(i -> {
			students.add(new Student());
		});
		return students;
	}



}
