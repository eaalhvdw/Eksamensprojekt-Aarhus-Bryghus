package applikation.model;

import java.util.ArrayList;

/**
 * Denne klasse modellerer et produkt i en produktgruppe.
 */
public class Produkt {
	
	private int id;
	private static int forrigeId;
	private String navn;
	private String beskrivelse;
	private double pant;
	private boolean harPant = false;
	
	// Link attributter
	private Produktgruppe produktgruppe;
	private ArrayList<Pris> priser;
	private ArrayList<Rabat> rabatter;
	private TilstandEnum tilstand;
	private Salgsstrategi salgsstrategi;

	/**
	 * Konstruktøren er package protected.
	 * @param navn navnet på produktet.
	 * @param beskrivelse beskrivelse af produktets indhold.
	 * @param produktgruppe den produktgruppe, som opretter produktet.
	 */
	Produkt(String navn, String beskrivelse, Produktgruppe produktgruppe, Salgsstrategi salgsstrategi) {
		
		forrigeId ++;
		id = forrigeId;

		this.navn = navn;
		this.beskrivelse = beskrivelse;
		
		priser = new ArrayList<Pris>();
		rabatter = new ArrayList<Rabat>();
		tilstand = TilstandEnum.HJEMME;
		this.salgsstrategi = salgsstrategi;
	}

	/**
	 * getter
	 * @return
	 */
	public int hentID() {
		return id;
	}
	
	/**
	 * setter
	 * @param navn
	 */
	public void seatNavn(String navn) {
		this.navn = navn;
	}
	
	/**
	 * getter
	 * @return
	 */
	public String hentNavn() {
		return navn;
	}

	/**
	 * getter
	 * @return
	 */
	public String hentBeskrivelse() {
		return beskrivelse;
	}
	
	/**
	 * setter
	 * @param beskrivelse
	 */
	public void seatBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	// ----- Produktgruppe -----
	
	/**
	 * getter
	 * @return
	 */
	public Produktgruppe hentProduktgruppe() {
		return produktgruppe;
	}

	// ----- Salgsstrategi -----
	
	/**
	 * setter
	 * @param salgsstrategi
	 */
	public void seatSalgsstrategi(Salgsstrategi salgsstrategi) {
		this.salgsstrategi = salgsstrategi;
	}
	
	/**
	 * getter
	 * @return
	 */
	public Salgsstrategi hentSalgsstrategi() {
		return salgsstrategi;
	}

	// ------ TilstandEnum -----
	
	/**
	 * setter
	 * @param tilstand
	 */
	public void saetTilstand(TilstandEnum tilstand) {
		this.tilstand = tilstand;
	}
	
	/**
	 * getter
	 * @return
	 */
	public TilstandEnum hentTilstand() {
		return tilstand;
	}

	// ------ Pris ------
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Pris> hentPriser() {
		return priser;
	}

	/**
	 * add
	 * @param pris
	 */
	public void tilfoejPris(Pris pris) {
		this.priser.add(pris);
	}
	
	/**
	 * setter
	 * @param priser
	 */
	public void seatPriser(ArrayList<Pris> priser) {
		this.priser = priser;
	}

	// ----- Rabat -----
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Rabat> hentRabatter() {
		return rabatter;
	}
	
	/**
	 * add
	 * @param rabat
	 */
	public void tilfoejRabat(Rabat rabat) {
		rabatter.add(rabat);
	}
	
	/**
	 * setter
	 * @param rabatter
	 */
	public void seatRabatter(ArrayList<Rabat> rabatter) {
		this.rabatter = rabatter;
	}
	
	// ----- Pant -----
	/**
	 * getter
	 * @return
	 */
	public boolean harPant() {
		return harPant;
	}
	
	/**
	 * getter
	 * @return
	 */
	public double hentPant() {
		return pant;
	}
	
	/**
	 * setter
	 * @param pant
	 */
	public void saetPant(double pant) {
		if(pant > 0){
			harPant = true;
			this.pant = pant;
		} 
	}

}
