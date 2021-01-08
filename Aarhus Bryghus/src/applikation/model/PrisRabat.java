package applikation.model;

import java.time.LocalDate;

public class PrisRabat extends Rabat {

	public PrisRabat(double rabat) {
		super(rabat);
	}
	
	public PrisRabat(double rabat, LocalDate startDato, LocalDate slutDato, Prisliste prisliste) {
		super(rabat, startDato, slutDato, prisliste);
	}

	/**
	 * Metoden beregner prisen for et produkt med denne rabat p�.
	 * @return prisen for et produkt med denne rabat.
	 */
	@Override
	public double hentRabatPris(double pris) {
		return pris-rabat;
	}

}
