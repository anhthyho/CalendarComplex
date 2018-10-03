package hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * HW#4 Event 
 * author: Anh-Thy Ho 
 */
public class Event implements Comparable<Event> {
	private String date;
	private String startTime;
	private String endTime;
	private String eventName;

	/**
	 * Event constructor creates an event
	 * 
	 * @param date
	 *            is the date of the event in MM/dd/YYYY
	 * @param startTime
	 *            when the event begins 00:00
	 * @param endTime
	 *            when the event ends 00:00
	 * @param name
	 *            the name of the event
	 */
	public Event(String date, String startTime, String endTime, String name) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventName = name;
	}

	/**
	 * gets the date of the event
	 * 
	 * @return the date of the event
	 */
	public String getDate() {
		return date;
	}

	/**
	 * gets the day of the month of the event (numerical)
	 * 
	 * @return the day of the month of the date
	 */
	public String getDay() {
		String dayMonth = date.substring(3, 5);
		return dayMonth;
	}

	public String getTimes() {
		return startTime + " " + endTime;
	}

	/**
	 * returns the month of the event (numerical)
	 * 
	 * @return the numerical month
	 */
	public String getMonth() {
		String month = null;

		if (this.getDate().substring(0, 1).equals("0")) {
			month = this.getDate().substring(1, 2);
		} else {
			month = this.getDate().substring(0, 2);
		}

		return month;
	}

	/**
	 * changes the month from a number to its literal string
	 * 
	 * @return the month from a number into words
	 */
	public String monthToString() {
		ArrayList<String> months = new ArrayList<>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");

		String month = null;
		for (int i = 0; i < months.size(); i++) {
			String iString = i + 1 + "";

			if (this.getMonth().equals(iString)) {
				month = months.get(i);
			}
		}
		return month;

	}

	/**
	 * turns the date (numbers) into a word format
	 * 
	 * @return date of string into words
	 * @throws Exception
	 */
	public String dateToString() throws Exception {
		Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		String month = this.monthToString();
		DateFormat df = new SimpleDateFormat("EEEE" + " " + "MMMM dd");
		String dateString = df.format(date2);
		// String dateString = "" + date2;
		return dateString;
	}

	/**
	 * gets the year of the event
	 * 
	 * @return year of event
	 */
	public String getYear() {
		return date.substring(date.length() - 4, date.length());
	}

	/**
	 * gets the name of the event
	 * 
	 * @return name of the event
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * gets the name of the event in lowercase (used to check for same string)
	 * 
	 * @return name of event in lowercase
	 */
	public String getEventNameLC() {
		return eventName.toLowerCase();
	}

	/**
	 * gets the time frame of the event (the duration 00:00 to 00:00)
	 * 
	 * @return time frame of the event
	 */
	public String getTimeFrame() {
		return startTime + " - " + endTime;
	}

	/**
	 * gets the time the event starts
	 * 
	 * @return starting time of event
	 */
	public int getStartTime() {
		return Integer.parseInt(startTime.substring(0, 2));
	}

	/**
	 * gets the start time minutes
	 * 
	 * @return the minutes of the start time
	 */
	public int getStartTimeMin() {
		return Integer.parseInt(startTime.substring(3, 5));
	}

	/**
	 * gets the time the event end
	 * 
	 * @return ending time of event
	 */
	public int getEndTime() {
		return Integer.parseInt(endTime.substring(0, 2));
	}

	/**
	 * gets the end time minutes
	 * 
	 * @return the minutes of the end time
	 */
	public int getEndTimeMin() {
		return Integer.parseInt(endTime.substring(3, 5));
	}

	/**
	 * turns the date into a string with the time and name of the event
	 * 
	 * @return all the info of the string (date, start time, end time, event name)
	 */
	public String getInfo() throws Exception {
		return this.dateToString() + " " + startTime + "-" + endTime + " " + eventName;
	}

	/**
	 * compare the days of events by date
	 * 
	 * @param event
	 *            is the event to be compared
	 * @return whether it is before, after or equal
	 */
	public int compareDay(Event event) {
		int comp = (this.getDay()).compareTo(event.getDay());
		return comp;
	}

	/**
	 * overrides the compareTo by comparing the month of the events
	 */
	public int compareTo(Event event) {
		int d1 = Integer.parseInt(this.getMonth());
		int d2 = Integer.parseInt(event.getMonth());
		int comp = d1 - d2;
		if (this.getMonth().equals(event.getMonth())) {
			comp = compareDay(event);
		}
		return comp;
	}

}
