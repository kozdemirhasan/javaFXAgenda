package de.kozdemir.javaFXAgenda.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
	
	private static final long serialVersionUID = 4415573199437891432L;
	
	private String vorname;
	private String nachname;
	private LocalDate geburstdatum;
	private String email;
	private String telefon;
	private String adress;
	private int plz;
	private String stadt;
	
	
	public Person() {
		
	}


	public Person(String vorname, String nachname, LocalDate geburstdatum, String email, String telefon, String adress,
			int plz, String stadt) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburstdatum = geburstdatum;
		this.email = email;
		this.telefon = telefon;
		this.adress = adress;
		this.plz = plz;
		this.stadt = stadt;
	}


	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}


	public String getNachname() {
		return nachname;
	}
	
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}


	public LocalDate getGeburstdatum() {
		return geburstdatum;
	}


	public void setGeburstdatum(LocalDate geburstdatum) {
		this.geburstdatum = geburstdatum;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public int getPlz() {
		return plz;
	}


	public void setPlz(int plz) {
		this.plz = plz;
	}


	public String getStadt() {
		return stadt;
	}


	public void setStadt(String stadt) {
		this.stadt = stadt;
	} 
	
	
	

}
