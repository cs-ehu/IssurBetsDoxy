package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "
				+ c.isDatabaseLocal() + " getDatabBaseOpenMode: "
				+ c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence
					.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":"
							+ c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccess() {
		new DataAccess(false);
	}

	/**
	 * This is the data access method that initializes the database with some
	 * events and questions. This method is invoked by the business logic
	 * (constructor of BLFacadeImplementation) when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Event ev1 = new Event(1, "Atletico-Athletic", newDate(2018

			, 1, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", newDate(2018, 1, 17));
			Event ev3 = new Event(3, "Getafe-Celta", newDate(2018, 1, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", newDate(2018, 1, 17));
			Event ev5 = new Event(5, "Español-Villareal", newDate(2018, 1, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", newDate(2018, 1, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", newDate(2018, 1, 17));
			Event ev8 = new Event(8, "Girona-Leganés", newDate(2018, 1, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", newDate(2018, 1,
					17));
			Event ev10 = new Event(10, "Betis-Real Madrid",
					newDate(2018, 1, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", newDate(2018, 2, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", newDate(2018, 2, 1));
			Event ev13 = new Event(13, "Getafe-Celta", newDate(2018, 2, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", newDate(2018, 2, 1));
			Event ev15 = new Event(15, "Español-Villareal", newDate(2018, 2, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla",
					newDate(2018, 2, 1));

			Event ev17 = new Event(17, "Malaga-Valencia", newDate(2018, 2, 31));
			Event ev18 = new Event(18, "Girona-Leganés", newDate(2018, 2, 31));
			Event ev19 = new Event(19, "Real Sociedad-Levante", newDate(2018,
					2, 31));
			Event ev20 = new Event(20, "Betis-Real Madrid",
					newDate(2018, 2, 31));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quien ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quien meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quien ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quien ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion(
						"How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?",
						2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17
						.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			User u = new User("1", "Danel", "ScrumMaster", newDate(2017,
					2, 2), 0.0, "Admin", "qqwqwq@gmail.com", "1234");

			db.persist(u);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event
	 *            to which question is added
	 * @param question
	 *            text of the question
	 * @param betMinimum
	 *            minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist
	 *             if the same question already exists for the event
	 */

	public Question createQuestion(Event event, String question,
			float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event
				+ " question= " + question + " betMinimum=" + betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle
					.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST
						// is added in questions property of Event class
						// @OneToMany(fetch=FetchType.EAGER,
						// cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}



	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date
	 *            in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery(
				"SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	private Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	/**
	 * This method persists an event, with a event
	 * 
	 * @param event
	 *            the event
	 * @return the event that is inserted on DB
	 */
	public Event createEvent(Event event) {
		System.out.println(">> DataAccess: createEvent");

		db.getTransaction().begin();
		db.persist(event);
		db.getTransaction().commit();
		return event;

	}

	/**
	 * This method adds a forecast to a question
	 * 
	 * @param f
	 *            the forecast to add
	 * @param event
	              the question we want to add a forecast
	 * @return the updated question
	 */
	public Question saveForecast(Forecast f, Question q) {
		System.out.println(">> DataAccess: saveForecast");


		Question qi = db.find(Question.class, q.getQuestionNumber());

		db.getTransaction().begin();
		qi.addForecast(f);
		db.getTransaction().commit();
		return qi;

	}
	/**
	 * This method adds a bet to an User
	 * 
	 * @param b
	 *            the b
	 * @param st
	              the user dniNum
	 * @return the updated User
	 */
	public User saveUser(Bet b, String st) {
		System.out.println(">> DataAccess: saveUser");

		User us = getUser(st);

		db.getTransaction().begin();
		us.addBet(b);
		db.getTransaction().commit();
		return us;

	}
	/**
	 * This method returns an User
	 * 
	 * @param u
	 *            the user dniNum 
	 * @return the updated User
	 */
	public User getUser(String u) {
		System.out.println(">> DataAccess: getUser");
		return db.find(User.class, u);
	}
	/**
	 * This method persists an User
	 * 
	 * @param u
	 *            User
	 * @return the User
	 */
	public User createUser(User u) {
		System.out.println(">> DataAccess: createUser");
		db.getTransaction().begin();
		db.persist(u);
		db.getTransaction().commit();
		return u;

	}
}
