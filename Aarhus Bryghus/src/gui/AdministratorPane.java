package gui;

import java.util.ArrayList;

import applikation.model.Pris;
import applikation.model.Produkt;
import applikation.model.Produktgruppe;
import applikation.service.Service;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class AdministratorPane extends GridPane {

	private Service service;
	
	private ListView<Produktgruppe> lstProduktgruppe;
	private ListView<Produkt> lstProdukt;
	private ListView<Pris> lstPris;
	
	private Button btnOpretGruppe;
	private Button btnOpretProdukt;
	private Button btnOpretPrisliste;
	private Button btnOpretPris;
	private Button btnOpretRabat;
	
	private Label lblError;
	
	public AdministratorPane() {
		service = Service.hentService();
		
		btnOpretGruppe = new Button("Opret produktgruppe");
		btnOpretProdukt = new Button("Opret produkt");
		btnOpretPrisliste = new Button("Opret prisliste");
		btnOpretPris = new Button("Registrer pris");
		btnOpretRabat = new Button("Opret rabat");
		
		lblError = new Label();
		
		this.setPadding(new Insets(20));
		this.setHgap(14);
		this.setVgap(14);
		this.setGridLinesVisible(false);
		
		initContent(this);
		
	}
	
	private void initContent(GridPane pane) {
		// initiate instances
		initLstProduktgruppe();
		initLstProdukt();
		initLstPris();
		
		// Label & ListView: Produktgruppe
		this.add(new Label("Vælg produktgruppe"), 0, 0);
		this.add(lstProduktgruppe, 0, 1, 1, 2);
		lstProduktgruppe.setMinWidth(30);
		
		// Button -> Opret Produktgruppe 
		this.add(btnOpretGruppe, 0, 3);
		btnOpretGruppe.setOnAction(event -> actionOpretGruppe());
		
		// Label & ListView: Produkt
		this.add(new Label("Vælg produkt"), 1, 0);
		this.add(lstProdukt, 1, 1, 1, 2);
		lstProdukt.setMinWidth(80);
		
		// HBox
		// Button -> Opret Produkt
		// Button -> Opret Rabat
		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_LEFT);
		hbox.getChildren().add(btnOpretProdukt);
		btnOpretProdukt.setOnAction(action -> actionOpretProdukt());
		btnOpretRabat.setOnAction(action -> actionOpretRabat());
		hbox.getChildren().add(btnOpretRabat);
		this.add(hbox, 1, 3, 3, 1);
		
		// Label & ListView: Pris
		this.add(new Label("Priser"), 2, 0);
		this.add(lstPris, 2, 1, 1, 2);
		lstPris.setMinWidth(250);
		
		// HBox
		// Button -> Opret Prisliste
		// Button -> Registrer pris
		HBox hbox2 = new HBox(10);
		hbox2.alignmentProperty().set(Pos.BOTTOM_LEFT);
		hbox2.getChildren().add(btnOpretPrisliste);
		btnOpretPrisliste.setOnAction(action -> actionOpretPrisliste());
		btnOpretPris.setOnAction(action -> actionRegistrerPris());
		
		hbox2.getChildren().add(btnOpretPris);
		this.add(hbox2, 2, 3, 3, 1);
		
		// Label Error Message
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 5, 2, 2);
		
	}
	
	private void initLstProduktgruppe() {
		
		lstProduktgruppe = new ListView<Produktgruppe>();
		
		lstProduktgruppe.getItems().setAll(service.hentProduktgrupper());
		lstProduktgruppe.setCellFactory(new ProduktgruppeCellFactory());
		
		ChangeListener<Produktgruppe> listenerPG = (ov, oldProduktgruppe, newProduktgruppe) -> selectionChangedGruppe();
		lstProduktgruppe.getSelectionModel().selectedItemProperty().addListener(listenerPG);
	}
	
	private void initLstProdukt() {
		
		lstProdukt = new ListView<Produkt>();
		
		lstProdukt.setCellFactory(new ProduktCellFactory());
		ChangeListener<Produkt> listenerP = (ov, oldProdukt, newProdukt) -> selectionChangedProdukt();
		lstProdukt.getSelectionModel().selectedItemProperty().addListener(listenerP);
	}
	
	private void initLstPris() {
		lstPris = new ListView<Pris>();
		lstPris.setCellFactory(new PrisCellFactory());
	}
	
	// SelectionChanged
	
	private void selectionChangedGruppe() {
		Produktgruppe produktgruppe = lstProduktgruppe.getSelectionModel().getSelectedItem();
		
		if (produktgruppe != null) {
			ArrayList<Produkt> produkter = new ArrayList<Produkt>();
			produkter.addAll(produktgruppe.hentProdukter());
			produkter.addAll(produktgruppe.hentUdlejningsprodukter());
			lstProdukt.getItems().setAll(produkter);
		}
	}
	
	private void selectionChangedProdukt() {
		Produkt produkt = lstProdukt.getSelectionModel().getSelectedItem();
		
		if(produkt != null) {
			if(produkt.hentPriser() != null && produkt.hentPriser().size() > 0) {
				lstPris.getItems().setAll(produkt.hentPriser());
			}
			else {
				lblError.setText("Der er ingen pris på denne vare");
			}
		}
	}
	
	//------------ Button action---------------------------------
	
	private void actionOpretGruppe() {
		OpretGruppeDialog dia = new OpretGruppeDialog();
		dia.showAndWait();
		
		// opdaterer produktgruppeliste
		lstProduktgruppe.getItems().setAll(service.hentProduktgrupper());
	}
	
	private void actionOpretProdukt() {
		Produktgruppe produktgruppe = lstProduktgruppe.getSelectionModel().getSelectedItem();
		
		lblError.setText("");
		
		if (produktgruppe == null) {
			lblError.setText("Vælg venligst en produktgruppe.");
		} else {
			OpretProduktDialog dia = new OpretProduktDialog(produktgruppe);
			dia.showAndWait();
			
			// opdaterer produktliste
			ArrayList<Produkt> produkter = new ArrayList<Produkt>();
			produkter.addAll(produktgruppe.hentProdukter());
			produkter.addAll(produktgruppe.hentUdlejningsprodukter());
			lstProdukt.getItems().setAll(produkter);
		}
	}
	
	private void actionOpretPrisliste() {
		OpretPrislisteDialog dia = new OpretPrislisteDialog();
		dia.showAndWait();
	}
	
	private void actionRegistrerPris() {
		Produkt produkt = this.lstProdukt.getSelectionModel().getSelectedItem();
		lblError.setText("");
		
		if (produkt == null) {
			lblError.setText("Vælg venligst et produkt eller et udlejningsprodukt.");
		} else {
			if(produkt != null) {
				OpretPrisTilProduktDialog dia = new OpretPrisTilProduktDialog(produkt);
				dia.showAndWait();
				this.selectionChangedProdukt();
			}
		}
	}
	
	private void actionOpretRabat() {
		Produkt produkt = this.lstProdukt.getSelectionModel().getSelectedItem();
		lblError.setText("");
		
		if (produkt == null) {
			lblError.setText("Vælg venligst et produkt.");
		
		} else if(produkt.hentPriser().isEmpty()) {
			lblError.setText("Der er ingen pris på dette produkt.");
			
		} else {
				OpretRabatTilProduktDialog dia = new OpretRabatTilProduktDialog(produkt);
				dia.showAndWait();
				// opdaterer liste med priser 
				selectionChangedProdukt();
				//opdaterer liste med produkter
				selectionChangedGruppe();
		}
	}
}
