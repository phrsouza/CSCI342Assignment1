package au.edu.uow.laptimer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import android.util.Log;

public class LTChallenge {
	private String challengeName;
	private ArrayList<LTTime> times;

	public LTChallenge(String challengeName) {
		super();
		this.challengeName = challengeName;
		this.times = new ArrayList<LTTime>();
		;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public ArrayList<LTTime> getTimes() {
		return this.times;
	}

	public void addTime(Long time, Date dateRecord, String comment) {
		this.times.add(new LTTime(time, dateRecord, comment));
		LTTime.parseTimeToString(time);
		this.sortTimes();
	}

	public LTTime timeAtIndex(Integer index) {
		return this.times.get(index);
	}

	public Integer numberOftimes() {
		return this.times.size();
	}

	public LTTime average() {
		Long sum = (long) 0;
		for (LTTime time : times) {
			sum += time.getTime();
		}
		;

		return new LTTime(sum / times.size());
	}

	public LTTime getBestTime() {
		return this.times.get(0);
	}

	public LTTime getWorstTime() {
		return this.times.get(this.times.size() - 1);
	}

	public void sortTimes() {
		Collections.sort(times);
	}
	
}
