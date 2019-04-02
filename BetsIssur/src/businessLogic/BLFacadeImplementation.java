package businessLogic;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess(c.getDataBaseOpenMode()
					.equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
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
	 * @throws EventFinished
	 *             if current data is after data of the event
	 * @throws QuestionAlreadyExist
	 *             if the same question already exists for the event
	 */
	@Override
	@WebMethod
	public Question createQuestion(Event event, String question,
			float betMinimum) throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		DataAccess dBManager = new DataAccess();
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas")
					.getString("ErrorEventHasFinished"));

		qry = dBManager.createQuestion(event, question, betMinimum);

		dBManager.close();

		return qry;
	};
	

	/**
	 * This method invokes the data access to retrieve the events of a given
	 * date
	 * 
	 * @param date
	 *            in which events are retrieved
	 * @return collection of events
	 */
	@Override
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		DataAccess dbManager = new DataAccess();
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@Override
	@WebMethod
	public void initializeBD() {
		DataAccess dBManager = new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}
	
	// ---------------ITERACION 0 FUNCIONES AÑADIDAS---------------------------------
	
	/**
	 * This method invokes the data access to do the persist of the user
	 * 
	 * @param u
	 *            the User we want to save on DB
	 * @return u
	 */
	@Override
	public User createUser(User u) {

		DataAccess dBManager = new DataAccess();

		dBManager.createUser(u);

		dBManager.close();

		return u;
	};
	/**
	 * This method get a User from DB whose dni = u 
	 * 
	 * @param u
	 *            a string with a dninumber
	 * @return User
	 */
	@Override
	public User getUser(String u) {
		DataAccess dBManager = new DataAccess();
		User x = dBManager.getUser(u);
		dBManager.close();
		return x;

	}
	/**
	 * This method creates an event, with a event text and the
	 * date
	 * 
	 * @param inputEvent
	 *            text of event
	 * @param date
	 *            event date
	 * @return the created event, or null
	 */
	@Override
	public Event createEvent(String inputEvent, Date date) {
		Event e = new Event(inputEvent, date);
		DataAccess dBManager = new DataAccess();
		dBManager.createEvent(e);
		dBManager.close();
		return e;

	}
	/**
	 * This method creates a forecast, with a forecast text,the forecast income, 
	 * and the question that the forecast answers.
	 * 
	 * @param inputForecast
	 *            text of forecast
	 * @param inputIncome
	 *            the income of forecast
	 * @param question
	 *            question

	 * @return forecast, or null
	 */
	@Override
	public Forecast createForecast(String inputForecast, float inputIncome,
			Question question) {
		Forecast f = new Forecast(inputForecast, inputIncome, question);
		DataAccess dBManager = new DataAccess();
		dBManager.saveForecast(f, question);
		dBManager.close();
		return null;
	}
	/**
	 * This method creates a bet, with a forecast,the bet input, 
	 * and the user who does the bet
	 * 
	 * @param f
	 *           forecast you want to bet
	 * @param input
	 *            the amount you want to bet
	 * @param user
	 *            the user who is doing the bet
	 * @return bet, or null
	 */
	@Override
	public Bet createBet(Forecast f, float input, User user) {
		Bet b = new Bet(f,input);
		DataAccess dBManager = new DataAccess();
		dBManager.saveUser(b,user.getDniNum());
		dBManager.close();
		return null;
	}

}
