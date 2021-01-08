package gui;

import applikation.model.Pris;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PrisCellFactory implements Callback<ListView<Pris>, ListCell<Pris>>{

	@Override
	public ListCell<Pris> call(ListView<Pris> listView) {
		return new PrisCell();
	}

}
