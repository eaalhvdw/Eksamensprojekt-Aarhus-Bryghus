package applikation.model;

import java.time.LocalDate;

public class ProcentvisRabat extends Rabat {

	public ProcentvisRabat(double rabat) {
		super(rabat);
	}
	
	public ProcentvisRabat(double rabat, LocalDate startDato, LocalDate slutDato, Prisliste prisliste) {
		super(rabat, startDato, slutDato, prisliste);
	}

	/**
	 *  Metoden beregner prisen for et produkt med denne rabat p�.
	 * @return prisen for et produkt med denne rabat.
	 */
	@Override
	public double hentRabatPris(double pris) {
		double samletPris = 0;
		double rabatPris = pris/100 * rabat;
		samletPris = pris - rabatPris;
		return samletPris; 
	}

}
