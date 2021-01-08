package gui;

import java.util.ArrayList;

import applikation.model.Produkt;
import applikation.model.Produktgruppe;
import applikation.model.Rabat;
import javafx.scene.control.ListCell;

public class ProduktgruppeCell extends ListCell<Produktgruppe> {

	
	@Override
	public void updateItem(Produktgruppe item, boolean empty) {
		super.updateItem(item, empty);
		
		String text = "";
		
		if(item != null && !empty) {
			text = item.hentNavn();
		}
		
		this.setText(text);
		
		setGraphic(null);
	}
	

}
