package gui;

import applikation.service.Service;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Service service;

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void init() {
		service = Service.hentService();
		service.opretNogleObjekter();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Kasseapparat");
		BorderPane pane = new BorderPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setHeight(400);
		stage.setWidth(1200);
		stage.show();
	}
	
	private void initContent(BorderPane pane) {
		TabPane tabPane = new TabPane();
		this.initTabPane(tabPane);
		pane.setCenter(tabPane);
	}
	
	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		opretTabTilTabPane("Administrator", new AdministratorPane(),tabPane);
		opretTabTilTabPane("Salg", new SalgPane(), tabPane);
		opretTabTilTabPane("Udlejning", new UdlejningPane(), tabPane);
		opretTabTilTabPane("Opgør udlejning", new OpgoerUdlejningPane(), tabPane);
		opretTabTilTabPane("Statistik", new StatistikPane(), tabPane);

	}
	
	private Tab opretTabTilTabPane(String navn, Node indhold, TabPane tabPane) {
		Tab tab = new Tab(navn);
		tab.setContent(indhold);
		tabPane.getTabs().add(tab);
		
		if(indhold instanceof HarUpdate) {
			tab.setOnSelectionChanged(action -> ((HarUpdate) indhold).update());
		}
		
		return tab;
	}

}
