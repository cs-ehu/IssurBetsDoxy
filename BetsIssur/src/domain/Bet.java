package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet {
	
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNum;
	private Forecast forecast;
	private float input;
	
	public Bet(Integer betNum, Forecast forecast, float input) {
		super();
		this.betNum = betNum;
		this.forecast = forecast;
		this.input = input;
	}
	
	public Bet(Forecast forecast, float input) {
		super();
		this.forecast = forecast;
		this.input = input;
	}

	public Integer getBetNum() {
		return betNum;
	}

	public void setBetNum(Integer betNum) {
		this.betNum = betNum;
	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public float getInput() {
		return input;
	}

	public void setInput(float input) {
		this.input = input;
	}

}
