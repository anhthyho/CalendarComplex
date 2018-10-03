package hw4;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel {

	private ArrayList<Event> events;
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Event> monthsEvents;
	private ArrayList<Event> dayEvents;
	private String buttonDay;
	private String month;
	private Color color;
	private GregorianCalendar gc;
	private File f;

	/**
	 * Model that holds events and changes views
	 * 
	 * @param events
	 *            the events to add/contained within model
	 * @throws Exception
	 *             for date
	 */
	public CalendarModel(ArrayList<Event> events) throws Exception {
		this.events = events;
		this.listeners = new ArrayList<ChangeListener>();
		gc = new GregorianCalendar();
		if ((gc.get(Calendar.DAY_OF_MONTH)) < 10) {
			buttonDay = "0" + (gc.get(Calendar.DAY_OF_MONTH)) + "";
		} else {
			buttonDay = (gc.get(Calendar.DAY_OF_MONTH)) + "";
		}

		month = (gc.get(Calendar.MONTH) + 1) + "";

		monthsEvents = new ArrayList<>();
		dayEvents = new ArrayList<>();

	}

	/**
	 * gets the gregorian calendar of the model
	 * @return current gregorian calendar
	 */
	public GregorianCalendar getGC() {
		return gc;
	}

	/**
	 * change the gc of the model
	 * @param g new gc 
	 */
	public void updateGC(GregorianCalendar g) {
		gc = g;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));

		}
	}

	/**
	 * Constructs a DataModel object
	 * 
	 * @return the data in an ArrayList
	 */
	public ArrayList<Event> getData() {
		return (ArrayList<Event>) (events.clone());
	}

	/**
	 * attaches panel listeners to notify for changes
	 * 
	 * @param calendarPanel
	 *            panel to attach
	 */
	public void attach(CalendarPanel calendarPanel) {
		listeners.add(calendarPanel);
	}

	/**
	 * returns month to display
	 * 
	 * @return month to display
	 */
	public ArrayList<Event> getMonth() {
		return (ArrayList<Event>) (monthsEvents.clone());
	}

	/**
	 * sets month to gather events from
	 * 
	 * @param month
	 *            string of month to find
	 */
	public void setMonthsEvents(String month) {
		ArrayList<Event> temp = new ArrayList<>();
		for (int i = 0; i < events.size(); i++) {
			Event e = events.get(i);
			if (e.getMonth().equals(month)) {
				temp.add(e);
			}
		}
		monthsEvents = temp;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * sets the month string to find
	 * 
	 * @param s
	 *            month string
	 */
	public void setMonth(String s) {
		month = s;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * gets the string of the month to contain
	 * 
	 * @return string of month
	 */
	public String getMonthString() {
		return month;
	}

	/**
	 * gets events from specified day
	 * 
	 * @return array list of events on certain day
	 */
	public ArrayList<Event> getDayEvents() {
		return (ArrayList<Event>) (dayEvents.clone());
	}

	/**
	 * sets the days events from specified day
	 * 
	 * @param day
	 *            is the day of the current month to find
	 */
	public void setDayEvents(String day) {
		ArrayList<Event> temp = new ArrayList<>();
		for (Event e : monthsEvents) {
			if (e.getDay().equals(day)) {
				temp.add(e);
			}
		}
		dayEvents = temp;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));

		}
	}

	/**
	 * sets the day to find events
	 * 
	 * @param day
	 *            to find events on
	 */
	public void setDay(String day) {
		buttonDay = day;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * returns the day for events
	 * 
	 * @return string of day
	 */
	public String getDay() {
		return buttonDay;
	}

	/**
	 * Change the data in the model at a particular location
	 * 
	 * @param location
	 *            the index of the field to change
	 * @param value
	 *            the new value
	 * @throws Exception 
	 */
	public void update(Event e) throws Exception {
		events.add(e);
		this.addEvent(e);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * reads line to get events
	 * 
	 * @throws Exception
	 */
	public void getEvents() throws Exception {
		f = new File("events.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));

		ArrayList<Integer> spaces = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			for (int i = 0; i < line.length(); i++) {
				if (line.substring(i, i + 1).equals(" ") && spaces.size() < 3) {
					spaces.add(i);
				}
			}
			String date = line.substring(0, spaces.get(0));
			String startTime = line.substring(spaces.get(0) + 1, spaces.get(1));
			String endTime = line.substring(spaces.get(1) + 1, spaces.get(2));
			String eventName = line.substring(spaces.get(2) + 1, line.length());
			Event e = new Event(date, startTime, endTime, eventName);
			if (this.addEvent(e)) {
				events.add(e);
			}

		}

		br.close();

	}

	/**
	 * gets the file of the events
	 * @return file of events
	 */
	public File getFile() {
		return f;
	}
	


	/**
	 * adds events to the model from reading file
	 * 
	 * @param events
	 *            to add
	 * @return true or false if accepted
	 * @throws Exception
	 *             for date
	 */
	public boolean addEvent(Event e) throws Exception {

		ArrayList<Event> sameTime = new ArrayList<>();
		for (Event sd : events) {
			if (sd.getDate().equals(e.getDate())) {

				int eStart = e.getStartTime();
				int eEnd = e.getEndTime();
				int sdStart = sd.getStartTime();
				int sdEnd = sd.getEndTime();

				int eMin = e.getStartTimeMin();
				int eEMin = e.getEndTimeMin();
				int sdSM = sd.getStartTimeMin();
				int sdEM = sd.getEndTimeMin();

				if (eStart < sdEnd && eEnd > sdStart) { // event begins before current ends
					sameTime.add(e);
				} else if (eStart > sdStart && eEnd < sdEnd) { // new event is within old event timeframe
					sameTime.add(e);
				} else if (eStart == sdStart || eStart == sdEnd || eEnd == sdStart || sdEnd == eStart) {
					if (sdEM >= eMin && eEMin >= sdSM) { // if start minutes is more than new ending minutes 06:20 >
															// 06:15
						sameTime.add(e);
					} else if (eEMin >= sdSM && eMin <= sdEM) {
						sameTime.add(e);
					} else if (eMin <= sdSM && eEMin >= sdSM) {
						sameTime.add(e);
					}
				}
			}

		}
		
		

		if (!sameTime.contains(e)) { // adds what does not have overlapping
					
			// times, prints added event information
			return true;
		} else {
			System.out.println("Cannot add event, time overlaps with existing event ");
			return false;
		}
		
		

	}

}
