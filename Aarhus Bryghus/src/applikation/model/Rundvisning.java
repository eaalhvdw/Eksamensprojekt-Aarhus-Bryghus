package applikation.model;

import java.time.LocalDate;

public class Rundvisning extends Produkt {

	private LocalDate rundvisningsDato;
	
	Rundvisning(String navn, String beskrivelse, Produktgruppe produktgruppe, Salgsstrategi salgsstrategi) {
		super(navn, beskrivelse, produktgruppe, salgsstrategi);
	}

	// Getters & Setters
	
	public LocalDate hentRundvisningsDato() {
		return rundvisningsDato;
	}

	public void seatRundvisningsDato(LocalDate rundvisningsDato) {
		this.rundvisningsDato = rundvisningsDato;
	}

}
