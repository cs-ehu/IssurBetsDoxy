package domain;

import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User {
	@Id
	private String dniNum;
	private String nombre;
	private String apellidos;
	private Date fechaNac;
	private double monedero;
	private String Rol;
	private String email;
	private String password;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Bet> bets = new Vector<Bet>();

	public User(String dniNum, String nombre, String apellidos,
			Date fechaNac, double monedero, String rol, String email,
			String password) {
		super();
		this.dniNum = dniNum;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNac = fechaNac;
		this.monedero = monedero;
		this.Rol = rol;
		this.email = email;
		this.password = password;
	}

	public String getDniNum() {
		return dniNum;
	}

	public void setDniNum(String dniNum) {
		this.dniNum = dniNum;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public double getMonedero() {
		return monedero;
	}

	public void setMonedero(double monedero) {
		this.monedero = monedero;
	}

	public String getRol() {
		return Rol;
	}

	public void setRol(String rol) {
		Rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Vector<Bet> getBets() {
		return bets;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}
	public Bet addBet(Bet b) {
		this.bets.add(b);
		return b;
	}
}
