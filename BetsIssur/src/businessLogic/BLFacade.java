package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Bet;
import domain.Event;
import domain.Forecast;
//import domain.Booking;
import domain.Question;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

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
	@WebMethod
	Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist;

	
	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date
	 *            in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);

	/**
	 * This method calls the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();
	
	// ---------------ITERACION 0 FUNCIONES AÑADIDAS---------------------------------
	/**
	 * This method invokes the data access to do the persist of the user
	 * 
	 * @param u
	 *            the User we want to save on DB
	 * @return u
	 */
	@WebMethod
	public User createUser(User u);
	
	
	/**
	 * This method get a User from DB whose dni = u 
	 * 
	 * @param u
	 *            a string with a dninumber
	 * @return User
	 */
	@WebMethod
	public User getUser(String u);

	
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
	@WebMethod
	public Event createEvent(String inputEvent, Date date);

	
	/**
	 * This method creates a forecast, with a forecast text,the forecast income, 
	 * and an event and a question that the forecast answers.
	 * 
	 * @param inputForecast
	 *            text of forecast
	 * @param inputIncome
	 *            the income of forecast
	 * @param question
	 *            question
	 * @param event
	 *            event
	 * @return forecast, or null
	 */
	@WebMethod
	public Forecast createForecast(String inputForecast, float inputIncome,
			Question question);
	
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
	@WebMethod
	public Bet createBet(Forecast f, float input, User u);

}
