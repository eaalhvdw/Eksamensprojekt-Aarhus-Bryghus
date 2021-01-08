package gui;

import java.util.ArrayList;

import applikation.model.Produkt;
import applikation.model.ProduktPakke;
import applikation.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TilfoejIndholdDialog extends Stage {

	private Service service;
	private ProduktPakke produktPakke;
	private ArrayList<Produkt> valgteProdukter;
	private Label lblIndholdManglerAntal;	// Hvor mange du stadig kan vælge
	private int antalValgTilbage;	// Holder hvor mange antal der er tilbage
	private ArrayList<Produkt> produkter;
	private ArrayList<Integer> antalProdukterValgt;
	private Button btnGodkend;
	
	public TilfoejIndholdDialog(ProduktPakke produktPakke) {
		
		service = Service.hentService();
		this.produktPakke = produktPakke;
		lblIndholdManglerAntal = new Label();
		antalProdukterValgt = new ArrayList<Integer>();
		btnGodkend = new Button("Godkend");
		valgteProdukter = new ArrayList<Produkt>();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Vælg ølvarianter til " + produktPakke.hentNavn() + ": " + produktPakke.hentBeskrivelse());
		
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane);
		this.initContent(pane);
		this.setScene(scene);
	}
	
	private void initContent(GridPane pane) {
		pane.setPadding(new Insets(20));
		pane.setHgap(20);
		pane.setVgap(10);
		pane.setGridLinesVisible(false);
		
		antalValgTilbage = produktPakke.hentIndholdStoerrelse();
		
		lblIndholdManglerAntal.setText("Vælg " + produktPakke.hentIndholdStoerrelse() + " produkter:");
		pane.add(lblIndholdManglerAntal, 0, 0);
		
		produkter = new ArrayList<Produkt>(produktPakke.hentIndholdGruppe().hentProdukter());
		
		for (int i = 0; i < produkter.size(); i++) {
			Label lbl = new Label(produkter.get(i).toString());
			pane.add(lbl, 0, i+1);
			
			Button btnFormindske = new Button("-");		
			Label lblAntal = new Label("0");
			Button btnOeg = new Button("+");
			
			HBox hbox = new HBox(10);
			hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
			hbox.getChildren().add(btnFormindske);
			hbox.getChildren().add(lblAntal);
			hbox.getChildren().add(btnOeg);
			pane.add(hbox, 2, i+1);
			
			Label lblAntalValgt = new Label("Antal");
			pane.add(lblAntalValgt, 1, i+1);
			
			antalProdukterValgt.add(0);
			
			// add action to the btns
			final int index = i;	// action kræver at parameter er final
			btnOeg.setOnAction(action -> actionTilfoejProdukt(index, lblAntal));
			btnFormindske.setOnAction(action -> actionFjernProdukt(index, lblAntal));
			
		}	
		btnGodkend.setOnAction(action -> actionGodkend());
		pane.add(btnGodkend, 0, produkter.size() + 2 );
	}
	
	private void justerLblIndholdManglerAntal(int antal) {
		
		antalValgTilbage += antal;
		
		lblIndholdManglerAntal.setText("Vælg " + antalValgTilbage + " produkter.");
	}
	
	// Actions
	
	private void actionTilfoejProdukt(int i, Label label) {
		
		if(produktPakke.derErPlads() && antalValgTilbage > 0) {
			valgteProdukter.add(produkter.get(i));
			antalProdukterValgt.set(i, antalProdukterValgt.get(i) + 1);	// increases it by one
			label.setText("" + antalProdukterValgt.get(i));
			justerLblIndholdManglerAntal(-1);
		}
	}
	
	private void actionFjernProdukt(int i, Label label) {
		if(antalProdukterValgt.get(i) >= 1 && antalValgTilbage < produktPakke.hentIndholdStoerrelse()) {
			valgteProdukter.remove(produkter.get(i));;
			antalProdukterValgt.set(i, antalProdukterValgt.get(i) - 1);	// decrease it by one
			label.setText("" +  antalProdukterValgt.get(i));
			justerLblIndholdManglerAntal(+1);
		}
	}

	private void actionGodkend() {
		if(antalValgTilbage == 0) {
			for (Produkt produkt : valgteProdukter) {
				produktPakke.tilfoejIndhold(produkt);
			}		
			this.hide();
		}
	}
}
