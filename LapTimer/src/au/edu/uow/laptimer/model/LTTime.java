package au.edu.uow.laptimer.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

public class LTTime implements Comparable<LTTime> {
	private Long time; // in milliseconds
	private Date dateRecord;
	private String comment;

	public LTTime(Long time) {
		super();
		this.time = time;
		this.dateRecord = new Date();
	}

	public LTTime(Long time, String comment) {
		super();
		this.time = time;
		this.dateRecord = new Date();
		this.comment = comment;
	}

	public LTTime(Long time, Date dateRecord, String comment) {
		super();
		this.time = time;
		this.dateRecord = dateRecord;
		this.comment = comment;
	}

	public Long getTime() {
		return time;
	}

	public Date getDateRecord() {
		return dateRecord;
	}

	public String getComment() {
		return comment;
	}

	public static String parseTimeToString(long timeInMilisseconds) {
		DateFormat formatter = new SimpleDateFormat("mm:ss.SSS");
		Calendar calendar = new GregorianCalendar().getInstance();
		calendar.setTimeInMillis(timeInMilisseconds);
		Log.v("teste",
				"" + timeInMilisseconds + " Parsed to "
						+ formatter.format(calendar.getTime()));
		return formatter.format(timeInMilisseconds).substring(0, 7);
	}

	public static String parseDateToString(Date date) {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
		return formatter.format(date);
	}

	@Override
	public int compareTo(LTTime another) {
		return this.time.compareTo(another.getTime());
	}

	@Override
	public String toString() {
		return "" + this.parseTimeToString(this.time) + " - " + this.comment;
	}

}
