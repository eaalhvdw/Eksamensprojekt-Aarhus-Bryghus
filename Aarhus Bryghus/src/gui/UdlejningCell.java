package gui;

import applikation.model.Pris;
import applikation.model.Produkt;
import applikation.model.Udlejning;
import javafx.scene.control.ListCell;

public class UdlejningCell extends ListCell<Udlejning> {

	private Udlejning udlejning;
	
	@Override
	public void updateItem(Udlejning item, boolean empty) {
		super.updateItem(item, empty);
		
		udlejning = item;
		
		String text = "";
		
		if(item != null && !empty) {
			text = udlejningToString();
		}
		
		this.setText(text);
	}
	
	private String udlejningToString() {
		
		
		String indhold = "";
		for (Pris pris : udlejning.hentPriser()) {
			indhold = indhold + "ID: " + pris.hentProdukt().hentID() + " Navn: " + pris.hentProdukt().hentNavn() + " ";
		}
		
		String string = "ID: " +  udlejning.hentID() + 
				" Tilstand: " + udlejning.hentTilstand() + " Pris: " + 
				udlejning.hentKoebsTidspunkt() + " [" + indhold + "]";
		
		return string;
	}

}
