package gui;

import java.text.DecimalFormat;

import applikation.model.Pris;
import javafx.scene.control.ListCell;

public class PrisCell extends ListCell<Pris> {

	private Pris pris;
	
	@Override
	public void updateItem(Pris item, boolean empty) {
		super.updateItem(item, empty);
		
		pris = item;
		
		String text = "";
		
		if(item != null && !empty) {
			text = prisToString();
		}
		
		this.setText(text);
	}
	
	private String prisToString () {
		
		DecimalFormat prisFormat = new DecimalFormat("#0.00");
		
		String string = pris.hentPrisliste().hentNavn() + ": " +  prisFormat.format(pris.hentPris()) + " dkk";
		String tilbud = "";
		
		
		if(pris.harAktiveRabatter()) {
			tilbud = "[TILBUD: " + prisFormat.format(pris.beregnSalgsPris()) + " dkk]";
		}

		return string + " " + tilbud;
	}
}
