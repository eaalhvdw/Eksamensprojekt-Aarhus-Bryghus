package gui;

import applikation.model.Produkt;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ProduktCellFactory implements Callback<ListView<Produkt>, ListCell<Produkt>>{

	@Override
	public ListCell<Produkt> call(ListView<Produkt> listView) {
		
		return new ProduktCell();
	}

}
