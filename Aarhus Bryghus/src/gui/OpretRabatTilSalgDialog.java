package gui;

import applikation.model.Salg;
import applikation.service.Service;
import javafx.beans.value.ChangeListener;
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

public class OpretRabatTilSalgDialog extends Stage {

	private Salg salg;
	private Service service;
	private Label lblRabat;
	private ComboBox<String> cbRabattype;
	private TextField txtRabat;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretRabatTilSalgDialog(Salg salg) {
	
		this.salg = salg;
		service = Service.hentService();
		lblRabat = new Label();
		cbRabattype = new ComboBox<String>();
		txtRabat = new TextField();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Opret rabat");

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
		
		pane.add(new Label("Vælg rabat: "), 0, 0);
		pane.add(cbRabattype, 1, 0);
		cbRabattype.getItems().add("Procentvis");
		cbRabattype.getItems().add("Overslag");
		cbRabattype.getSelectionModel().select(0);
		cbRabattype.setMinWidth(100);
		
		ChangeListener<String> listenerR = (ov, oldRabat, newRabat) -> selectionChangedComboBox();
		cbRabattype.getSelectionModel().selectedItemProperty().addListener(listenerR);
		
		selectionChangedComboBox();
		pane.add(lblRabat, 0, 1);
		pane.add(txtRabat, 1, 1);
		txtRabat.setMaxWidth(100);
		
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
		lblError.setMinWidth(240);
	}
	
	public void selectionChangedComboBox() {
		if(cbRabattype.getSelectionModel().getSelectedItem().equals("Overslag")) {
			lblRabat.setText("Pris: ");
		} else {
			lblRabat.setText("Rabatprocent: ");
		}
	}
	
	//-----------------Button action-------------------------------------
	
	private void opretAction() {
		if(cbRabattype.getSelectionModel().getSelectedItem().equals("Overslag")) {
			if(txtRabat.getText().isEmpty()) {
				lblError.setText("Skriv venligst prisoverslaget.");
			} else {
				try {
					service.opretPrisRabatTilSalg(Double.parseDouble(txtRabat.getText()), salg);
					this.hide();
				}
				catch(NumberFormatException e){
					lblError.setText("Skriv venligst prisen på overslaget med tal.");
				}
			}
		} else if(cbRabattype.getSelectionModel().getSelectedItem().equals("Procentvis")) {
			if(txtRabat.getText().isEmpty()) {
				lblError.setText("Skriv venligst en rabatprocent.");
			} else {
				try {
					service.opretProcentvisRabatTilSalg(Double.parseDouble(txtRabat.getText()), salg);
					this.hide();
				}
				catch(NumberFormatException e){
					lblError.setText("Skriv venligst den procentvise rabat med tal.");
				}
			}
		}
	}
}
