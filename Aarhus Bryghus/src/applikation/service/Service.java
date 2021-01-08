package applikation.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import applikation.model.ProduktPakke;
import applikation.model.Betalingsform;
import applikation.model.BetalingsformEnum;
import applikation.model.Fustage;
import applikation.model.Pris;
import applikation.model.PrisRabat;
import applikation.model.Prisliste;
import applikation.model.ProcentvisRabat;
import applikation.model.Produkt;
import applikation.model.Produktgruppe;
import applikation.model.Rabat;
import applikation.model.Rundvisning;
import applikation.model.Salg;
import applikation.model.Salgsstrategi;
import applikation.model.StandardSalgsstrategi;
import applikation.model.TilstandEnum;
import applikation.model.Udlejning;
import applikation.model.Udlejningsstrategi;
import applikation.model.Udlejningsstrategi;
import storage.Storage;

/**
 * Singleton
 */
public class Service {

	/**
	 * statisk instansvariabel service
	 */
	private Storage storage;
	private static Service service;
	
	/**
	 * privat konstuktør
	 */
	private Service() {
		storage = new Storage();
	}
	
	/**
	 * statisk metode til oprettelse af service
	 * @return
	 */
	public static Service hentService() {
		if (service == null) {
			service = new Service();
		}
		return service;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param salg
	 * @return
	 */
	public Rabat opretPrisRabatTilSalg(double pris, Salg salg) {
		Rabat r = new PrisRabat(pris);
		salg.tilfoejRabat(r);
		return r;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param salg
	 * @return
	 */
	public Rabat opretProcentvisRabatTilSalg(double pris, Salg salg) {
		Rabat r = new ProcentvisRabat(pris);
		salg.tilfoejRabat(r);
		return r;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param produkt
	 * @param prisliste
	 * @param startDato
	 * @param slutDato
	 * @return
	 */
	public Rabat opretPrisRabatTilProdukt(double pris, Produkt produkt, Prisliste prisliste, 
			LocalDate startDato, LocalDate slutDato) {		
		Rabat r = new PrisRabat(pris, slutDato, slutDato, prisliste);
		produkt.tilfoejRabat(r);
		return r;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param produkt
	 * @param prisliste
	 * @param startDato
	 * @param slutDato
	 * @return
	 */
	public Rabat opretProcentvisRabatTilProdukt(double pris, Produkt produkt, Prisliste prisliste, LocalDate startDato, LocalDate slutDato) {
		Rabat r = new ProcentvisRabat(pris, slutDato, slutDato, prisliste);
		produkt.tilfoejRabat(r);	
		return r; 
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param udlejningsprodukt
	 * @param prisliste
	 * @param startDato
	 * @param slutDato
	 * @return
	 */
	public Rabat opretPrisRabatTilUdlejningsprodukt(double pris, Produkt udlejningsprodukt, Prisliste prisliste, 
			LocalDate startDato, LocalDate slutDato) {		
		Rabat r = new PrisRabat(pris, slutDato, slutDato, prisliste);
		udlejningsprodukt.tilfoejRabat(r);
		return r;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param udlejningsprodukt
	 * @param prisliste
	 * @param startDato
	 * @param slutDato
	 * @return
	 */
	public Rabat opretProcentvisRabatTilUdlejningsprodukt(double pris, Produkt udlejningsprodukt, Prisliste prisliste, LocalDate startDato, LocalDate slutDato) {
		Rabat r = new ProcentvisRabat(pris, slutDato, slutDato, prisliste);
		udlejningsprodukt.tilfoejRabat(r);		
		return r; 
	}
	
	/**
	 * Metode
	 * @param prisObj
	 * @param pris
	 */
	public void redigerPris(Pris prisObj, double pris) {
		prisObj.saetPris(pris);
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param produkt
	 * @param prisliste
	 * @return
	 */
	public Pris opretPrisPaaProdukt(double pris, Produkt produkt, Prisliste prisliste) {
		Pris p = prisliste.opretPris(pris, produkt);
		return p;
	}
	
	/**
	 * Opret metode
	 * @param pris
	 * @param udlejningsprodukt
	 * @param prisliste
	 * @return
	 */
	public Pris opretPrisPaaUdlejningsprodukt(double pris, Produkt udlejningsprodukt, Prisliste prisliste) {
		Pris p = prisliste.opretPris(pris, udlejningsprodukt);
		return p;
	}
	
	/**
	 * Opret metode
	 * @param navn
	 * @return
	 */
	public Prisliste opretPrisliste(String navn) {
		Prisliste prisliste = new Prisliste(navn);
		Storage.tilfoejPrisliste(prisliste);
		return prisliste;
	}
	
	/**
	 * Opret metode
	 * @param navn
	 * @param beskrivelse
	 * @param produktgruppe
	 * @return
	 */
	public Produkt opretProdukt(String navn, String beskrivelse, Produktgruppe produktgruppe) {
		Produkt produkt = produktgruppe.opretProdukt(navn, beskrivelse, new StandardSalgsstrategi());
		return produkt;
	}
	
	public Produkt opretProdukt(String navn, String beskrivelse, Produktgruppe produktgruppe, Salgsstrategi salgsstrategi) {
		Produkt produkt = produktgruppe.opretProdukt(navn, beskrivelse, salgsstrategi);
		return produkt;
	}
	
	/**
	 * Opret metode
	 * @param navn
	 * @param beskrivelse
	 * @param produktgruppe
	 * @return
	 */
	public Produkt opretUdlejningsprodukt(String navn, String beskrivelse, Produktgruppe produktgruppe) {
		Produkt udlejningsprodukt = produktgruppe.opretProdukt(navn, beskrivelse, new Udlejningsstrategi());
		return udlejningsprodukt;
	}
	
	/**
	 * Opret metode
	 * @param navn
	 * @param beskrivelse
	 * @param produktgruppe
	 * @param stoerrelse
	 * @param indholdGruppe
	 * @return
	 */
	public ProduktPakke opretProduktPakke(String navn, String beskrivelse, Produktgruppe produktgruppe, int stoerrelse, Produktgruppe indholdGruppe) {
		ProduktPakke produktPakke = produktgruppe.opretProduktPakke(navn, beskrivelse, stoerrelse, indholdGruppe, new StandardSalgsstrategi());
		return produktPakke;
	}
	
	public Fustage opretFustage(String navn, String beskrivelse, Produktgruppe produktgruppe, double liter) {
		Fustage fustage = produktgruppe.opretFustage(navn, beskrivelse, new Udlejningsstrategi(), liter);
		return fustage;
	}
	
	public Fustage opretUdlejningFustage(String navn, String beskrivelse, Produktgruppe produktgruppe, double liter, Salgsstrategi salgsstrategi, double pant) {
		Fustage fustage = produktgruppe.opretFustage(navn, beskrivelse, salgsstrategi, liter);
		fustage.saetPant(pant);
		return fustage;
	}
	
	/**
	 * Opret metode
	 * @param navn
	 * @return
	 */
	public Produktgruppe opretProduktgruppe(String navn) {
		Produktgruppe produktgruppe = new Produktgruppe(navn);
		Storage.tilfoejProduktgruppe(produktgruppe);
		return produktgruppe;
	}
	
	public ArrayList<Produktgruppe> hentProduktgrupper() {
		return Storage.hentProduktgrupper();
	}
	
	public ArrayList<Prisliste> hentPrislister() {
		return Storage.hentPrislister();
	}
	
	/**
	 * Opret metode
	 * @return
	 */
	public Salg opretSalg() {
		Salg s = new Salg();
		Storage.tilfoejSalg(s);
		return s;
	}

	public Salg opretSalg(ArrayList<Pris> priser, ArrayList<Rabat> rabatter, ArrayList<Betalingsform> betaling) {
		Salg s = new Salg();
		Storage.tilfoejSalg(s);
		
		for (Pris pris : priser) {
			s.tilfoejPris(pris);
		}
		
		for (Rabat rabat : rabatter) {
			s.tilfoejRabat(rabat);
		}
		
		s.betal(betaling);
		
		return s;
	}
	
	/**
	 * Opret metode
	 * @return
	 */
	public Udlejning opretUdlejning() {
		Udlejning u = new Udlejning();
		Storage.tilfoejUdlejning(u);
		return u;
	}
	
	public Udlejning opretUdlejning(ArrayList<Pris> priser, ArrayList<Rabat> rabatter, ArrayList<Betalingsform> betaling) {
		Udlejning u = new Udlejning();
		Storage.tilfoejUdlejning(u);
		
		for (Pris pris : priser) {
			u.tilfoejPris(pris);
		}
		
		for (Rabat rabat : rabatter) {
			u.tilfoejRabat(rabat);
		}
		u.betal(betaling);
		return u;
	}
	
	/**
	 * Metode
	 * @param pris
	 * @param salg
	 */
	public void tilfoejPrisTilSalg(Pris pris, Salg salg) {
		salg.tilfoejPris(pris);
	}
	
	public ArrayList<Salg> hentSalg() {
		return Storage.hentSalg();
	}
	
	public ArrayList<Udlejning> hentUdlejninger() {
		return Storage.hentUdlejninger();
	}
	
	public ArrayList<Udlejning> hentUdlejninger(LocalDate dato) {
		ArrayList<Udlejning> resultat  = new ArrayList<Udlejning>();
		
		for (Udlejning udlejning : Storage.hentUdlejninger()) {
			if(udlejning.hentStartDato().equals(dato)) {
				resultat.add(udlejning);
			}
		}
		return resultat;
	}
	
	public ArrayList<Produkt> hentUdlejedeProdukter(Produktgruppe produktgruppe) {		
		return produktgruppe.hentUdlejningsprodukter();
	}
	
	/**
	 * Metode
	 * @return
	 */
	public ArrayList<Produktgruppe> hentProduktgrupperDerHarUdlejning() {
		ArrayList<Produktgruppe> produktgrupper = new ArrayList<Produktgruppe>();
		
		for (Produktgruppe produktgruppe : Storage.hentProduktgrupper()) {
			if(!produktgruppe.hentUdlejningsprodukter().isEmpty()) { //Her udelukkes muligheden for at oprette en ny produktgruppe.
				produktgrupper.add(produktgruppe);
			}
		}		
		return produktgrupper;
	}
	
	/**
	 * Metode
	 * @return
	 */
	public ArrayList<Produkt> findProdukterDerErLejetUd() {
		ArrayList<Produkt> resultat = new ArrayList<>();
		
		for (Udlejning udlejning : Storage.hentUdlejninger()) {
			for (Pris pris : udlejning.hentPriser()) {
				
				Produkt produkt = pris.hentProdukt();
				
				if(produkt.hentSalgsstrategi().hentTilstand() == TilstandEnum.UDLEJET ||produkt.hentSalgsstrategi().hentTilstand() == TilstandEnum.FORSINKET) {					
					resultat.add(produkt);
				}
			}
		}
		return resultat;
	}
	
	/**
	 * Metode
	 * @return
	 */
	public DayOfWeek hentNuvaerendeDag() {
		return LocalDate.now().getDayOfWeek();
	}
	
	public Pris opretPris(Prisliste prisliste, double pris, Produkt produkt) {
		Pris prisObjekt = prisliste.opretPris(pris, produkt);
		return prisObjekt;
		
	}

	public StandardSalgsstrategi opretStandardSalgsstrategi() {
		StandardSalgsstrategi salgsstrategi = new StandardSalgsstrategi();
		return salgsstrategi;
	}

	public Udlejningsstrategi opretUdlejningsstrategi() {
		Udlejningsstrategi strategi = new Udlejningsstrategi();
		return strategi;
	}

	public Rundvisning opretRundvisning(String navn, String beskrivelse, Produktgruppe produktgruppe,
		Salgsstrategi salgsstrategi) {
		Rundvisning rundvisning = produktgruppe.opretRundvisning(navn, beskrivelse, salgsstrategi);
		return rundvisning;
		
	}
	
	public Pris clonePris(Pris pris){
		return (Pris)pris.clone();
	}
	
	/**
	 * Init storage
	 */
	public void opretNogleObjekter() {
		//Produktgrupper
		Produktgruppe pg = new Produktgruppe("Diverse");
		Produktgruppe pg1 = new Produktgruppe("Flaske");
		Produktgruppe pg2 = new Produktgruppe("Fadøl, 40 cl");
		Produktgruppe pg3 = new Produktgruppe("Spiritus");
		Produktgruppe pg4 = new Produktgruppe("Fustage");
		Produktgruppe pg5 = new Produktgruppe("Kulsyre");
		Produktgruppe pg6 = new Produktgruppe("Malt");
		Produktgruppe pg7 = new Produktgruppe("Beklædning");
		Produktgruppe pg8 = new Produktgruppe("Anlæg");
		Produktgruppe pg9 = new Produktgruppe("Glas");
		Produktgruppe pg10 = new Produktgruppe("Sampakninger");
		Produktgruppe pg11 = new Produktgruppe("Rundvisning");
		
		Storage.tilfoejProduktgruppe(pg);
		Storage.tilfoejProduktgruppe(pg1);
		Storage.tilfoejProduktgruppe(pg2);
		Storage.tilfoejProduktgruppe(pg3);
		Storage.tilfoejProduktgruppe(pg4);
		Storage.tilfoejProduktgruppe(pg5);
		Storage.tilfoejProduktgruppe(pg6);
		Storage.tilfoejProduktgruppe(pg7);
		Storage.tilfoejProduktgruppe(pg8);
		Storage.tilfoejProduktgruppe(pg9);
		Storage.tilfoejProduktgruppe(pg10);
		Storage.tilfoejProduktgruppe(pg11);
		
		//Produkter
		Produkt p = opretProdukt("Klippekort", "4 klip", pg);
		Produkt p1 = opretProdukt("Klosterbryg", null, pg1);
		Produkt p2 = opretProdukt("Sweet Georgia Brown", null, pg1);
		Produkt p3 = opretProdukt("Extra Pilsner", null, pg1);
		Produkt p4 = opretProdukt("Celebration", null, pg1);
		Produkt p5 = opretProdukt("Blondie", null, pg1);
		Produkt p6 = opretProdukt("Forårsbryg", null, pg1);
		Produkt p7 = opretProdukt("India Pale Ale", null, pg1);
		Produkt p8 = opretProdukt("Julebryg", null, pg1);
		Produkt p9 = opretProdukt("Juletønden", null, pg1);
		Produkt p10 = opretProdukt("Old Strong Ale", null, pg1);
		Produkt p11 = opretProdukt("Fregatten Jylland", null, pg1);
		Produkt p12 = opretProdukt("Imperial Stout", null, pg1);
		Produkt p13 = opretProdukt("Tribute", null, pg1);
		Produkt p14 = opretProdukt("Black Monster", null, pg1);
		
		Produkt p15 = opretProdukt("Klosterbryg", null, pg2);
		Produkt p16 = opretProdukt("Jazz Classic", null, pg2);
		Produkt p17 = opretProdukt("Extra Pilsner", null, pg2);
		Produkt p18 = opretProdukt("Celebration", null, pg2);
		Produkt p19 = opretProdukt("Blondie", null, pg2);
		Produkt p20 = opretProdukt("Forårsbryg", null, pg2);
		Produkt p21 = opretProdukt("India Pale Ale", null, pg2);
		Produkt p22 = opretProdukt("Julebryg", null, pg2);
		Produkt p23 = opretProdukt("Imperial Stout", null, pg2);
		Produkt p24 = opretProdukt("Special", null, pg2);
		Produkt p25 = opretProdukt("Æblebrus", null, pg2);
		Produkt p26 = opretProdukt("Chips", null, pg2);
		Produkt p27 = opretProdukt("Peanuts", null, pg2);
		Produkt p28 = opretProdukt("Cola", null, pg2);
		Produkt p29 = opretProdukt("Nikoline", null, pg2);
		Produkt p30 = opretProdukt("7-Up", null, pg2);
		Produkt p31 = opretProdukt("Vand", null, pg2);
		
		Produkt p32 = opretProdukt("Spirit of Aarhus", null, pg3);
		Produkt p33 = opretProdukt("SOA med pind", null, pg3);
		Produkt p34 = opretProdukt("Whisky", null, pg3);
		Produkt p35 = opretProdukt("Liquor of Aarhus", null, pg3);
		
		// Fustager
		Produkt p36 = opretFustage("Klosterbryg", "20 liter", pg4, 20);
		Produkt p37 = opretFustage("Jazz Classic", "25 liter", pg4, 25);
		Produkt p38 = opretFustage("Extra Pilsner", "25 liter", pg4, 25);
		Produkt p39 = opretFustage("Celebration", "20 liter", pg4, 20);
		Produkt p40 = opretFustage("Blondie", "25 liter", pg4, 25);
		Produkt p41 = opretFustage("Forårsbryg", "20 liter", pg4, 20);
		Produkt p42 = opretFustage("India Pale Ale", "20 liter", pg4, 20);
		Produkt p43 = opretFustage("Julebryg", "20 liter", pg4, 20);
		Produkt p44 = opretFustage("Imperial Stout", "20 liter", pg4, 20);
		Produkt p45 = opretProdukt("Pant", null, pg4);
		
		Produkt p46 = opretProdukt("6 kg", null, pg5);
		Produkt p47 = opretProdukt("Pant", null, pg5);
		Produkt p48 = opretProdukt("4 kg", null, pg5);
		Produkt p49 = opretProdukt("10 kg", null, pg5);
		
		Produkt p50 = opretProdukt("25 kg sæk", null, pg6);
		
		Produkt p51 = opretProdukt("T-shirt", null, pg7);
		Produkt p52 = opretProdukt("Polo", null, pg7);
		Produkt p53 = opretProdukt("Cap", null, pg7);
		
		Produkt p54 = opretProdukt("1 hane", null, pg8);
		Produkt p55 = opretProdukt("2 haner", null, pg8);
		Produkt p56 = opretProdukt("Bar med flere haner", null, pg8);
		Produkt p57 = opretProdukt("Levering", null, pg8);
		Produkt p58 = opretProdukt("Krus", null, pg8);
		
		Produkt p59 = opretProdukt("Uanset størrelse", null, pg9);
		
		Produkt p60 = opretProduktPakke("Gaveæske", "2 øl, 2 glas", pg10, 2, pg1);	// Vælg således at glas er valgt
		Produkt p61 = opretProduktPakke("Gaveæske", "4 øl", pg10, 2, pg1);
		Produkt p62 = opretProduktPakke("Trækasse", "6 øl", pg10, 6, pg1);
		Produkt p63 = opretProduktPakke("Gavekurv", "6 øl, 2 glas", pg10, 6, pg1);
		Produkt p64 = opretProduktPakke("Trækasse", "6 øl, 6 glas", pg10, 6, pg1);
		Produkt p65 = opretProduktPakke("Trækasse", "12 øl", pg10, 12, pg1);
		Produkt p66 = opretProduktPakke("Papkasse", "12 øl", pg10, 12, pg1);
		
		Produkt p67 = opretRundvisning("Pr person", "dag", pg11, opretStandardSalgsstrategi());
		Produkt p68 = opretRundvisning("Pr person", "aften", pg11, opretStandardSalgsstrategi());
		Produkt p69 = opretRundvisning("Pr person", "dag-studierabat", pg11, opretStandardSalgsstrategi());
		Produkt p70 = opretRundvisning("Pr person", "aften-studierabat", pg11, opretStandardSalgsstrategi());
	
		//Prislister
		Prisliste pl1 =  new Prisliste("Butik");
		Prisliste pl2 = new Prisliste("Fredagsbar");
		
		Storage.tilfoejPrisliste(pl1);
		Storage.tilfoejPrisliste(pl2);
		
		// Priser
		Pris pris = this.opretPrisPaaProdukt(100, p, pl1);
		Pris pris1 = this.opretPrisPaaProdukt(100, p, pl2);
		Pris pris2 = this.opretPrisPaaProdukt(36, p1, pl1);
		Pris pris3 = this.opretPrisPaaProdukt(50, p1, pl2);
		Pris pris4 = this.opretPrisPaaProdukt(36, p2, pl1);
		Pris pris5 = this.opretPrisPaaProdukt(50, p2, pl2);
		Pris pris6 = this.opretPrisPaaProdukt(36, p3, pl1);
		Pris pris7= this.opretPrisPaaProdukt(50, p3, pl2);
		Pris pris8 = this.opretPrisPaaProdukt(36, p4, pl1);
		Pris pris9 = this.opretPrisPaaProdukt(50, p4, pl2);
		Pris pris10 = this.opretPrisPaaProdukt(36, p5, pl1);
		Pris pris11 = this.opretPrisPaaProdukt(50, p5, pl2);
		Pris pris12 = this.opretPrisPaaProdukt(36, p6, pl1);
		Pris pris13 = this.opretPrisPaaProdukt(50, p6, pl2);
		Pris pris14 = this.opretPrisPaaProdukt(36, p7, pl1);
		Pris pris15 = this.opretPrisPaaProdukt(50, p7, pl2);
		Pris pris16 = this.opretPrisPaaProdukt(36, p8, pl1);
		Pris pris17 = this.opretPrisPaaProdukt(50, p8, pl2);
		Pris pris18 = this.opretPrisPaaProdukt(36, p9, pl1);
		Pris pris19 = this.opretPrisPaaProdukt(50, p9, pl2);
		Pris pris20 = this.opretPrisPaaProdukt(36, p10, pl1);
		Pris pris21 = this.opretPrisPaaProdukt(50, p10, pl2);
		Pris pris22 = this.opretPrisPaaProdukt(36, p11, pl1);
		Pris pris23 = this.opretPrisPaaProdukt(50, p11, pl2);
		Pris pris24 = this.opretPrisPaaProdukt(36, p12, pl1);
		Pris pris25 = this.opretPrisPaaProdukt(50, p12, pl2);
		Pris pris26 = this.opretPrisPaaProdukt(36, p13, pl1);
		Pris pris27 = this.opretPrisPaaProdukt(50, p13, pl2);
		Pris pris28 = this.opretPrisPaaProdukt(50, p14, pl1);
		Pris pris29 = this.opretPrisPaaProdukt(50, p14, pl2);
		
		Pris pris30 = this.opretPrisPaaProdukt(30, p15, pl2);
		Pris pris31 = this.opretPrisPaaProdukt(30, p16, pl2);
		Pris pris32 = this.opretPrisPaaProdukt(30, p17, pl2);
		Pris pris33 = this.opretPrisPaaProdukt(30, p18, pl2);
		Pris pris34 = this.opretPrisPaaProdukt(30, p19, pl2);
		Pris pris35 = this.opretPrisPaaProdukt(30, p20, pl2);
		Pris pris36 = this.opretPrisPaaProdukt(30, p21, pl2);
		Pris pris37 = this.opretPrisPaaProdukt(30, p22, pl2);
		Pris pris38 = this.opretPrisPaaProdukt(30, p23, pl2);
		Pris pris39 = this.opretPrisPaaProdukt(30, p24, pl2);
		
		Pris pris40 = this.opretPrisPaaProdukt(15, p25, pl2);
		Pris pris41 = this.opretPrisPaaProdukt(10, p26, pl2);
		Pris pris42 = this.opretPrisPaaProdukt(10, p27, pl2);
		Pris pris43 = this.opretPrisPaaProdukt(15, p28, pl2);
		Pris pris44 = this.opretPrisPaaProdukt(15, p29, pl2);
		Pris pris45 = this.opretPrisPaaProdukt(15, p30, pl2);
		Pris pris46 = this.opretPrisPaaProdukt(10, p31, pl2);
		
		Pris pris47 = this.opretPrisPaaProdukt(300, p32, pl1);
		Pris pris48 = this.opretPrisPaaProdukt(300, p32, pl2);
		Pris pris49 = this.opretPrisPaaProdukt(350, p33, pl1);
		Pris pris50 = this.opretPrisPaaProdukt(350, p33, pl2);
		Pris pris51 = this.opretPrisPaaProdukt(500, p34, pl1);
		Pris pris52 = this.opretPrisPaaProdukt(500, p34, pl2);
		Pris pris53 = this.opretPrisPaaProdukt(175, p35, pl1);
		Pris pris54 = this.opretPrisPaaProdukt(175, p35, pl2);
		
		Pris pris55 = this.opretPrisPaaProdukt(775, p36, pl1);
		Pris pris56 = this.opretPrisPaaProdukt(625, p37, pl1);
		Pris pris57 = this.opretPrisPaaProdukt(575, p38, pl1);
		Pris pris58 = this.opretPrisPaaProdukt(775, p39, pl1);
		Pris pris59 = this.opretPrisPaaProdukt(700, p40, pl1);
		Pris pris60 = this.opretPrisPaaProdukt(775, p41, pl1);
		Pris pris61 = this.opretPrisPaaProdukt(775, p42, pl1);
		Pris pris62 = this.opretPrisPaaProdukt(775, p43, pl1);
		Pris pris63 = this.opretPrisPaaProdukt(775, p44, pl1);
		Pris pris64 = this.opretPrisPaaProdukt(200, p45, pl1);
	
		Pris pris65 = this.opretPrisPaaProdukt(400, p46, pl1);
		Pris pris66 = this.opretPrisPaaProdukt(400, p46, pl2);
		Pris pris67 = this.opretPrisPaaProdukt(1000, p47, pl1);
		Pris pris68 = this.opretPrisPaaProdukt(1000, p47, pl2);
		
		Pris pris69 = this.opretPrisPaaProdukt(0, p48, pl1);
		Pris pris70 = this.opretPrisPaaProdukt(0, p49, pl1);
		
		Pris pris71 = this.opretPrisPaaProdukt(300, p50, pl1);
		
		Pris pris72 = this.opretPrisPaaProdukt(70, p51, pl1);
		Pris pris73 = this.opretPrisPaaProdukt(70, p51, pl2);
		Pris pris74 = this.opretPrisPaaProdukt(100, p52, pl1);
		Pris pris75 = this.opretPrisPaaProdukt(100, p52, pl2);
		Pris pris76 = this.opretPrisPaaProdukt(30, p53, pl1);
		Pris pris77 = this.opretPrisPaaProdukt(30, p53, pl2);
		
		Pris pris78 = this.opretPrisPaaProdukt(250, p54, pl1);
		Pris pris79 = this.opretPrisPaaProdukt(400, p55, pl1);
		Pris pris80 = this.opretPrisPaaProdukt(500, p56, pl1);
		Pris pris81 = this.opretPrisPaaProdukt(500, p57, pl1);
		Pris pris82 = this.opretPrisPaaProdukt(60, p58, pl1);
		
		Pris pris83 = this.opretPrisPaaProdukt(15, p59, pl1);
		
		Pris pris84 = this.opretPrisPaaProdukt(100, p60, pl1);
		Pris pris85 = this.opretPrisPaaProdukt(100, p60, pl2);
		Pris pris86 = this.opretPrisPaaProdukt(130, p61, pl1);
		Pris pris87 = this.opretPrisPaaProdukt(130, p61, pl2);
		Pris pris88 = this.opretPrisPaaProdukt(240, p62, pl1);
		Pris pris89 = this.opretPrisPaaProdukt(240, p62, pl2);
		Pris pris90 = this.opretPrisPaaProdukt(250, p63, pl1);
		Pris pris91 = this.opretPrisPaaProdukt(250, p63, pl2);
		Pris pris92 = this.opretPrisPaaProdukt(290, p64, pl1);
		Pris pris93 = this.opretPrisPaaProdukt(290, p64, pl2);
		Pris pris94 = this.opretPrisPaaProdukt(390, p65, pl1);
		Pris pris95 = this.opretPrisPaaProdukt(390, p65, pl2);
		Pris pris96 = this.opretPrisPaaProdukt(360, p66, pl1);
		Pris pris97 = this.opretPrisPaaProdukt(360, p66, pl2);
		
		Pris pris98 = this.opretPrisPaaProdukt(100, p67, pl1);
		Pris pris99 = this.opretPrisPaaProdukt(120, p68, pl1);
		Pris pris100 = this.opretPrisPaaProdukt(80, p69, pl1);
		Pris pris101 = this.opretPrisPaaProdukt(96, p70, pl1);	
		
		//Udlejningsprodukter
		Produkt p71 = opretUdlejningsprodukt("1 hane", null, pg8);
		Produkt p72 = opretUdlejningsprodukt("2 haner", null, pg8);
		Produkt p73 = opretUdlejningsprodukt("Bar med flere haner", null, pg8);
		Produkt p74 = opretUdlejningsprodukt("Levering", null, pg8);
		Produkt p75 = opretUdlejningsprodukt("Krus", null, pg8);
		
		this.opretPrisPaaProdukt(250, p71, pl1);
		this.opretPrisPaaProdukt(400, p72, pl1);
		this.opretPrisPaaProdukt(500, p73, pl1);
		this.opretPrisPaaProdukt(500, p74, pl1);
		this.opretPrisPaaProdukt(60, p75, pl1);
		
		Produkt p76 = opretUdlejningFustage("Klosterbryg", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		Produkt p77 = opretUdlejningFustage("Jazz Classic", "25 liter", pg4 , 25, new Udlejningsstrategi(), 25);
		Produkt p78 = opretUdlejningFustage("Extra Pilsner", "25 liter", pg4, 25, new Udlejningsstrategi(), 25);
		Produkt p79 = opretUdlejningFustage("Celebration", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		Produkt p80 = opretUdlejningFustage("Blondie", "25 liter", pg4, 25, new Udlejningsstrategi(), 25);
		Produkt p81 = opretUdlejningFustage("Forårsbryg", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		Produkt p82 = opretUdlejningFustage("India Pale Ale", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		Produkt p83 = opretUdlejningFustage("Julebryg", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		Produkt p84 = opretUdlejningFustage("Imperial Stout", "20 liter", pg4, 20, new Udlejningsstrategi(), 20);
		
		this.opretPrisPaaProdukt(775, p76, pl1);
		this.opretPrisPaaProdukt(625, p77, pl1);
		this.opretPrisPaaProdukt(575, p78, pl1);
		this.opretPrisPaaProdukt(775, p79, pl1);
		this.opretPrisPaaProdukt(700, p80, pl1);
		this.opretPrisPaaProdukt(775, p81, pl1);
		this.opretPrisPaaProdukt(775, p82, pl1);
		this.opretPrisPaaProdukt(775, p83, pl1);
		this.opretPrisPaaProdukt(775, p84, pl1);
		//this.opretPrisPaaProdukt(200, p85, pl1);
		
		/*Udlejning udlejning = opretUdlejning();
		udlejning.saetStartDato(LocalDate.now());
		udlejning.saetSlutDato(LocalDate.now().plusDays(2));
		
		// Produkter
		udlejning.tilfoejPris(pris);
		
		Betalingsform betalingsform = new Betalingsform(BetalingsformEnum.KONTANT, pris.hentPris());
		ArrayList<Betalingsform> betalingsformer = new ArrayList<Betalingsform>();
		betalingsformer.add(betalingsform);
		
		udlejning.betal(betalingsformer);
		*/
		
		//Salg
		//TODO
		
		
		ArrayList<Pris> priser = new ArrayList<Pris>();
		ArrayList<Rabat> rabatter = new ArrayList<Rabat>();
		ArrayList<Betalingsform> betaling = new ArrayList<Betalingsform>();
		double sum = 0;
		
		priser.clear();
		rabatter.clear();
		betaling.clear();
		
		priser.add(pris1);
		priser.add(pris1);
		
		sum = 0;
		for (Pris pr : priser) {
			sum += pr.beregnSalgsPris();
		}
		
		betaling.add(new Betalingsform(BetalingsformEnum.DANKORT, sum));
		
		this.opretSalg(priser, rabatter, betaling);
		
		/////
		priser.clear();
		rabatter.clear();
		betaling.clear();
		
		priser.add(pris5);
		priser.add(pris6);
		priser.add(pris12);
		
		sum = 0;
		for (Pris pr : priser) {
			sum += pr.beregnSalgsPris();
		}
		
		betaling.add(new Betalingsform(BetalingsformEnum.MOBILEPAY, sum));
		
		this.opretSalg(priser, rabatter, betaling);
		
		/////
		priser.clear();
		rabatter.clear();
		betaling.clear();
		
		priser.add(pris20);
		
		sum = 0;
		for (Pris pr : priser) {
			sum += pr.beregnSalgsPris();
		}
		
		betaling.add(new Betalingsform(BetalingsformEnum.KONTANT, sum));
		
		this.opretSalg(priser, rabatter, betaling);
		
		/////
		
		priser.clear();
		rabatter.clear();
		betaling.clear();
		
		priser.add(pris70);
		priser.add(pris100);
		priser.add(pris55);
		priser.add(pris44);
		priser.add(pris33);
	
		rabatter.add(new PrisRabat((double) 100));
		
		betaling.add(new Betalingsform(BetalingsformEnum.KONTANT, 800));
		
		this.opretSalg(priser, rabatter, betaling);
		
		
		/////
		priser.clear();
		rabatter.clear();
		betaling.clear();
		
		priser.add(pris74);
		priser.add(pris98);
		priser.add(pris10);
		priser.add(pris12);
		priser.add(pris4);
	
		rabatter.add(new ProcentvisRabat((double) 50));
		
		sum = 0;
		for (Pris pr : priser) {
			sum += pr.beregnSalgsPris();
		}
		
		betaling.add(new Betalingsform(BetalingsformEnum.KONTANT, 154));
		
		this.opretSalg(priser, rabatter, betaling);
		
		
		//Udlejninger
		//TODO
	}
}
