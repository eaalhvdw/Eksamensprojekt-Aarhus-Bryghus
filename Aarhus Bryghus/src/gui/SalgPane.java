package gui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import applikation.model.Fustage;
import applikation.model.Pris;
import applikation.model.PrisRabat;
import applikation.model.Produkt;
import applikation.model.ProduktPakke;
import applikation.model.Produktgruppe;
import applikation.model.Rabat;
import applikation.model.Salg;
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

public class SalgPane extends GridPane implements HarUpdate {

	private Service service;
	private Salg salg;
	
	private ListView<Produktgruppe> lstProduktgruppe;
	private ListView<Produkt> lstProdukt;
	private ListView<Pris> lstPris;
	private ListView<String> lstKurv;
	
	private Button btnTilfoejProdukt;
	private Button btnKoeb;
	private Button btnOpretRabat;
	private Button btnToemKurv;
	
	private Label lblKurvPrisSum;
	private Label lblError;
	
	public SalgPane() {
		service = Service.hentService();
		
		btnTilfoejProdukt = new Button("Tilføj Produkt");
		btnKoeb = new Button("Køb");
		btnOpretRabat = new Button("Tilføj Rabat");
		btnToemKurv = new Button("Tøm kurv");
		
		lblKurvPrisSum = new Label();
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
		
		// Label & ListView: Produkt
		this.add(new Label("Vælg produkt"), 1, 0);
		this.add(lstProdukt, 1, 1, 1, 2);
		lstProdukt.setMinWidth(80);
		
		// Label & ListView: Pris
		this.add(new Label("Priser"), 2, 0);
		this.add(lstPris, 2, 1, 1, 2);
		lstPris.setMinWidth(250);
		
		// Btn tilfoejProdukt
		this.add(btnTilfoejProdukt, 2, 3);
		btnTilfoejProdukt.setOnAction(event -> actionTilfoejProdukt());
		btnTilfoejProdukt.setMinSize(40, 20);

		// Label & ListView: Kurv
		this.add(new Label("Kurv"), 3, 0);
		lstKurv = new ListView<String>();
		this.add(lstKurv, 3, 1,1,2);
		
		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_LEFT);
		hbox.getChildren().add(lblKurvPrisSum);
		hbox.getChildren().add(btnOpretRabat);
		btnOpretRabat.setOnAction(action -> this.actionRabat());
		hbox.getChildren().add(btnKoeb);
		btnKoeb.setOnAction(action -> this.actionKoeb());
		hbox.getChildren().add(btnToemKurv);
		btnToemKurv.setOnAction(action -> clear());
		this.add(hbox, 3, 3, 3, 1);
		
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
			lstProdukt.getItems().setAll(produktgruppe.hentProdukter());
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
	
	private void vælgIndholdTilSammenPakning(ProduktPakke produktPakke) {
		if( produktPakke.derErPlads()) {
			TilfoejIndholdDialog dia = new TilfoejIndholdDialog(produktPakke); 
			dia.showAndWait();
		}
	}
	
	private void updateLblKurvPrisSum() {
		DecimalFormat prisFormat = new DecimalFormat("#0.00");
		
		try {
			lblKurvPrisSum.setText("Totalpris: " + prisFormat.format(salg.samletPris()) + " dkk"); 
		} catch (NullPointerException e) {
			
		}
	}
	
	private String produktToString (Produkt produkt) {
		
		ArrayList<Rabat> rabatter = produkt.hentRabatter(); 
		int id = produkt.hentID();
		String navn = produkt.hentNavn();
		String beskrivelse = produkt.hentBeskrivelse();
		
		if(rabatter != null) {
			for(Rabat rabat : rabatter) {
				if(rabat.erAktiv() && beskrivelse != null) {
					return "ID: " + id + ". " + navn + ", " + beskrivelse + ". [TILBUD]";
				} else if(rabat.erAktiv()) {
					return "ID: " + id + ". " + navn + ". [TILBUD]";
				}		
			}
		}
		
		if(beskrivelse != null) {
			return "ID: " + id + ". " + navn + ", " + beskrivelse;
		}
		return "ID: " + id + ". " + navn;
	}
	
	private String prisToString (Pris pris) {
		
		DecimalFormat prisFormat = new DecimalFormat("#0.00");
		
		String string = pris.hentPrisliste().hentNavn() + ": " +  prisFormat.format(pris.hentPris()) + " dkk";
		String tilbud = "";
		
		
		if(pris.harAktiveRabatter()) {
			tilbud = "[TILBUD: " + prisFormat.format(pris.beregnSalgsPris()) + " dkk]";
		}

		return string + " " + tilbud;
	}
	
	private String rabatTilString(Rabat rabat) {
		String string;
		DecimalFormat rabatFormat = new DecimalFormat("#0.00");
		
		if(rabat instanceof PrisRabat) {
			string = "PrisRabat -" + rabatFormat.format((rabat.hentRabat())) + " dkk";
		}
		else {
			string = "ProcentRabat -" + rabatFormat.format(rabat.hentRabat()) + "%";
		}
		
		return string;
	}
	
	private void clear() {
		lstKurv.getItems().clear();
		lblKurvPrisSum.setText("Totalpris: ");
		lstPris.getSelectionModel().clearSelection();
		lstPris.getItems().clear();
		lstProdukt.getSelectionModel().clearSelection();
		lstProdukt.getItems().clear();
		lstProduktgruppe.getSelectionModel().clearSelection();
		salg = null;
	}
	
	// ----- Button actions
	
	private void actionTilfoejProdukt() {		
		if(salg == null) {
			salg = service.opretSalg();
		}
		
		Pris pris = this.lstPris.getSelectionModel().getSelectedItem();
		Produktgruppe produktgruppe = lstProduktgruppe.getSelectionModel().getSelectedItem();
		Produkt produkt = this.lstProdukt.getSelectionModel().getSelectedItem();
		lblError.setText("");
		
		if(produktgruppe == null && produkt == null) {
			lblError.setText("Vælg venligst et produkt for en produktgruppe.");
				
		} else if(produkt == null) {
			lblError.setText("Vælg venligst et produkt.");
			
		} else if(this.lstPris.getItems().size() == 1 || pris != null) {
			
			pris = (Pris) pris.clone();
			
			if(pris.hentProdukt() instanceof ProduktPakke) {
				this.vælgIndholdTilSammenPakning((ProduktPakke) pris.hentProdukt());
			}

			salg.tilfoejPris(pris);
			lstKurv.getItems().add(this.produktToString(pris.hentProdukt()) + " " + this.prisToString(pris));
			this.updateLblKurvPrisSum();
			
		} else {
			lblError.setText("Vælg venligst en pris for produktet.");
		}
	}
	
	private void actionRabat() {
		
		Produktgruppe produktgruppe = lstProduktgruppe.getSelectionModel().getSelectedItem();
		Produkt produkt = this.lstProdukt.getSelectionModel().getSelectedItem();
		lblError.setText("");
			
		if(lstKurv.getItems().isEmpty()) {
			this.lblError.setText("Tilføj venligst minimum ét produkt til kurven.");
				
		} else if(produktgruppe != null && produkt == null) {
			lblError.setText("Vælg venligst et produkt.");
					
		} else {		
			int antalRabatter = salg.hentRabatter().size();
			OpretRabatTilSalgDialog dia = new OpretRabatTilSalgDialog(salg);
			dia.showAndWait();
			if(salg.hentRabatter().size() != antalRabatter) {
				lstKurv.getItems().add(this.rabatTilString(salg.hentRabatter().get(antalRabatter)));	
					updateLblKurvPrisSum();
			}			
		}
	}
		
	private void actionKoeb() {
			
			if(salg == null){
				this.lblError.setText("Tilføj venligst minimum ét produkt til kurven.");
			}
			else {
				OpretKoebDialog dia = new OpretKoebDialog(salg); 
				dia.showAndWait();
				clear();	// Gør klar til at lave et nyt salg
			}
		
	}

	@Override
	public void update() {
		lstProduktgruppe.getItems().setAll(service.hentProduktgrupper());
		this.selectionChangedGruppe();
		this.selectionChangedProdukt();
		this.updateLblKurvPrisSum();
		
	}
}
