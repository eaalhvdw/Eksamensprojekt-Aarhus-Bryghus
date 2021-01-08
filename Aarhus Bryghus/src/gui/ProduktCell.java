package gui;

import java.util.ArrayList;

import applikation.model.Produkt;
import applikation.model.Rabat;
import applikation.model.Udlejningsstrategi;
import javafx.scene.control.ListCell;

public class ProduktCell extends ListCell<Produkt> {
	
	private Produkt produkt;
	
	@Override
	public void updateItem(Produkt item, boolean empty) {
		super.updateItem(item, empty);
		
		produkt = item;
		
		String text = "";
		
		if(item != null && !empty) {
			text = produktToString();
		}
		
		this.setText(text);
	}
	
	private String produktToString () {
		
		ArrayList<Rabat> rabatter = produkt.hentRabatter(); 
		int id = produkt.hentID();
		String navn = produkt.hentNavn();
		String beskrivelse = produkt.hentBeskrivelse();
		
		String string = "ID: " + id + ". " + navn;
		
		if(beskrivelse != null && !beskrivelse.isEmpty()) {
			string = string + ", " + beskrivelse;
		}
		
		boolean found = false;
		int i = 0;
		while(!found && i < rabatter.size()) {
			if(rabatter.get(i).erAktiv()) {
				found = true;
			}
			
			i ++;
		}
		
		if(found) {
			string = string + " [TILBUD]";
		}
		
		if(produkt.hentSalgsstrategi() instanceof Udlejningsstrategi) {
			string = string + " [UDLEJNING]";
		}
		
		return string;
	}
}
