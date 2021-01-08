package applikation.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RabatTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testBeregnRabatPris() {
		
		//---------TC 1------------------
		
		// arrange
		Salg salg = new Salg();
		Produktgruppe produktgruppe = new Produktgruppe("Beklædning");
		Produkt produkt = produktgruppe.opretProdukt("Polo", null, new StandardSalgsstrategi());
		Prisliste prisliste = new Prisliste("Fredagsbar");
		Pris pris = new Pris(100, produkt, prisliste);
		salg.tilfoejPris(pris);
		Rabat rabat = new ProcentvisRabat(50);
		salg.tilfoejRabat(rabat);
		
		// act
		double rabatPris = rabat.hentRabatPris(pris.hentPris());	
		double resultat = 50;
		
		// assert
		assertEquals(resultat, rabatPris, 0.0);
		
		//---------TC 2------------------
		
		// arrange
		Salg salg_2 = new Salg();
		Produktgruppe produktgruppe_2 = new Produktgruppe("Flaske");
		Produkt produkt_2 = produktgruppe_2.opretProdukt("Celebration", null, new StandardSalgsstrategi());
		Pris pris_2 = new Pris(50, produkt_2, prisliste);
		salg_2.tilfoejPris(pris_2);
		salg_2.tilfoejRabat(rabat);
		
		// act
		double rabatPris_2 = rabat.hentRabatPris(pris_2.hentPris());
		double resultat_2 = 25;
		
		// assert
		assertEquals(resultat_2, rabatPris_2, 0.0);
		
		//---------TC 3------------------
		
		// arrange
		Salg salg_3 = new Salg();
		Produktgruppe produktgruppe_3 = new Produktgruppe("Sampakninger");
		Produkt produkt_3 = produktgruppe_3.opretProduktPakke("Gaveæske", "4 øl", 4, produktgruppe_2, new StandardSalgsstrategi());
		Pris pris_3 = new Pris(130, produkt_3, prisliste);
		salg_3.tilfoejPris(pris_3);
		salg_3.tilfoejRabat(rabat);
		
		// act
		double rabatPris_3 = rabat.hentRabatPris(pris_3.hentPris());
		double resultat_3 = 65;
		
		// assert
		assertEquals(resultat_3, rabatPris_3, 0.0);
	}
}
