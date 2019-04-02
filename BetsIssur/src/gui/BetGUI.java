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
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BetGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private User logedUser;
	
	private JComboBox<Event> jEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("Query"));
	private JLabel jLabelMinBet = new JLabel("Bet input");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(
			"Etiquetas").getString("EventDate"));
	private JTextField jInput = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton("Create Bet");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle(
			"Etiquetas").getString("Close"));
	private final JLabel jLabelError = new JLabel("");
	private final JLabel lblListOfForecasts = new JLabel(ResourceBundle
			.getBundle("Etiquetas").getString("BetGUI.lblListOfForecasts.text")); //$NON-NLS-1$ //$NON-NLS-2$
	
	
	private final JComboBox<Forecast> jForecasts = new JComboBox<Forecast>();
	private final JComboBox<Question> jQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Forecast> modelForecasts = new DefaultComboBoxModel<Forecast>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	
	public BetGUI(Vector<domain.Event> v, User user) {
		try {
			logedUser = user;
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 384));
		this.setTitle("Bet");
		

		jEvents.setModel(modelEvents);
		jEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(290, 78, 91, 20));
		jLabelMinBet.setBounds(new Rectangle(290, 213, 75, 20));
		jInput.setBounds(new Rectangle(365, 213, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(148, 259, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(323, 259, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jInput, null);

		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jEvents, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(40, 305, 203, 14);

		getContentPane().add(jLabelError);
		lblListOfForecasts.setBounds(new Rectangle(290, 78, 75, 20));
		lblListOfForecasts.setBounds(290, 138, 91, 20);

		getContentPane().add(lblListOfForecasts);
		jForecasts.setBounds(new Rectangle(275, 47, 250, 20));
		jForecasts.setBounds(275, 169, 250, 20);

		getContentPane().add(jForecasts);

		jQuestions.setBounds(275, 107, 250, 20);
		getContentPane().add(jQuestions);

		jQuestions.setModel(modelQuestions);
		jForecasts.setModel(modelForecasts);
		jEvents.setModel(modelEvents);
		
		jEvents.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
					jQuestions.removeAllItems();
					jForecasts.removeAllItems();
					return;
				}

				Event ev = (Event) jEvents.getSelectedItem();
				modelQuestions.removeAllElements();
				jQuestions.removeAllItems();
				jForecasts.removeAllItems();
				if(!ev.getQuestions().isEmpty()){
					
					for (domain.Question q : ev.getQuestions()) {
						modelQuestions.addElement(q);
					}
				}
				jQuestions.repaint();
			}
		});
		
		jQuestions.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED) {
					jForecasts.removeAll();
					return;
				}
				if(modelQuestions.getSize()==0){
					jForecasts.removeAll();
				}else{
				Question q = (Question) jQuestions.getSelectedItem();
				
				modelForecasts.removeAllElements();
				jForecasts.removeAll();

				for (domain.Forecast f : q.getForecasts()) {
				modelForecasts.addElement(f);
				}
				jForecasts.repaint();
				}
			}
		});
		

		
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
						jEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jEvents.repaint();

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
		domain.Question selectedQuestion = ((domain.Question) jQuestions.getSelectedItem());
		domain.Forecast selectedForecast = ((domain.Forecast) jForecasts.getSelectedItem());

		if(selectedForecast != null && selectedQuestion != null){
		
		try {
			jLabelError.setText("");

			if(jInput.getText().length()!=0) {

			float betInput = Float.parseFloat(jInput.getText());

				if (betInput <= selectedQuestion.getBetMinimum())
					jLabelError.setText("Your bet is less than  minimun bet");
				else {

					// Obtain the business logic from a StartWindow class (local
					// or remote)
					BLFacade facade = LoginGUI.getBusinessLogic();

					facade.createBet(selectedForecast, betInput, logedUser);

					jLabelError.setText("Bet Created");
				}
			} else
				jLabelError.setText("Data error");
			
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas")
					.getString("ErrorNumber"));
		} catch (java.lang.NullPointerException e1) {
			jLabelError.setText("Error");
		} catch (Exception e1) {

			e1.printStackTrace();

		}
		}else
			jLabelError.setText("Error");
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
