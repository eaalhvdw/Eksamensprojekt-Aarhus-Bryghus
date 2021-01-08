package gui;

import java.time.LocalDate;
import java.util.ArrayList;

import applikation.model.Pris;
import applikation.model.Prisliste;
import applikation.model.Produkt;
import applikation.model.Rabat;
import applikation.service.Service;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretRabatTilProduktDialog extends Stage {

	private Produkt produkt;
	private Service service;
	private LocalDate startdato;
	private LocalDate slutdato;
	private DatePicker datePickStartdato;
	private DatePicker datePickSlutdato;
	private Label lblRabat;
	private TextField txtRabat;
	private ComboBox<String> cbPrislister;
	private ComboBox<String> cbRabattype;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretRabatTilProduktDialog(Produkt produkt) {
		
		this.produkt = produkt;
		service = Service.hentService();
		datePickStartdato = new DatePicker();
		datePickSlutdato = new DatePicker();
		lblRabat = new Label();
		txtRabat = new TextField();
		cbPrislister = new ComboBox<String>();
		cbRabattype = new ComboBox<String>();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Opret rabat på produkt");

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
		
		pane.add(new Label("Vælg rabattype: "), 0, 0);
		pane.add(cbRabattype, 1, 0);
		cbRabattype.getItems().add("Procentvis");
		cbRabattype.getItems().add("Overslag");
		cbRabattype.getSelectionModel().select(0);
		cbRabattype.setMinWidth(100);
		
		ChangeListener<String> listenerR = (ov, oldRabat, newRabat) -> selectionChangedComboBox();
		cbRabattype.getSelectionModel().selectedItemProperty().addListener(listenerR);
		
		selectionChangedComboBox();
		pane.add(lblRabat, 0, 2);
		pane.add(txtRabat, 1, 2);
		txtRabat.setMaxWidth(100);
		
		pane.add(new Label("Vælg prisliste: "), 0, 1);
		pane.add(cbPrislister, 1, 1);

		for (Prisliste prisliste : service.hentPrislister()){
			cbPrislister.getItems().add(prisliste.hentNavn());
		}
		
		cbPrislister.getSelectionModel().select(0);
		cbPrislister.setMinWidth(100);
		
		pane.add(new Label("Vælg gyldighedsperiodens startdato"), 0, 4);
		pane.add(datePickStartdato, 0, 5, 1, 1);
		datePickStartdato.setOnAction(action -> actionVaelgStartdato());
		
		pane.add(new Label("Vælg gyldighedsperiodens slutdato"), 1, 4);
		pane.add(datePickSlutdato, 1, 5, 1, 1);
		datePickSlutdato.setOnAction(action -> actionVaelgSlutdato());
		
		btnOpret.setMinWidth(100);
		btnOpret.setOnAction(event -> opretAction());
		btnAnnuller.setMinWidth(100);
		btnAnnuller.setOnAction(event -> this.hide());

		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(btnOpret);
		hbox.getChildren().add(btnAnnuller);
		pane.add(hbox, 0, 6, 2, 1);

		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 7, 2, 1);
	}
	
	public void actionVaelgStartdato() {
		LocalDate dato = datePickStartdato.getValue();
		
		if(dato != null)
			startdato = dato;
	}
	
	public void actionVaelgSlutdato() {
		LocalDate dato = datePickSlutdato.getValue();
		
		if(dato != null)
			slutdato = dato;
	}
	
	public void selectionChangedComboBox() {
		if(cbRabattype.getSelectionModel().getSelectedItem().equals("Overslag")) {
			lblRabat.setText("Angiv overslagspris: ");
		} else {
			lblRabat.setText("Angiv rabatprocent: ");
		}
	}
	
	//-----------------Button action-------------------------------------
	
	private void opretAction() {
		
		if(txtRabat.getText().isEmpty()) {	
			if(this.cbRabattype.getSelectionModel().getSelectedItem().equals("Procentvis")) {
				lblError.setText("Skriv venligst en rabatprocent.");
			} else {
				lblError.setText("Skriv venligst et prisoverslag.");
			}		
		} else if(startdato == null && slutdato == null) {
			
			lblError.setText("Vælg venligst rabattens gyldighedsperiode.");
		
		} else if(startdato == null) {
			
			lblError.setText("Vælg venligst en startdato for gylidghedsperioden.");
		
		} else if(slutdato == null) {
			
			lblError.setText("Vælg venligst en slutdato for gyldighedsperioden.");
		
		} else if(slutdato.isBefore(startdato)) {
			
			lblError.setText("Slutdatoen kan ikke komme før startdatoen.");
		
		} else if(startdato.isBefore(LocalDate.now())) {
			
			lblError.setText("Startdatoen kan ikke komme før dags dato.");
			
		} else {
			
			try {

				// Opret Rabat
				if(this.cbRabattype.getSelectionModel().getSelectedItem().equals("Procentvis")) {
					
					double pris = Double.parseDouble(txtRabat.getText());
					Prisliste prisliste = service.hentPrislister().get(cbPrislister.getSelectionModel().getSelectedIndex());

					service.opretProcentvisRabatTilProdukt(pris, produkt, prisliste,startdato, slutdato);
				} else {
					
					double pris = Double.parseDouble(txtRabat.getText());
					Prisliste prisliste = service.hentPrislister().get(cbPrislister.getSelectionModel().getSelectedIndex());
					
					service.opretPrisRabatTilProdukt(pris, produkt, prisliste,startdato,slutdato);
					
				}		
				this.hide();
			}
			catch(NumberFormatException e) {
				lblError.setText("Angiv venligst rabatten med tal.");
			}
		}
	}
}
