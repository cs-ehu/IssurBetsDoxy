package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import com.toedter.calendar.JCalendar;

import domain.Event;

public class CreateEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("ListEvents"));
	private JLabel jLabelEvent = new JLabel("Description");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("EventDate"));

	private JTextField jDescription = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle(
			"Etiquetas").getString("CreateEventGUI.jButtonCreate.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle(
			"Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private Date choosenDate;

	public CreateEventGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle("Create Event");

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelEvent.setBounds(new Rectangle(25, 211, 106, 20));
		jDescription.setBounds(new Rectangle(134, 211, 165, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 275, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e, jCalendar);
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jDescription, null);
		this.getContentPane().add(jLabelEvent, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent
							.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals(
						"calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					choosenDate = calendarMio.getTime();
					DateFormat dateformat1 = DateFormat.getDateInstance(1,
							jCalendar.getLocale());
					jCalendar.setCalendar(calendarMio);
					Date firstDay = trim(new Date(jCalendar.getCalendar()
							.getTime().getTime()));

					try {
						BLFacade facade = LoginGUI.getBusinessLogic();

						Vector<domain.Event> events = facade
								.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle
									.getBundle("Etiquetas").getString(
											"NoEvents")
									+ ": "
									+ dateformat1.format(calendarMio.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle
									.getBundle("Etiquetas").getString("Events")
									+ ": "
									+ dateformat1.format(calendarMio.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
				paintDaysWithEvents(jCalendar);
			}
		});
	}

	private Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and
		// in that case, the background color for that day is changed.

		BLFacade facade = LoginGUI.getBusinessLogic();

		Calendar calendar = jCalendar.getCalendar();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		int month = calendar.get(Calendar.MONTH);
		while (month == calendar.get(Calendar.MONTH)) {
			Vector<domain.Event> events = facade.getEvents(calendar.getTime());
			if (events.size() > 0) {
				// Obtain the component of the day in the panel of the
				// DayChooser of the JCalendar.
				// The component is located after the decorator buttons of
				// "Sun", "Mon",... or "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days
				// previous to each day.
				// That number of components is calculated with "offset" and is
				// different in English and Spanish
				// Component o=(Component)
				// jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);;
				Component o = jCalendar
						.getDayChooser()
						.getDayPanel()
						.getComponent(
								calendar.get(Calendar.DAY_OF_MONTH) + offset);
				;
				o.setBackground(Color.CYAN);
			}
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// jCalendar.setDate(calendar.getTime());
	}

	private void jButtonCreate_actionPerformed(ActionEvent e,
			JCalendar jCalendar) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		jLabelError.setText("");
		jLabelMsg.setText("");

		Calendar calendar = jCalendar.getCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		// Displays an exception if the query field is empty
		String inputEvent = jDescription.getText();

		if (inputEvent.length() > 0) {

			BLFacade facade = LoginGUI.getBusinessLogic();
			System.out.println(choosenDate);
			facade.createEvent(inputEvent, choosenDate);

			jLabelMsg.setText("EventCreated");
		} else
			jLabelMsg.setText("ErrorEvent");

	}

	private Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
