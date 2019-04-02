package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import com.toedter.calendar.JCalendar;

import domain.Event;
import domain.Forecast;
import domain.Question;

public class CreateForecastGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("Query"));
	private JLabel jLabelMinBet = new JLabel("Description");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("EventDate"));
	private JTextField jDescription = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPane = new JScrollPane();

	private JButton jButtonCreate = new JButton("Create forecast");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle(
			"Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private final JLabel lblIncome = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("CreateForecastGUI.lblIncome.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextField jIncome = new JTextField();
	private final JLabel jLabelError = new JLabel("");
	private final JComboBox<Question> jQuestions = new JComboBox<Question>();
	private final DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();

	private final DefaultListModel<Forecast> modelForecasts = new DefaultListModel<Forecast>();
	private final JList<Forecast> jForecasts = new JList<Forecast>();

	public CreateForecastGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {
		jForecasts.setModel(modelForecasts);
		jQuestions.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
					jForecasts.removeAll();
					return;
				}

				Question q = (Question) jQuestions.getSelectedItem();
				modelForecasts.removeAllElements();
				jForecasts.removeAll();
				for (domain.Forecast f : q.getForecasts()) {
					modelForecasts.addElement(f);
				}
				jForecasts.repaint();
			}
		});

		jQuestions.setModel(modelQuestions);
		jQuestions.setBounds(new Rectangle(275, 47, 250, 20));
		jQuestions.setBounds(275, 109, 250, 20);
		getContentPane().add(jQuestions);

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 471));
		this.setTitle("Create Forecast");
		
		jComboBoxEvents.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
					jQuestions.removeAllItems();
					return;
				}

				Event ev = (Event) jComboBoxEvents.getSelectedItem();
				modelQuestions.removeAllElements();
				jQuestions.removeAllItems();
				for (domain.Question q : ev.getQuestions()) {
					modelQuestions.addElement(q);
				}
				jQuestions.repaint();
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(290, 78, 75, 20));
		jLabelMinBet.setBounds(new Rectangle(290, 213, 88, 20));
		jDescription.setBounds(new Rectangle(393, 213, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		jButtonCreate.setEnabled(false);
		// scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(112, 361, 166, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(323, 361, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jDescription, null);

		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		lblIncome.setBounds(new Rectangle(290, 213, 75, 20));
		lblIncome.setBounds(290, 258, 75, 20);

		getContentPane().add(lblIncome);
		jIncome.setBounds(new Rectangle(365, 213, 60, 20));
		jIncome.setBounds(393, 254, 60, 20);

		getContentPane().add(jIncome);
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(292, 294, 231, 35);

		getContentPane().add(jLabelError);
		jForecasts.setBounds(26, 227, 239, 93);

		getContentPane().add(jForecasts);
		jForecasts.setBounds(37, 230, 228, 87);

		getContentPane().add(jForecasts);

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

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

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

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputForecast = jDescription.getText();

			if (inputForecast.length() > 0) {

				// It could be to trigger an exception if the introduced string
				// is not a number
				float inputIncome = Float.parseFloat(jIncome.getText());

				if (inputIncome <= 0)
					jLabelError.setText("Incorrect income");
				else {

					// Obtain the business logic from a StartWindow class (local
					// or remote)
					BLFacade facade = LoginGUI.getBusinessLogic();

					facade.createForecast(inputForecast, inputIncome,
							(Question) jQuestions.getSelectedItem());

					jLabelMsg.setText("Forecast created");
				}
			} else
				jLabelMsg.setText("Incorrect data");
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas")
					.getString("ErrorNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
