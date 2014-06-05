package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;

public class Statistic {
	 private float totalDistance;//
	 private float longestDistance;//
	 private float avgDistance;//
	 private List<Float> last7Days;
	 private float totalTime;//
	 private float avgTime;//
	 private float emission;//
	 private float fuel;//
	 
	 public Statistic(List<Route> list) {
		 longestDistance = 0.0f;
		 Date today = new Date();
		 
		 DateTime now = new DateTime();
		 DateTime lastWeek = now.minusDays(7);
		 lastWeek = lastWeek.hourOfDay().roundFloorCopy();
		 		 
		 Float last7DaysArr[] = new Float[7];
		 Arrays.fill(last7DaysArr, 0.0f);

		 for (Route r : list) {
			 
			 if (lastWeek.isBefore(new DateTime(r.getStarttime()))) {
				 int index = Days.daysBetween(lastWeek, new DateTime(r.getStarttime())).getDays();
				 last7DaysArr[index] += r.getDistance();				 
			 }
			 
			 //total distance
			 totalDistance += r.getDistance();						 
			 
			 //longest distance
			 if (r.getDistance() > longestDistance) {
				 longestDistance = r.getDistance();
			 }			 
					
			 totalTime += r.getStoptime().getTime() - r.getStarttime().getTime();			 
		 }
		 
		 //average distance
		 avgDistance = totalDistance / list.size();
		 
		 //average distance
		 avgTime = totalTime / list.size();
		 
		 emission = totalDistance * 0.185f; //factor for co2???
		 fuel = emission / 2320.0f; //factor for fuel???	
		 
		 //distances of the last 7 days
		 last7Days = Arrays.asList(last7DaysArr);
	 }

	public float getTotalDistance() {
		return totalDistance;
	}

	public float getLongestDistance() {
		return longestDistance;
	}

	public float getAvgDistance() {
		return avgDistance;
	}

	public List<Float> getLast7Days() {
		return last7Days;
	}

	public float getTotalTime() {
		return totalTime;
	}

	public float getAvgTime() {
		return avgTime;
	}

	public float getEmission() {
		return emission;
	}

	public float getFuel() {
		return fuel;
	}
	 
	 
	 
	 
}
