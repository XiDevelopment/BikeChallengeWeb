package test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Route;
import model.Statistic;

public class StatisticTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Route> list = new ArrayList<Route>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			list.add( new Route(1000.0f,
					dateFormat.parse("2014/05/26 15:13:12"),
					dateFormat.parse("2014/05/26 15:18:12"), 9));
			list.add( new Route(2000.0f,
					dateFormat.parse("2014/05/25 15:13:12"),
					dateFormat.parse("2014/05/25 15:18:12"), 9));
			list.add( new Route(3000.0f,
					dateFormat.parse("2014/05/24 15:13:12"),
					dateFormat.parse("2014/05/24 15:18:12"), 9));
			list.add( new Route(4.0f,
					dateFormat.parse("2014/05/23 15:13:12"),
					dateFormat.parse("2014/05/23 15:18:12"), 9));
			list.add( new Route(5.0f,
					dateFormat.parse("2014/05/22 15:13:12"),
					dateFormat.parse("2014/05/22 15:18:12"), 9));
			list.add( new Route(6.0f,
					dateFormat.parse("2014/05/21 15:13:12"),
					dateFormat.parse("2014/05/21 15:18:12"), 9));
			list.add( new Route(7.0f,
					dateFormat.parse("2014/05/20 15:13:12"),
					dateFormat.parse("2014/05/20 15:18:12"), 9));
			list.add( new Route(8.0f,
					dateFormat.parse("2014/05/19 15:13:12"),
					dateFormat.parse("2014/05/19 15:18:13"), 9));
			list.add( new Route(9.0f,
					dateFormat.parse("2014/05/18 15:13:12"),
					dateFormat.parse("2014/05/18 15:18:12"), 9));
			list.add( new Route(10.0f,
					dateFormat.parse("2014/05/17 15:13:12"),
					dateFormat.parse("2014/05/17 15:18:12"), 9));
			list.add( new Route(100.0f,
					dateFormat.parse("2014/05/26 15:13:12"),
					dateFormat.parse("2014/05/26 15:18:12"), 9));
		} catch (Exception e) {
			System.out.println("ERROR");
		}
		
		Statistic test = new Statistic(list);
		
		System.out.println("totaldistance" + test.getTotalDistance());
		System.out.println("longstdis" + test.getLongestDistance());
		System.out.println(test.getAvgDistance());
		long millis = (long) test.getTotalTime();
		long milliss = (long) test.getAvgTime();
		System.out.println(
		String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
			));
			
		System.out.println(
				String.format("%d min, %d sec", 
					    TimeUnit.MILLISECONDS.toMinutes(milliss),
					    TimeUnit.MILLISECONDS.toSeconds(milliss) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliss))
				)	
		);
		System.out.println("emm" + test.getEmission());
		System.out.println("fuel" + test.getFuel());
		
		
		
		

	}
}
