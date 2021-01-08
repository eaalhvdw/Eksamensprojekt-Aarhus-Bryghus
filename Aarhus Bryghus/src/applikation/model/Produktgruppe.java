package applikation.model;

import java.util.ArrayList;

/**
 * Denne klasse moddellerer en produktgruppe med et navn.
 */
public class Produktgruppe {
	
	private String navn;
	private ArrayList<Produkt> produkter;
	private ArrayList<Produkt> udlejningsProdukter;

	/**
	 * I konstruktøren oprettes to tomme lister til 
	 * hhv. produkter og udlejningsprodukter.
	 * @param navn navnet på produktgruppen.
	 */
	public Produktgruppe(String navn) {
		this.navn = navn;
		produkter = new ArrayList<Produkt>();
		udlejningsProdukter = new ArrayList<Produkt>();
		
	}
	
	private void tilfoejProdukt(Produkt produkt) {
		if(produkt.hentSalgsstrategi() instanceof Udlejningsstrategi) {
			udlejningsProdukter.add(produkt);
		}
		else {
			produkter.add(produkt);
		}
	}
	
	public String hentNavn() {
		return navn;
	}
	
	// ----- Produkt ------
	
	/**
	 * Produktgruppe opretter Produkt objekt
	 * @param navn
	 * @param beskrivelse
	 * @param salgsstrategi
	 * @return
	 */
	public Produkt opretProdukt(String navn, String beskrivelse, Salgsstrategi salgsstrategi) {
		Produkt produkt = new Produkt(navn, beskrivelse, this, salgsstrategi);
		tilfoejProdukt(produkt);
		
		return produkt;
	}
	
	/**
	 * Produktgruppe opretter ProduktPakke objekt
	 * @param navn
	 * @param beskrivelse
	 * @param indholdAntal
	 * @param indholdGruppe
	 * @param salgsstrategi
	 * @return
	 */
	public ProduktPakke opretProduktPakke(String navn, String beskrivelse, int indholdAntal, Produktgruppe indholdGruppe, Salgsstrategi salgsstrategi) {
		ProduktPakke produktPakke = new ProduktPakke(navn, beskrivelse, this, indholdAntal, indholdGruppe, salgsstrategi);
		tilfoejProdukt(produktPakke);
		
		return produktPakke;
	}
	
	/**
	 * Produktgruppe opretter Rundvisning objekt
	 * @param navn
	 * @param beskrivelse
	 * @param salgsstrategi
	 * @return
	 */
	public Rundvisning opretRundvisning(String navn, String beskrivelse, Salgsstrategi salgsstrategi) {
		Rundvisning rundvisning = new Rundvisning(navn, beskrivelse, this, salgsstrategi);
		tilfoejProdukt(rundvisning);
		return rundvisning;
	}
	
	/**
	 * Produktgruppe opretter Fustage objekt
	 * @param navn
	 * @param beskrivelse
	 * @param salgsstrategi
	 * @param liter
	 * @return
	 */
	public Fustage opretFustage(String navn, String beskrivelse, Salgsstrategi salgsstrategi, double liter) {
		Fustage fustage = new Fustage(navn, beskrivelse, this, salgsstrategi, liter);
		tilfoejProdukt(fustage);
		return fustage;
	}
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Produkt> hentProdukter() {
		return new ArrayList<>(produkter);
	}

	/**
	 * getter
	 * @return
	 */
	public ArrayList<Produkt> hentUdlejningsprodukter() {
		return new ArrayList<Produkt>(udlejningsProdukter); 
	}
}
