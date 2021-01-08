package applikation.model;

import java.util.ArrayList;

/**
 * Klassen Prisliste er en liste 
 * med priser for en bestemt salgssituation.
 */
public class Prisliste {
	
	private String navn;
	private ArrayList<Pris> priser;
	
	/**
	 * Konstruktøren opretter en prisliste med et navn.
	 * Instansvariablen navn er det navn, 
	 * som salgssituationen kan beskrives med.
	 * @param navn navnet på prislisten.
	 */
	public Prisliste(String navn) {
		this.navn = navn;
		priser = new ArrayList<>();
	}

	/**
	 * Prisliste har ansvaret for oprettelse af Pris.
	 * @param pris den pris som skal sættes på produktet.
	 * @param produkt det produkt, som skal have en pris.
	 * @return pris den pris som er blevet sat på produktet.
	 */
	public Pris opretPris(double pris, Produkt produkt) {
		Pris p = new Pris(pris, produkt, this);
		priser.add(p);
		produkt.tilfoejPris(p);
		return p;
	}
	
	/**
	 * setter
	 * @param pris
	 */
	public void tilfoejPris(Pris pris) {
		priser.add(pris);
	}
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Pris> hentPriser() {
		return priser;
	}
	
	/**
	 * getter
	 * @return
	 */
	public String hentNavn() {
		return navn;
	}
}
