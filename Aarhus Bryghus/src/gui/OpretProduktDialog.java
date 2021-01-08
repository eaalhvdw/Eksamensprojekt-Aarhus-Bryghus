package gui;

import applikation.model.Produktgruppe;
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

public class OpretProduktDialog extends Stage {

	private Produktgruppe produktgruppe;
	private Service service;
	private ComboBox<String> cbProduktTyper;
	private ComboBox<String> cbProduktgrupper;
	private TextField txtNavn;
	private TextField txtBeskrivelse;
	private TextField txtAntal;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblAntal;
	private Label lblError;
	
	public OpretProduktDialog(Produktgruppe produktgruppe) {
		
		this.produktgruppe = produktgruppe;
		service = Service.hentService();
		cbProduktTyper = new ComboBox<String>();
		cbProduktgrupper = new ComboBox<String>();
		txtNavn = new TextField();
		txtBeskrivelse = new TextField();
		lblAntal = new Label();
		txtAntal = new TextField();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(true);
		this.setTitle("Opret produkt");

		GridPane pane = new GridPane();
		Scene scene = new Scene(pane);
		this.initContent(pane);
		this.setScene(scene);
	}
	
	private void initContent(GridPane pane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setGridLinesVisible(false);
		
		pane.add(new Label("Produktgruppe: "), 0, 0);
		pane.add(new Label("" + produktgruppe.hentNavn()), 1, 0);
		
		pane.add(new Label("Produkttype:"),  0, 1);
		String[] produktTyper = new String[] {"Standard Produkt", "Produkt Pakke", "Rundvisning", "Fustage"};
		cbProduktTyper.getItems().setAll(produktTyper);
		cbProduktTyper.getSelectionModel().selectFirst();
		cbProduktTyper.setOnAction(action -> this.selectionChangedCbProduktType(pane));
		
		pane.add(cbProduktTyper, 1, 1);
		pane.add(new Label("Navn: "), 0, 2);
		pane.add(txtNavn, 1, 2, 3, 1);
		pane.add(new Label("Beskrivelse: "), 0, 3);
		pane.add(txtBeskrivelse, 1, 3, 3, 1);		
		
		// add produktgruppe til combobox
		for (Produktgruppe produktgruppe : service.hentProduktgrupper()) {
			this.cbProduktgrupper.getItems().add(produktgruppe.hentNavn());
		}
		
		cbProduktgrupper.getSelectionModel().selectFirst();
		
		
		btnOpret.setMinWidth(60);
		btnOpret.setOnAction(event -> opretAction());
		btnAnnuller.setMinWidth(60);
		btnAnnuller.setOnAction(event -> this.hide());

		HBox hbox = new HBox(20);
		hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(btnOpret);
		hbox.getChildren().add(btnAnnuller);
		pane.add(hbox, 1, 6, 1, 1);

		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 7, 3, 1);
	}
	
	private void selectionChangedCbProduktType(GridPane pane) {
		
		pane.getChildren().remove(lblAntal);
		pane.getChildren().remove(txtAntal);
		
		switch (cbProduktTyper.getSelectionModel().getSelectedItem()) {
		
		case "Standard Produkt":
			break;
		
		case "Produkt Pakke":
			pane.add(lblAntal, 0, 4);
			lblAntal.setText("Indhold antal: ");
			pane.add(txtAntal, 1, 4);
			pane.add(new Label("Vælg indhold gruppe: "), 0, 5);
			
			pane.add(cbProduktgrupper, 1, 5);
			
			break;
			
		case "Rundvisning":
			break;
		
		case "Fustage":
			pane.add(lblAntal, 0, 4);
			lblAntal.setText("Liter: ");
			pane.add(txtAntal, 1, 4);
			break;
			
		default:
			break;
		}
	}
	
	//------------ Button action---------------------------------
	
	private void opretAction() {
		
		String navn = txtNavn.getText();
		String beskrivelse = txtBeskrivelse.getText();
		
		int antal = 0;
		
		try {
			antal = Integer.parseInt(txtAntal.getText());
		}
		catch(NumberFormatException e){

		}
		
		Produktgruppe indholdgruppe = service.hentProduktgrupper().get(this.cbProduktgrupper.getSelectionModel().getSelectedIndex());
		
		if(txtNavn.getText().isEmpty()) {
			lblError.setText("Skriv venligst navnet på produktet.");
		} else {
			switch (cbProduktTyper.getSelectionModel().getSelectedItem()) {
			
			case "Produkt Pakke":
				service.opretProduktPakke(navn, beskrivelse, produktgruppe, antal, indholdgruppe);
				break;
				
			case "Rundvisning":
				service.opretRundvisning(navn, beskrivelse, produktgruppe, service.opretStandardSalgsstrategi());
				break;
			
			case "Fustage":
				service.opretFustage(navn, beskrivelse, produktgruppe, antal);
				break;
				
			default:
				service.opretProdukt(txtNavn.getText(), beskrivelse, produktgruppe, service.opretStandardSalgsstrategi());
				break;
			}

			this.hide();
		}
	}
}
