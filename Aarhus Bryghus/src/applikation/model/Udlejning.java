package applikation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Udlejning modellerer en udlejning af nogle udlejningsprodukter.
 */
public class Udlejning extends Salg {

	private TilstandEnum tilstand;
	private LocalDate startDato;
	private LocalDate slutDato;
	private boolean forudBetalt;
	
	/**
	 * I konstruktøren af en udlejning 
	 * bliver konstruktøren til salg nedarvet.
	 */
	public Udlejning() {
		super();
		tilstand = TilstandEnum.HJEMME;
	}

	/**
	 * Metoden beregner den periode, som varerne udlejes i.
	 * @return perioden den periode produkterne er udlejet.
	 */
	public int beregnPeriode() {
		int periode = 0;
		periode = (int) ChronoUnit.DAYS.between(startDato, slutDato);
		return periode;
	}
	
	/**
	 * getter
	 * @return
	 */
	public LocalDate hentStartDato() {
		return startDato;
	}
	
	/**
	 * setter
	 * @param startDato
	 */
	public void saetStartDato(LocalDate startDato) {
		this.startDato = startDato;
	}
	
	/**
	 * getter
	 * @return
	 */
	public LocalDate hentSlutDato() {
		return slutDato;
	}
	
	/**
	 * setter
	 * @param slutDato
	 */
	public void saetSlutDato(LocalDate slutDato) {
		this.slutDato = slutDato;
	}
	
	/**
	 * getter
	 * @return
	 */
	public TilstandEnum hentTilstand() {
		return tilstand;
	}
	
	/**
	 * udregner den samlet Pant for udlejning
	 * @return
	 */
	public double samletPantPris() {
		double samletPantPris = 0;
		
		for(Pris p : hentPriser()) {
			samletPantPris += p.hentProdukt().hentPant();
		}
		
		return samletPantPris;
	}
	
	/**
	 * Set udlejning til at være udlejet.
	 */
	public void udlever() {
		
		tilstand = TilstandEnum.UDLEJET;
		
		for (Pris pris : hentPriser()) {
			pris.hentProdukt().saetTilstand(TilstandEnum.UDLEJET);
		}
	}
	
	/**
	 * Metoden kontrollerer om pant 
	 * stemmer overens med det/de beløb, der er 
	 * angivet i betalingsforme
	 * @param betalinger de eller den valgte betalingsform.
	 * @return false hvis salgsbeløbet ikke går op med betalingerne.
	 * @return true hvis salgsbeløb og betalinger stemmer overens.
	 */
	public boolean kanBetalesPant(ArrayList<Betalingsform> betalinger) {
		int sum = 0;
		
		for (Betalingsform betaling : betalinger) {
			sum += betaling.hentSamletVaerdi();
		}
		
		if(sum == samletPantPris()) {
			return true;
		}	
		return false;
	}
	
	/**
	 * Metoden kalder super.betal, hvor der bliver tjekket om det kan betales
	 * Hvis udlejning er blevet blevet betalt, sætter metoden Tilstand i forhold til:
	 * Hvis allerede udlejet eller planlagt udlejet -> Tilstand til hjemme
	 * eller -> Tilstand til planlagt udlejning
	 */
	public void betal(ArrayList<Betalingsform> betalingsformer) {
		if(forudBetalt) {
			super.betal(betalingsformer);
		
			if(hentBetalt()) {
		
				if(tilstand.equals(TilstandEnum.UDLEJET) || tilstand.equals(TilstandEnum.PLANLAGT_UDLEJNING)) {
					tilstand = TilstandEnum.HJEMME;
				}
			}
				
		} else {
			if(kanBetalesPant(betalingsformer)) {
				addAllBeatlingsformer((betalingsformer));
				koebsTidspunkt = LocalDateTime.now();
				forudBetalt = true;
				tilstand = TilstandEnum.PLANLAGT_UDLEJNING;
					
				for (Pris pris : this.hentPriser()) {
					pris.hentProdukt().hentSalgsstrategi().koeb();
				}
			}
		}
	}
	
	public boolean hentForudBetalt() {
		return forudBetalt;
	}

}
