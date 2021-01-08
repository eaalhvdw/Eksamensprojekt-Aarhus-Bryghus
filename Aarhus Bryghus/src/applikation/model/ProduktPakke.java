package applikation.model;


public class ProduktPakke extends Produkt {

	private int indholdStoerrelse;
	private Produktgruppe indholdGruppe;
	private Produkt[] indhold;
	private int indholdAntal = 0;
	
	ProduktPakke(String navn, String beskrivelse, Produktgruppe produktgruppe, int indholdStoerrelse, Produktgruppe indholdGruppe, Salgsstrategi salgsstrategi) {
		super(navn, beskrivelse, produktgruppe, salgsstrategi);
		
		this.indholdStoerrelse = indholdStoerrelse;
		indhold = new Produkt[indholdStoerrelse];
		
		this.indholdGruppe = indholdGruppe;
	}
	
	public boolean derErPlads() {
		if(indholdAntal < indholdStoerrelse) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Getters & Setters
	
	public Produktgruppe hentIndholdGruppe() {
		return indholdGruppe;
	}


	public void seatIndholdGruppe(Produktgruppe indholdGruppe) {
		this.indholdGruppe = indholdGruppe;
	}

	/**
	 * @return the indholdStoerrelse
	 */
	public int hentIndholdStoerrelse() {
		return indholdStoerrelse;
	}

	/**
	 * @param indholdStoerrelse the indholdStoerrelse to set
	 */
	public void seatIndholdStoerrelse(int indholdStoerrelse) {
		this.indholdStoerrelse = indholdStoerrelse;
	}

	/**
	 * @return the indhold
	 */
	public Produkt[] hentIndhold() {
		Produkt[] produkter = indhold;
		return produkter;
	}
	
	public void tilfoejIndhold(Produkt produkt) {
		if(derErPlads()) {
			indhold[indholdAntal] = produkt;
			indholdAntal ++;
		}
	}
}
