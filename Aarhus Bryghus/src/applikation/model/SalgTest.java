package applikation.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SalgTest {
	
	@Before
	public void setUp() throws Exception { 
		
		// Vi kan ikke kalde salg her, sådan som Salg() er kodet nu.	
		// Denne metode kaldes hver gang en af de nedenstående metoder kaldes.
		// Salg får nyt id hver gang denne metode kaldes.
	}
	
	@Test
	public final void testSalg() { 
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		
		// act
		int id = salg.hentID();
		ArrayList<Betalingsform> betalingsformer = salg.hentBetalingsformer();
		ArrayList<Pris> priser = salg.hentPriser();
		ArrayList<Rabat> rabatter = salg.hentRabatter();
		
		// assert
		assertNotNull(salg);
		assertEquals(1, id);
		assertNotNull(betalingsformer);
		assertNotNull(priser);
		assertNotNull(rabatter);
		
		//---------TC 2------------------
		
		// arrange
		Salg salg_2 = new Salg();
		Salg salg_3 = new Salg();
		Salg salg_4 = new Salg();
		Salg salg_5 = new Salg();

		// act
		int id_1 = id;
		int id_2 = salg_2.hentID();							
		System.out.println("SalgTest: l. 52, id = " + id_2);
		int id_3 = salg_3.hentID();
		System.out.println("SalgTest: l. 54, id = " + id_3);
		int id_4 = salg_4.hentID();
		System.out.println("SalgTest: l. 56, id = " + id_4);
		int id_5 = salg_5.hentID();
		System.out.println("SalgTest: l. 58, id = " + id_5);
	
		// assert
		assertEquals(1, id_1); // id = 1 - Korrekt
		assertEquals(2, id_2); // id = 1 - forkert
		assertEquals(3, id_3); // id = 1 - forkert
		assertEquals(4, id_4); // id = 1 - forkert
		assertEquals(5, id_5); // id = 1 - forkert
	}

	@Test
	public final void testTilfoejBetalingsform() {
		
		//---------TC 1------------------

		// arrange
		Salg salg = new Salg();
		Betalingsform betalingsform = new Betalingsform(BetalingsformEnum.KONTANT, 100);
				
		// act
		salg.tilfoejBetalingsform(betalingsform);
		
		// assert
		assertNotNull(betalingsform);
		assertTrue(salg.hentBetalingsformer().contains(betalingsform));
	}

	@Test
	public final void testHentBetalingsform() {

		//---------TC 1------------------

		// arrange
		Salg salg = new Salg();
		Betalingsform betalingsform = new Betalingsform(BetalingsformEnum.KONTANT, 100);
		Betalingsform betalingsform_2 = new Betalingsform(BetalingsformEnum.KONTANT, 385);
		Betalingsform betalingsform_3 = new Betalingsform(BetalingsformEnum.MOBILEPAY, 127);
		Betalingsform betalingsform_4 = new Betalingsform(BetalingsformEnum.REGNING, 24);
		
		salg.tilfoejBetalingsform(betalingsform);
		salg.tilfoejBetalingsform(betalingsform_2);
		salg.tilfoejBetalingsform(betalingsform_3);
		salg.tilfoejBetalingsform(betalingsform_4);
		
		ArrayList<Betalingsform> betalingsformer = new ArrayList<Betalingsform>();
		betalingsformer.add(betalingsform);
		betalingsformer.add(betalingsform_2);
		betalingsformer.add(betalingsform_3);
		betalingsformer.add(betalingsform_4);
		
		// act
		ArrayList<Betalingsform> resultat = salg.hentBetalingsformer();
		
		// assert	
		assertTrue(resultat.equals(betalingsformer));
	}

	@Test
	public final void testTilfoejPris() {
		
		//---------TC 1------------------

		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Flasker");
		Produkt produkt = produktgruppe.opretProdukt("Klosterbryg", null, new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Butik");
		Pris pris = new Pris(100, produkt, prisliste);
		
		// act
		salg.tilfoejPris(pris);
		
		//assert
		assertNotNull(pris);
		assertTrue(salg.hentPriser().contains(pris));
	}

	@Test
	public final void testHentPriser() {

		//---------TC 1------------------

		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Flasker");
		Produkt produkt = produktgruppe.opretProdukt("Klosterbryg", null, new StandardSalgsstrategi());			
		Produkt produkt_2 = produktgruppe.opretProdukt("Imperial Stout", null, new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Butik");
		Prisliste prisliste_2 = new Prisliste("Fredagsbar");
		Pris pris = new Pris(20, produkt, prisliste);
		Pris pris_2 = new Pris(34, produkt, prisliste_2);
		Pris pris_3 = new Pris(120, produkt_2, prisliste);
		Pris pris_4 = new Pris(144, produkt_2, prisliste_2);
		
		salg.tilfoejPris(pris);
		salg.tilfoejPris(pris_2);
		salg.tilfoejPris(pris_3);
		salg.tilfoejPris(pris_4);
		
		ArrayList<Pris> priser = new ArrayList<>();
		priser.add(pris);
		priser.add(pris_2);
		priser.add(pris_3);
		priser.add(pris_4);

		// act
		ArrayList<Pris> resultat = salg.hentPriser();	
		
		// assert
		assertTrue(resultat.equals(priser));		
	}

	@Test
	public final void testTilfoejRabat() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Rabat rabat = new PrisRabat(100);
		
		// act
		salg.tilfoejRabat(rabat);
		
		// assert
		assertNotNull(rabat);
		assertTrue(salg.hentRabatter().contains(rabat));
		
		//---------TC 2------------------
		
		// arrange
		Rabat rabat_2 = new ProcentvisRabat(60);
		
		// act
		salg.tilfoejRabat(rabat_2);
		
		// assert
		assertNotNull(rabat_2);
		assertTrue(salg.hentRabatter().contains(rabat_2));
	}

	@Test
	public final void testHentRabatter() {

		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Rabat rabat = new PrisRabat(20);
		Rabat rabat_2 = new ProcentvisRabat(15);
		Rabat rabat_3 = new ProcentvisRabat(5);
		Rabat rabat_4 = new PrisRabat(52);
		
		salg.tilfoejRabat(rabat);
		salg.tilfoejRabat(rabat_2);
		salg.tilfoejRabat(rabat_3);
		salg.tilfoejRabat(rabat_4);
		
		ArrayList<Rabat> rabatter = new ArrayList<>();
		rabatter.add(rabat);
		rabatter.add(rabat_2);
		rabatter.add(rabat_3);
		rabatter.add(rabat_4);
		
		// act
		ArrayList<Rabat> resultat = salg.hentRabatter();
		
		// assert
		assertTrue(resultat.equals(rabatter));
	}

	@Test
	public final void testHentBetalt() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		
		// act
		boolean betalt = salg.hentBetalt();
		
		// assert
		assertEquals(false, betalt);
	}

	@Test
	public final void testHentKoebsTidspunkt() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		ArrayList<Betalingsform> betalingsformer = new ArrayList<>();
		
		// act
		salg.betal(betalingsformer);
		LocalDateTime koebsTidspunkt = LocalDateTime.now().withNano(0);
		LocalDateTime resultat = salg.hentKoebsTidspunkt().withNano(0);
		
		// assert
		assertEquals(resultat, koebsTidspunkt);
	}

	@Test
	public final void testHentId() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		
		// act
		int id = salg.hentID();
		int resultat = 1;
		
		// assert
		assertEquals(resultat, id);
	}

	@Test
	public final void testKanBetales() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Fadøl");
		Produkt produkt = produktgruppe.opretProdukt("Celebration", "60 cl", new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Baren");
		Pris pris = new Pris(100, produkt, prisliste);
		Rabat rabat = new PrisRabat(10);
		salg.tilfoejPris(pris);
		salg.tilfoejRabat(rabat);
		ArrayList<Betalingsform> betalinger = new ArrayList<Betalingsform>();
				
		// act
		Betalingsform betaling = new Betalingsform(BetalingsformEnum.DANKORT, 90);
		betalinger.add(betaling);
		
		boolean test = salg.kanBetales(betalinger);
		boolean resultat = true;
				
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling));
		assertEquals(resultat, test);	
		
		//---------TC 2------------------
		
		// arrange 
		betalinger.clear();
		
		// act
		Betalingsform betaling_2 = new Betalingsform(BetalingsformEnum.KONTANT, 70);
		betalinger.add(betaling_2);
		
		boolean test_2 = salg.kanBetales(betalinger);
		boolean resultat_2 = false;
		
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling_2));
		assertEquals(resultat_2, test_2);
		
		//---------TC 3------------------
		
		// arrange 
		betalinger.clear();
		
		// act
		Betalingsform betaling_3 = new Betalingsform(BetalingsformEnum.DANKORT, 110);
		betalinger.add(betaling_3);
		
		boolean test_3 = salg.kanBetales(betalinger);
		boolean resultat_3 = false;
		
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling_3));
		assertEquals(resultat_3, test_3);
	}

	@Test
	public final void testBetal() {

		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Fustager");
		Produkt produkt = produktgruppe.opretProdukt("Forårsbryg", "20 liter", new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Butik");
		Pris pris = new Pris(350, produkt, prisliste);
		Rabat rabat = new ProcentvisRabat(20);
		salg.tilfoejPris(pris);
		salg.tilfoejRabat(rabat);
		ArrayList<Betalingsform> betalinger = new ArrayList<Betalingsform>();
		
		// act
		Betalingsform betaling = new Betalingsform(BetalingsformEnum.MOBILEPAY, 280); 
		betalinger.add(betaling);
		salg.betal(betalinger);

		boolean betalt = salg.hentBetalt();
		boolean resultat = true;
		LocalDateTime koebsTidspunkt = salg.hentKoebsTidspunkt().withNano(0);
		LocalDateTime test = LocalDateTime.now().withNano(0);
		
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling));
		assertEquals(resultat, betalt);
		assertEquals(test, koebsTidspunkt);
		
		//---------TC 2------------------
		
		// arrange
		betalinger.clear();
		Salg salg2 = new Salg();
		salg2.tilfoejPris(pris);
		salg2.tilfoejRabat(rabat);
		
		// act
		Betalingsform betaling_2 = new Betalingsform(BetalingsformEnum.DANKORT, 170);
		betalinger.add(betaling_2);
		salg.betal(betalinger);
		
		boolean betalt_2 = salg2.hentBetalt();
		boolean resultat_2 = false;
		
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling_2));
		assertEquals(resultat_2, betalt_2);
		
		//---------TC 3------------------
		
		// arrange
		betalinger.clear();
		Salg salg3 = new Salg();
		salg3.tilfoejPris(pris);
		salg3.tilfoejRabat(rabat);
		
		// act
		Betalingsform betaling_3 = new Betalingsform(BetalingsformEnum.KONTANT, 310);
		betalinger.add(betaling_3);
		salg3.betal(betalinger);
		
		boolean betalt_3 = salg3.hentBetalt();
		boolean resultat_3 = false;
		
		// assert
		assertNotNull(betalinger);
		assertTrue(betalinger.contains(betaling_3));
		assertEquals(resultat_3, betalt_3);
	}

	@Test
	public final void testSamletPris() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Flaske");
		Produkt produkt = produktgruppe.opretProdukt("Blondie", null , new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Butik");
		Prisliste prisliste_2 = new Prisliste("Fredagsbar");
		Pris pris = new Pris(150, produkt, prisliste);
		Pris pris_2 = new Pris(25, produkt, prisliste_2);
		salg.tilfoejPris(pris);
		salg.tilfoejPris(pris_2);
		
		// act
		double sum = salg.samletPris();
		double resultat = 175;
		
		// assert
		assertEquals(resultat, sum, 0.0);
		
		//---------TC 2------------------
		
		// arrange
		Rabat rabat = new PrisRabat(10);
		salg.tilfoejRabat(rabat);
		
		// act
		double sum_2 = salg.samletPris();
		double resultat_2 = 165;
		
		// assert
		assertEquals(resultat_2, sum_2, 0.0);
		
		//---------TC 3------------------
		
		// arrange
		Rabat rabat_2 = new ProcentvisRabat(15);
		salg.tilfoejRabat(rabat_2);
		
		// act
		double sum_3 = salg.samletPris();
		double resultat_3 = 140.25;
		
		// assert
		assertEquals(resultat_3, sum_3, 0.0);
	}

	@Test
	public final void testHentKunde() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Kunde kunde = new Kunde("Tim", "Timmy@gmail.com", 75301041);
		salg.seatKunde(kunde);
		
		// assert
		assertTrue(salg.hentKunde().equals(kunde));
	}

	@Test
	public final void testSaetKunde() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Kunde kunde = new Kunde("Kenni", "Kenni@gmail.com", 41103075);
		
		// act
		salg.seatKunde(kunde);
		
		// assert
		assertNotNull(kunde);
		assertTrue(salg.hentKunde().equals(kunde));
	}

	@Test
	public final void testPrisDerSkalBetales() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Spiritus");
		Produkt produkt = produktgruppe.opretProdukt("Spirit of Aarhus", null, new StandardSalgsstrategi());
		Produkt produkt_2 = produktgruppe.opretProdukt("Whisky", null, new StandardSalgsstrategi());
		Produkt produkt_3 = produktgruppe.opretProdukt("Liqour of Aarhus", null, new StandardSalgsstrategi());; 
		Prisliste prisliste = new Prisliste("Butik");
		Pris pris = new Pris(300, produkt, prisliste);
		Pris pris_2 = new Pris(500, produkt_2, prisliste);
		Pris pris_3 = new Pris(175, produkt_3, prisliste);
		Rabat rabat = new PrisRabat(150);
		salg.tilfoejPris(pris);
		salg.tilfoejPris(pris_2);
		salg.tilfoejPris(pris_3);
		salg.tilfoejRabat(rabat);
		
		// act
		double sum = salg.samletPris();
		double resultat = 825;
		
		// assert
		assertEquals(resultat, sum, 0.0);	
	}
}
