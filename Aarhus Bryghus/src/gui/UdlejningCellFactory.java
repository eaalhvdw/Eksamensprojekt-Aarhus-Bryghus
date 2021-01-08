package gui;

import applikation.model.Udlejning;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UdlejningCellFactory implements Callback<ListView<Udlejning>, ListCell<Udlejning>>{

	@Override
	public ListCell<Udlejning> call(ListView<Udlejning> listView) {
		return new UdlejningCell();
	}

}
