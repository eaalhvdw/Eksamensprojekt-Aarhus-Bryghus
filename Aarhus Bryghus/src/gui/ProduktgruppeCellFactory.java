package gui;

import applikation.model.Produktgruppe;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ProduktgruppeCellFactory  implements Callback<ListView<Produktgruppe>, ListCell<Produktgruppe>>{

	@Override
	public ListCell<Produktgruppe> call(ListView<Produktgruppe> param) {
		return new ProduktgruppeCell();
	}

}
