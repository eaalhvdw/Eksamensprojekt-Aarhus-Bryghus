package applikation.model;


public class Pris implements Cloneable {

	private double pris;
	private Produkt produkt;
	private Prisliste prisliste;
	
	Pris(double pris, Produkt produkt, Prisliste prisliste) {
		this.pris = pris;
		this.produkt = produkt;
		this.prisliste = prisliste;
	}

	/**
	 * Denne metode bruges til at finde ud af, om der er nogle rabatter på denne pris.
	 * @return boolean true, hvis der er rabat på prisen, og false hvis der ikke er rabat.
	 */
	public boolean harAktiveRabatter() {
		for (Rabat rabat : produkt.hentRabatter()) {
			if(rabat.hentPrisliste().equals(prisliste) && rabat.erAktiv()) {
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * Denne metode beregner en salgspris, 
	 * ved at hente eventuelle rabatter på prisen 
	 * og så trække rabatten fra prisen, hvis der findes en rabat.
	 * @return salgsPris den pris, som produktet bliver solgt for.
	 */
	public double beregnSalgsPris() {
		double salgsPris = pris;
		
		for (Rabat rabat : produkt.hentRabatter()) {
			if(rabat.hentPrisliste().equals(prisliste) && rabat.erAktiv()) {
				salgsPris = rabat.hentRabatPris(salgsPris);
			}
		}	
		return salgsPris;
	}
	
	// Getters & Setters
	
	public double hentPris() {
		return pris;
	}

	public void saetPris(double pris) {
		this.pris = pris;
	}
	
	// ----- Produkt -----
	
	public Produkt hentProdukt() {
		return produkt;
	}

	public void saetProdukt(Produkt produkt) {
		this.produkt = produkt;
	}

	// ----- Prisliste -----
	
	public Prisliste hentPrisliste() {
		return prisliste;
	}

	public void seatPrisliste(Prisliste prisliste) {
		this.prisliste = prisliste;
	}
	
	// ----- Cloneable -----
	/**
	 * Kloner objektet 
	 * @return ny Object af Pris
	 */
	@Override
	public Object clone() {
		
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new Error("CloneNotSupported");
		}
	}

}
