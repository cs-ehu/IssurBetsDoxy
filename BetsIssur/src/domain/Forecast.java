package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Forecast {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer forecastNum;
	private String forecastDesc;
	private float income;
	@XmlIDREF
	private Question question;

	public Forecast(Integer forecastNum, String forecastDesc, float income,
			Question question) {
		super();
		this.forecastNum = forecastNum;
		this.forecastDesc = forecastDesc;
		this.income = income;
		this.question = question;
	}

	public Forecast(String forecastDesc, float income, Question question) {
		super();

		this.forecastDesc = forecastDesc;
		this.income = income;
		this.question = question;
	}

	public Integer getForecastNum() {
		return forecastNum;
	}

	public void setForecastNum(Integer forecastNum) {
		this.forecastNum = forecastNum;
	}

	public String getForecastDesc() {
		return forecastDesc;
	}

	public void setForecastDesc(String forecastDesc) {
		this.forecastDesc = forecastDesc;
	}

	public float getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	@Override
	public String toString() {
		return "" + forecastDesc + "==> income :" + income + "€";
	}
}
