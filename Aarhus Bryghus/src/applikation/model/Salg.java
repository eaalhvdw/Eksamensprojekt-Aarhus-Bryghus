package applikation.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Denne klasse modellerer et salg, som indeholder nogle produkter.
 */
public class Salg {

	private int id;
	private static int forrigeId;
	protected boolean betalt;
	protected LocalDateTime koebsTidspunkt;
	//Link attributter
	private ArrayList<Pris> priser;
	private ArrayList<Rabat> rabatter;
	private ArrayList<Betalingsform> betalingsformer;
	private Kunde kunde;
	
	/**
	 * I konstruktøren af et salg bliver der 
	 * genereret et nyt id og nogle nye, tomme lister
	 *  hver gang konstruktøren kaldes.
	 */
	public Salg() {
		forrigeId ++;
		id = forrigeId;
		
		priser = new ArrayList<Pris>();
		rabatter = new ArrayList<Rabat>();
		betalingsformer = new ArrayList<Betalingsform>();
	}
	
	/**
	 * Metoden kontrollerer om salgsbeløbet 
	 * stemmer overens med det/de beløb, der er 
	 * angivet i betalingsforme(n/rne).
	 * @param betalinger de eller den valgte betalingsform.
	 * @return false hvis salgsbeløbet ikke går op med betalingerne.
	 * @return true hvis salgsbeløb og betalinger stemmer overens.
	 */
	public boolean kanBetales(ArrayList<Betalingsform> betalinger) {
		int sum = 0;
		
		for (Betalingsform betaling : betalinger) {
			sum += betaling.hentSamletVaerdi();
		}
		
		if(sum == samletPris()) {
			return true;
		}	
		return false;
	}
	
	/**
	 * Metoden tjekker om betalingsformer kan betale 
	 * salget, hvis ja tilføjes den valgte betalingsform 
	 * til salget og købstidpunktet for salget noteres.
	 * @param betalingsformer de valgte betalingsformer.
	 * @return true hvis betalingsformer kan betales.
	 */
	public void betal(ArrayList<Betalingsform> betalingsformer) {
		
		if(kanBetales(betalingsformer)) {
			this.betalingsformer.addAll(betalingsformer);
			
			for (Pris pris : priser) {
				pris.hentProdukt().hentSalgsstrategi().koeb();
			}
			
			koebsTidspunkt = LocalDateTime.now();
			betalt = true;
		}
	}
	
	/**
	 * Denne metode beregner den samlede pris 
	 * for et salg med diverse rabatter inkluderet.
	 * @return samletPris prisen for hele salgets indhold.
	 */
	public double samletPris() {
		double samletPris = 0;
		
		for(Pris p : priser) {
			samletPris += p.beregnSalgsPris();
		}
		
		for (Rabat rabat : rabatter) {
			samletPris = rabat.hentRabatPris(samletPris);
		}
		return samletPris;
	}

	/**
	 * getter
	 * @return
	 */
	public int hentID() {
		return id;
	}
	
	/**
	 * getter
	 * @return
	 */
	public boolean hentBetalt() {
		return betalt;
	}
	
	/**
	 * getter
	 * @return
	 */
	public LocalDateTime hentKoebsTidspunkt() {
		return koebsTidspunkt;
	}
	
	/**
	 * adder
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
		return new ArrayList<Pris>(priser);
	}
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Rabat> hentRabatter() {
		return new ArrayList<Rabat>(rabatter);
	}
	
	/**
	 * adder
	 * @param rabat
	 */
	public void tilfoejRabat(Rabat rabat) {
		rabatter.add(rabat);
	}
	
	/**
	 * getter
	 * @return
	 */
	public ArrayList<Betalingsform> hentBetalingsformer() {
		return new ArrayList<>(betalingsformer);
	}
	
	/**
	 * add all
	 * @param betalingsformer
	 */
	public void addAllBeatlingsformer(ArrayList<Betalingsform> betalingsformer) {
		this.betalingsformer = betalingsformer;
	}

	/**
	 * getter
	 * @return the kunde
	 */
	public Kunde hentKunde() {
		return kunde;
	}

	/**
	 * setter
	 * @param kunde the kunde to set
	 */
	public void seatKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * setter
	 * @param betalingsform
	 */
	public void tilfoejBetalingsform(Betalingsform betalingsform) {
		betalingsformer.add(betalingsform);
	}
}
