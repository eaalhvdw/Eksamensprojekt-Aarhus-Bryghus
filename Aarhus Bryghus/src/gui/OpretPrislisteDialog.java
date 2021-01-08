package gui;

import applikation.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretPrislisteDialog extends Stage {

	private Service service;
	private TextField txtNavn;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretPrislisteDialog() {
		
		service = Service.hentService();
		txtNavn = new TextField();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Opret prisliste");

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
		
		pane.add(new Label("Navn: "), 0, 0);
		pane.add(txtNavn, 1, 0);
		
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
	
	private void opretAction() {
		if(txtNavn.getText().isEmpty()) {
			lblError.setText("Skriv venligst navnet på prislisten.");
		} else {
			service.opretPrisliste(txtNavn.getText());
			this.hide();
		}
	}
}
