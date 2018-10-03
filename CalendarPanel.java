package hw4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

enum MONTHS {
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}

enum DAYS {
	Su, Mo, Tu, We, Th, Fr, Sa;
}

/**
 * calendar panel that contains views of the data model
 * 
 * @author anhthy ho hw #4
 *
 */
public class CalendarPanel extends JFrame implements ChangeListener {
	private CalendarModel calendarModel;
	private ArrayList<Event> events;
	private ArrayList<Event> dayEvents;
	private ArrayList<Event> monthEvents;
	private Rectangle2D.Double[] calendarBoxes;
	private GregorianCalendar gc;
	private String day;
	private String month;
	private int firstDay;

	private Point mousePoint;

	/**
	 * Creates mouselisteners to use mouse points/controller on board
	 * 
	 * @author Anh-Thy Ho
	 *
	 */
	private class MouseListeners extends MouseAdapter {

		/**
		 * moves beads/update data when the mouse is pressed finds the pit which
		 * contains moved point
		 * 
		 * @param e
		 *            is the mouse event aka where the mouse is clicked
		 */
		public void mousePressed(MouseEvent e) {
			mousePoint = e.getPoint();
			Rectangle2D.Double current = new Rectangle2D.Double();
			for (int i = 0; i < calendarBoxes.length; i++) {
				current = calendarBoxes[i];
				if (current.contains(mousePoint)) {
					int select = i - firstDay - 6;
					String s = select + "";
					if (select < 10) {
						s = "0" + select;
					}
					calendarModel.setDay(s);
					calendarModel.setDayEvents(s);
				}
			}

		}

		@Override
		/**
		 * allows mouse wheel to be moved
		 * 
		 * @param arg0
		 *            is where mouse wheel is moved
		 */
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			if (mousePoint == null) {
				return;
			}
			Point lastMousePoint = mousePoint;
			mousePoint = arg0.getPoint();
			double dx = mousePoint.getX() - lastMousePoint.getX();
			double dy = mousePoint.getY() - lastMousePoint.getY();

		}

	}

	/**
	 * the frame to hold views
	 * 
	 * @param c
	 *            calendarmodel that contains events
	 * @throws Exception
	 *             for date string
	 */
	public CalendarPanel(CalendarModel c) throws Exception {
		this.setSize(1000, 1000);
		this.setLayout(new BorderLayout());
		calendarModel = c;
		gc = calendarModel.getGC();
		events = calendarModel.getData();
		day = calendarModel.getDay();
		monthEvents = calendarModel.getMonth();
		month = calendarModel.getMonthString();
		calendarBoxes = new Rectangle2D.Double[43];

		MouseListeners listeners = new MouseListeners();
		addMouseListener(listeners);
		addMouseMotionListener(listeners);

		this.setTitle("Calendar");

		JPanel cg = new JPanel();
		// GregorianCalendar temp = new GregorianCalendar(gc.get(Calendar.YEAR),
		// gc.get(Calendar.MONTH)+1, 1);

		int lastDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

		JPanel eventPanel = new JPanel();

		int calendarH = 300;
		int calendarW = 300;
		Icon calendarIcon = new Icon() {
			int boxD = 40;

			@Override
			public int getIconHeight() {
				// TODO Auto-generated method stub
				return calendarH;
			}

			@Override
			public int getIconWidth() {
				// TODO Auto-generated method stub
				return calendarW;
			}

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				// TODO Auto-generated method stub

				Graphics2D g2 = (Graphics2D) g;

				Rectangle2D.Double title = new Rectangle2D.Double(0, 0, 280, 20);
				g2.setPaint(Color.white);
				g2.draw(title);
				g2.fill(title);
				g2.setPaint(Color.black);

				
				String date = (gc.get(Calendar.MONTH) + 1) +"";
				String year = new SimpleDateFormat("MMMM").format(gc.get(Calendar.MONTH) + 1);

				g2.drawString(date, (int) title.getCenterX() - 20, (int) title.getCenterY() + 5);

				for (int i = 0; i < 43; i++) {
					if (i <= 7) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 1) * boxD, 20 + 0, boxD, boxD);
						calendarBoxes[i] = (box);
					} else if (i <= 14) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 8) * boxD, 20 + boxD, boxD, boxD);
						calendarBoxes[i] = (box);
					} else if (i <= 21) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 15) * boxD, 20 + 2 * boxD, boxD, boxD);
						calendarBoxes[i] = (box);
					} else if (i <= 28) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 22) * boxD, 20 + 3 * boxD, boxD, boxD);
						calendarBoxes[i] = (box);
					} else if (i <= 35) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 29) * boxD, 20 + 4 * boxD, boxD, boxD);
						calendarBoxes[i] = (box);
					} else if (i <= 43) {
						Rectangle2D.Double box = new Rectangle2D.Double((i - 36) * boxD, 20 + 5 * boxD, boxD, boxD);
						calendarBoxes[i] = (box);
					}

					// Rectangle2D.Double box = new Rectangle2D.Double(0+i*boxD,boxD, boxD, boxD);

				}
				for (int i = 0; i < calendarBoxes.length; i++) {
					g2.setPaint(Color.white);
					g2.fill(calendarBoxes[i]);
					g2.setPaint(Color.BLACK);
					g2.draw(calendarBoxes[i]);

				}

				firstDay = gc.get(Calendar.DAY_OF_WEEK) ;
				for (int i = firstDay; i < gc.getActualMaximum(Calendar.DAY_OF_MONTH) + firstDay; i++) {
					Rectangle2D.Double box = calendarBoxes[i];
					g2.drawString((i - (firstDay) + 1 + ""), (int) box.getX() + 10, (int) box.getCenterY());
				}

			}

		};
		JLabel cal = new JLabel();
		cal.setIcon(calendarIcon);
		cg.add(cal);
		add(cg, BorderLayout.WEST);

		calendarModel.setMonthsEvents(calendarModel.getMonthString());
		calendarModel.setDayEvents(calendarModel.getDay());

		dayEvents = calendarModel.getDayEvents();

		eventPanel.add(new JLabel("Day's Events: "));
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.PAGE_AXIS));
		int height = 400;
		int width = 400;

		Icon eventIcon = new Icon() {

			@Override
			public int getIconHeight() {
				// TODO Auto-generated method stub
				return height;
			}

			@Override
			public int getIconWidth() {
				// TODO Auto-generated method stub
				return width;
			}

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2 = (Graphics2D) g;
				int boxHeight = 50;
				int dy = 0;
				ArrayList<Rectangle2D.Double> eventBoxes = new ArrayList<>();
				for (int i = 0; i < dayEvents.size(); i++) {
					Event e = dayEvents.get(i);
					dy = i * boxHeight + 10;
					Rectangle2D.Double box = new Rectangle2D.Double(10, dy, width, boxHeight);

					eventBoxes.add(box);

				}
				ArrayList<Event> print = new ArrayList<>();
				Collections.sort(events);
				for (Event e : dayEvents) {
					print.add(e);
				}
				for (int i = 0; i < eventBoxes.size(); i++) {
					Rectangle2D.Double box = eventBoxes.get(i);

					g2.setPaint(Color.PINK);
					g2.fill(box);
					g2.setPaint(Color.black);
					g2.draw(box);
					g2.setColor(Color.BLACK);
					g2.drawString("Event: " + print.get(i).getTimeFrame(), 20, (int) box.getY() + 20);
					g2.drawString(print.get(i).getEventName(), 20, (int) box.getY() + 40);

				}

			}

		};

		JLabel eventLabel = new JLabel();
		eventLabel.setIcon(eventIcon);
		eventPanel.add(eventLabel);
		add(eventPanel, BorderLayout.EAST);

		JPanel optionPanel = new JPanel();
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.add(Calendar.MONTH, 1);
				calendarModel.updateGC(gc);
				String s = calendarModel.getMonthString();
				int select = Integer.parseInt(s) + 1;
				calendarModel.setMonth(select + "");
				calendarModel.setMonthsEvents(select + "");

			}
		});

		JButton previous = new JButton("Previous");
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gc.add(Calendar.MONTH, -1);
				calendarModel.updateGC(gc);

				String s = calendarModel.getMonthString();
				int select = Integer.parseInt(s) - 1;

				calendarModel.setMonth(select + "");
				calendarModel.setMonthsEvents(select + "");

			}
		});

		JTextField tf = new JTextField();
		tf.setText("start time: ");
		
		JTextField tf2 = new JTextField();
		tf2.setText("end time: ");
		
		JTextField tf3 = new JTextField();
		tf3.setText("event name: ");

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String month = calendarModel.getMonthString();
				int mo = Integer.parseInt(month)%12; 
				if (mo==0) {
					mo = 12;
				}
				System.out.println(mo);
				month = mo +""; 
				String day = calendarModel.getDay();
				if (month.length()<=1) {
					month = "0"+month;
				}
				String year = "" + calendarModel.getGC().get(Calendar.YEAR);
				String date = month + "/" + day + "/" + year;

				String startTime = tf.getText();
				
				String endTime = tf2.getText();
				String eventName = tf3.getText();

				Event ev = new Event(date, startTime, endTime, eventName);
				try {
					calendarModel.update(ev);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				

			}

		});

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(calendarModel.getFile()));
					for (Event ev : events) {
						String str = ev.getDate() + " " + ev.getTimes() + " " + ev.getEventName() + "\n";
						writer.write(str);

					}
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		optionPanel.add(previous);
		optionPanel.add(next);
		optionPanel.add(tf);
		optionPanel.add(tf2);
		optionPanel.add(tf3);
		optionPanel.add(create);
		optionPanel.add(quit);

		this.add(optionPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * should repain the frame based on notifier from model
	 * 
	 * @param e
	 *            is the change event created by modifying the model
	 */
	public void stateChanged(ChangeEvent e) {

		gc = calendarModel.getGC();
		day = calendarModel.getDay();
		month = calendarModel.getMonthString();
		monthEvents = calendarModel.getMonth();
		dayEvents = calendarModel.getDayEvents();
		events = calendarModel.getData();

		repaint();
		revalidate();

	}
}
