package hw4;

import java.util.ArrayList;

public class CalendarTester {
	public static void main(String[] args) throws Exception {

		ArrayList<Event> events = new ArrayList<>();
		CalendarModel c = new CalendarModel(events);
		c.getEvents();
		CalendarPanel cp = new CalendarPanel(c);

		c.attach(cp);

	}

}
