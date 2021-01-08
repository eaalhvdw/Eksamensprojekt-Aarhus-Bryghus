package applikation.model;

/**
 * Denne klasse modellerer en Betalingsform, 
 * som har en type af klassen BetalingsformEnum.
 */
public class Betalingsform {

	private BetalingsformEnum type;
	private double betaling; // Den mængde der bliver betalt af betalingsformen.
	private double vaerdi; // Værdien for en betaling i dkk

	/**
	 * Konstruktør
	 * @param type
	 * @param betaling
	 */
	public Betalingsform(BetalingsformEnum type, double betaling) {
		this.type = type;
		this.betaling = betaling;
		vaerdi = udregnVaerdi();
	}
	
	/**
	 * metode
	 * @return
	 */
	private double udregnVaerdi() {
		if(type.equals(BetalingsformEnum.KLIPPEKORT)){
			return 15; // En fadøl koster 30 dkk og kræver to klip
		}
		return 1;
	}
	
	/**
	 * getter
	 * @return
	 */
	public double hentSamletVaerdi() {
		return betaling * vaerdi;
	}
	
	/**
	 * getter
	 * @return
	 */
	public BetalingsformEnum hentBetalingsformEnum() {
		return type;
	}

	/**
	 * getter
	 * @return
	 */
	public double hentBetaling() {
		return betaling;
	}

	/**
	 * sætter
	 * @param betaling
	 */
	public void saetBetaling(double betaling) {
		this.betaling = betaling;
	}	
}
