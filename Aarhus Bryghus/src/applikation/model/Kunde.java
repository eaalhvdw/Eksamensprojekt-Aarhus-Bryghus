package applikation.model;

import java.util.ArrayList;

/**
 * klassebeskrivelse
 */
public class Kunde {
	
	private int id;
	private static int forrigeId;
	private String navn;
	private String email;
	private int telefonNr;
	private ArrayList<Salg> salgListe;
	
	/**
	 * Konstruktør
	 * @param navn
	 * @param email
	 */
	public Kunde(String navn, String email, int telefonnr) {
		forrigeId ++;
		id = forrigeId;
		this.navn = navn;
		this.email = email;
		this.telefonNr = telefonnr;
		salgListe = new ArrayList<Salg>();
	}
	
	/**
	 * Metode
	 * @param salg
	 */
	void tiloejSalg(Salg salg) {
		salgListe.add(salg);
	}
	
	public int hentId() {
		return id;
	}
	
	public String hentNavn() {
		return navn;
	}
	
	public String hentEmail() {
		return email;
	}
	
	public int hentTelefonNr() {
		return telefonNr;
	}
	
	public String toString() {
		return "Navn: " + navn + ", email: " + email;
	}
}