package storage;

import java.util.ArrayList;
import java.util.List;

import applikation.model.Prisliste;
import applikation.model.Produktgruppe;
import applikation.model.Salg;
import applikation.model.Udlejning;

public class Storage {

	private static List<Prisliste> prislister = new ArrayList<Prisliste>();
	private static List<Produktgruppe> produktgrupper =  new ArrayList<Produktgruppe>();
	private static ArrayList<Salg> salg  = new ArrayList<Salg>();
	private static ArrayList<Udlejning> udlejninger = new ArrayList<Udlejning>();
	
	public Storage() {
	}
	
	// ----- Prisliste -----
	
	public static void tilfoejPrisliste(Prisliste prisliste) {
		if(!prislister.contains(prisliste))
		prislister.add(prisliste);
	}
	
	public static ArrayList<Prisliste> hentPrislister() {
		return new ArrayList<>(prislister);
	}
	
	// ----- Produktgruppe -----
	
	public static void tilfoejProduktgruppe(Produktgruppe produktgruppe) {
		if(!produktgrupper.contains(produktgruppe)) {
			produktgrupper.add(produktgruppe);
		}
	}
	
	public static ArrayList<Produktgruppe> hentProduktgrupper() {
		return new ArrayList<>(produktgrupper);
	}
	
	// ----- Salg -----
	public static void tilfoejSalg(Salg s) {
		if(!salg.contains(s)) {
			salg.add(s);
		}
	}
	
	public static ArrayList<Salg> hentSalg() {
		return new ArrayList<>(salg);
	}
	
	// ----- Udlejninger -----
	public static void tilfoejUdlejning(Udlejning u) {
		if(!udlejninger.contains(u)) {
			udlejninger.add(u);
		}
	}
	
	public static ArrayList<Udlejning> hentUdlejninger() {
		return new ArrayList<>(udlejninger);
	}
}
