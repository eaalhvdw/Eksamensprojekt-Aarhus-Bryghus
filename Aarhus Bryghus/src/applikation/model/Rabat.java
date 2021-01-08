package applikation.model;

import java.time.LocalDate;

public abstract class Rabat {

	protected double rabat;
	private LocalDate startDato;	
	private LocalDate slutDato;
	private Prisliste prisliste;
	
	public Rabat(double rabat) {
		this.rabat = rabat;
	}
	
	public Rabat(double rabat, LocalDate startDato, LocalDate slutDato, Prisliste prisliste) {
		this.rabat = rabat;
		this.startDato = startDato;
		this.slutDato = slutDato;
		this.prisliste = prisliste;
	}

	/**
	 * Denne abstrakte metode beregner rabatprisen 
	 * for et produkt med den valgte prisliste.
	 * @param pris prisen p� produktet.
	 * @return prisen med rabat p� produktet.
	 */
	public abstract double hentRabatPris(double pris);
	
	/**
	 * Denne metode beregner om rabatten er aktiv ud fra start- og slutdato.
	 * @return false hvis slutdato er overskredet. 
	 * @return true hvis startdato er passeret men slutdato ikke er overskredet.
	 */
	public boolean erAktiv() {
		
		LocalDate nu = LocalDate.now();
		if(nu.isAfter(startDato) || nu.isEqual(startDato)){
			if(nu.isBefore(slutDato) || nu.isEqual(slutDato)){
				return true;
			}
		}
		
		return false;
	}
	
	// Getters & Setters
	
	public double hentRabat() {
		return rabat;
	}

	public void seatRabat(double rabat) {
		this.rabat = rabat;
	}

	public LocalDate hentStartDato() {
		return startDato;
	}

	public void seatStartDato(LocalDate startDato) {
		this.startDato = startDato;
	}

	public LocalDate hentSlutDato() {
		return slutDato;
	}

	public void seatSlutDato(LocalDate slutDato) {
		this.slutDato = slutDato;
	}

	public Prisliste hentPrisliste() {
		return prisliste;
	}

	public void seatPrisliste(Prisliste prisliste) {
		this.prisliste = prisliste;
	}

}
