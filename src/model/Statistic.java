package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Statistic {
	private int score;
	private double emissions = 0; 
	private double fuel = 0; 
	private double avgDistance = 0; 
	private double longestDistance = 0; 
	private double totalDistance = 0; 
	private long avgTime = 0;
	private long longestTime = 0;
	private long totalTime = 0;
	private List<Double> last7DaysDistances = new ArrayList<Double>();
	private List<Long> last7DaysTimes = new ArrayList<Long>();
	private double globalEmissions = 0;
	private double globalFuel = 0;
	private long globalTime = 0;
	private double globalDistance = 0;
	
	private final float EMISSION_FACTOR = 0.185f;
	private final float FUEL_FACTOR = 2320.0f;
	

	public Statistic(List<Route> list, int score, double globalDistance, long globalTime) {
		//global statistics and score
		this.score = score;
		this.globalDistance = globalDistance;
		this.globalTime = globalTime;
		this.globalEmissions = globalDistance * EMISSION_FACTOR;
		this.globalFuel = globalEmissions / FUEL_FACTOR;

		if (list.size() <= 0) 
			return;
		
		longestDistance = 0.0f;;

		DateTime now = new DateTime();
		DateTime lastWeek = now.minusDays(6);
		lastWeek = lastWeek.hourOfDay().roundFloorCopy();

		Double last7DaysDistancesArr[] = new Double[7];
		Long last7DaysTimesArr[] = new Long[7];
		Arrays.fill(last7DaysDistancesArr, 0.0);
		Arrays.fill(last7DaysTimesArr, Long.valueOf(0));

		for (Route r : list) {
			//last 7 days distance and time
			if (lastWeek.isBefore(new DateTime(r.getStartTime()))) {
				int index = Days.daysBetween(lastWeek,
						new DateTime(r.getStartTime())).getDays();
				last7DaysDistancesArr[index] += r.getDistance();
				last7DaysTimesArr[index] += r.getStopTime().getTime()
						- r.getStartTime().getTime();
			}

			// total distance
			totalDistance += r.getDistance();

			// longest distance
			if (r.getDistance() > longestDistance)
				longestDistance = r.getDistance();

			// longest time
			if (r.getStopTime().getTime() - r.getStartTime().getTime() > longestTime)
				longestTime = r.getStopTime().getTime()
						- r.getStartTime().getTime();

			totalTime += r.getStopTime().getTime() - r.getStartTime().getTime();
		}

		// average distance
		avgDistance = totalDistance / list.size();

		// average distance
		avgTime = totalTime / list.size();	

		emissions = totalDistance * EMISSION_FACTOR; // factor for co2???
		fuel = emissions / FUEL_FACTOR; // factor for fuel???

		// distances of the last 7 days
		last7DaysDistances = Arrays.asList(last7DaysDistancesArr);
		last7DaysTimes = Arrays.asList(last7DaysTimesArr);
	}


	public int getScore() {
		return score;
	}

	public double getEmissions() {
		return emissions;
	}

	public double getFuel() {
		return fuel;
	}

	public double getAvgDistance() {
		return avgDistance;
	}

	public double getLongestDistance() {
		return longestDistance;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public long getAvgTime() {
		return avgTime;
	}

	public long getLongestTime() {
		return longestTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public List<Double> getLast7DaysDistances() {
		return last7DaysDistances;
	}

	public List<Long> getLast7DaysTimes() {
		return last7DaysTimes;
	}

	public double getGlobalEmissions() {
		return globalEmissions;
	}

	public double getGlobalFuel() {
		return globalFuel;
	}

	public long getGlobalTime() {
		return globalTime;
	}

	public double getGlobalDistance() {
		return globalDistance;
	}
}
