package applikation.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

public class UdlejningTest {

	@Test
	public void testBetal() {
		
		// TC1 
		
		// Arrange
		Udlejning udlejning = new Udlejning();
		Prisliste prisliste = new Prisliste("Bar");
		Produktgruppe produktgruppe = new Produktgruppe("Flaske");
		Produkt produkt = produktgruppe.opretFustage("Klostebryg", null, new Udlejningsstrategi(), 20);
		Produkt produkt2 = produktgruppe.opretFustage("Extra Pilsner", null, new Udlejningsstrategi(), 15);
		Pris pris = new Pris(100, produkt, prisliste);
		Pris pris2 = new Pris(80, produkt, prisliste);
		Rabat rabat = new ProcentvisRabat(50);
		
		udlejning.tilfoejPris(pris);
		udlejning.tilfoejPris(pris2);
		udlejning.tilfoejRabat(rabat);
		udlejning.udlever();
		
		// Act
		ArrayList<Betalingsform> betaling = new ArrayList<>();
		betaling.add(new Betalingsform(BetalingsformEnum.KONTANT, 45));
		betaling.add(new Betalingsform(BetalingsformEnum.DANKORT, 45));
		
		udlejning.betal(betaling);
		boolean resultatBetalt = udlejning.hentBetalt();
		LocalDateTime koebsTidspunkt = udlejning.hentKoebsTidspunkt().withNano(0);
		TilstandEnum tilstand = udlejning.hentTilstand();
		
		
		// Assert
		boolean forventetResultat = true;
		LocalDateTime forventedTidspunkt = LocalDateTime.now().withNano(0);
		TilstandEnum forventedTilstand = TilstandEnum.HJEMME;
		
		assertEquals(forventetResultat, resultatBetalt);
		assertEquals(forventedTidspunkt, koebsTidspunkt);
		assertEquals(forventedTilstand, tilstand);
		
		// TC2
		udlejning = new Udlejning();
		prisliste = new Prisliste("Bar");
		produktgruppe = new Produktgruppe("Flaske");
		produkt = produktgruppe.opretFustage("Klostebryg", null, new Udlejningsstrategi(), 20);
		produkt2 = produktgruppe.opretFustage("Extra Pilsner", null,new Udlejningsstrategi(), 15);
		pris = new Pris(100, produkt, prisliste);
		pris2 = new Pris(80, produkt, prisliste);
		rabat = new ProcentvisRabat(50);
		
		udlejning.tilfoejPris(pris);
		udlejning.tilfoejPris(pris2);
		udlejning.tilfoejRabat(rabat);
		udlejning.udlever();
		
		
		// Act
		betaling = new ArrayList<>();
		betaling.add(new Betalingsform(BetalingsformEnum.KONTANT, 50));
		
		udlejning.betal(betaling);
		resultatBetalt = udlejning.hentBetalt();
		koebsTidspunkt = udlejning.hentKoebsTidspunkt();
		tilstand = udlejning.hentTilstand();
		
		// Assert
		forventetResultat = false;
		forventedTidspunkt = null;
		forventedTilstand = TilstandEnum.UDLEJET;
		
		assertEquals(forventetResultat, resultatBetalt);
		assertEquals(forventedTidspunkt, koebsTidspunkt);
		assertEquals(forventedTilstand, tilstand);		
		
	}
	
	@Test
	public void testUdlever() {
		// Arrange
		Udlejning udlejning = new Udlejning();
		Prisliste prisliste = new Prisliste("Bar");
		Produktgruppe produktgruppe = new Produktgruppe("Flaske");
		Produkt produkt = produktgruppe.opretFustage("Klostebryg", null, new Udlejningsstrategi(), 20);
		Produkt produkt2 = produktgruppe.opretFustage("Extra Pilsner", null, new Udlejningsstrategi(), 15);
		Pris pris = new Pris(100, produkt, prisliste);
		Pris pris2 = new Pris(80, produkt2, prisliste);
		Rabat rabat = new ProcentvisRabat(50);
		
		udlejning.tilfoejPris(pris);
		udlejning.tilfoejPris(pris2);
		udlejning.tilfoejRabat(rabat);
		
		
		// Act
		udlejning.udlever();
		TilstandEnum tilstand = udlejning.hentTilstand();
		
		// Assert
		TilstandEnum forventedTilstand = TilstandEnum.UDLEJET;
		assertEquals(forventedTilstand, tilstand);	
		
	}

}
