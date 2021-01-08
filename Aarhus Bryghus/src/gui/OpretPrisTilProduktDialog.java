package gui;

import applikation.model.Pris;
import applikation.model.Prisliste;
import applikation.model.Produkt;
import applikation.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretPrisTilProduktDialog extends Stage {

	private Produkt produkt;
	private Service service;
	private TextField txtPris;
	private ComboBox<String> cbPrislister;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretPrisTilProduktDialog(Produkt produkt) {
		
		this.produkt = produkt;
		service = Service.hentService();
		txtPris = new TextField();
		cbPrislister = new ComboBox<String>();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Registrer pris");

		GridPane pane = new GridPane();
		Scene scene = new Scene(pane);
		this.initContent(pane);
		this.setScene(scene);
	}
	
	private void initContent(GridPane pane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(20);
		pane.setVgap(10);
		pane.setGridLinesVisible(false);
		
		pane.add(new Label("Pris: "), 0, 0);
		pane.add(txtPris, 1, 0);
		txtPris.setMaxWidth(100);
		
		pane.add(new Label("Prisliste: "), 0, 1);
		pane.add(cbPrislister, 1, 1);
		
		for (Prisliste prisliste : service.hentPrislister()) {
			this.cbPrislister.getItems().add(prisliste.hentNavn());
		}
		
		cbPrislister.getSelectionModel().select(0);
		cbPrislister.setMinWidth(100);
		
		btnOpret.setMinWidth(100);
		btnOpret.setOnAction(event -> opretAction());
		btnAnnuller.setMinWidth(100);
		btnAnnuller.setOnAction(event -> this.hide());

		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(btnOpret);
		hbox.getChildren().add(btnAnnuller);
		pane.add(hbox, 0, 3, 2, 1);

		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 4, 2, 1);
	}
	
	
	//-----------------Button action-------------------------------------
	
	private void opretAction() {
		Prisliste prisliste = service.hentPrislister().get(cbPrislister.getSelectionModel().getSelectedIndex());
		
		if(txtPris.getText().isEmpty()) {	
			lblError.setText("Skriv venligst prisen på produktet.");
		} else if(cbPrislister.getSelectionModel().isEmpty()) {
			lblError.setText("Vælg venligst en prisliste.");
		} else {
			
			try {
				// Check om brugeren er ved at ændre en prisliste der allerede eksisterer
				for (Pris pris : produkt.hentPriser()) {
					if(pris.hentPrisliste().hentNavn().equals(prisliste.hentNavn())) {
						service.redigerPris(pris, Double.parseDouble(txtPris.getText()));
						this.hide();
						return;
					}
				}
				
				// Opret ny pris
				service.opretPris(prisliste,Double.parseDouble(txtPris.getText()), produkt);
		
				this.hide();
			}
			catch(NumberFormatException e){
				lblError.setText("Angiv venligst prisen med tal.");
			}
		}
	}
}