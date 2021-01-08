package applikation.model;

public class Fustage extends Produkt {

	private double liter;
	
	Fustage(String navn, String beskrivelse, Produktgruppe produktgruppe, Salgsstrategi salgsstrategi, double liter) {
		super(navn, beskrivelse, produktgruppe, salgsstrategi);
		this.liter = liter;
		
		saetPant(200);
	}
	
	public void seatLiter(double liter) {
		this.liter = liter;
	}
	
	public double hentLiter() {
		return liter;
	}
}
