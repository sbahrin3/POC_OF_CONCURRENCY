package demo.test;

import java.util.List;

import demo.entity.User;
import lebah.db.entity.Persistence;

public class TestFetchDataOldWay {
	
	static long startTime, endTime, duration;	

	public static void main(String...strings) throws Exception {
		Persistence db = Persistence.db();

		logBegin("Fetching database records the normal way.");
		
		String query = "select u from User u order by u.fullName";
		List<User> users = db.list(query);
		
		logEnd(users);
		
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
