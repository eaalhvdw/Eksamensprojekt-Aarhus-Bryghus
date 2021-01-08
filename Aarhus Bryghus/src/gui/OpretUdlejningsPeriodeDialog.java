package gui;

import java.time.LocalDate;

import applikation.model.Udlejning;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretUdlejningsPeriodeDialog extends Stage {

	private Udlejning udlejning;
	private LocalDate startdato;
	private LocalDate slutdato;
	private DatePicker datePickStartdato;
	private DatePicker datePickSlutdato;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretUdlejningsPeriodeDialog(Udlejning udlejning) {
		
		this.udlejning = udlejning;
		datePickStartdato = new DatePicker();
		datePickSlutdato = new DatePicker();
		btnOpret = new Button("Opret");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Udlejningsperiode");

		GridPane pane = new GridPane();
		Scene scene = new Scene(pane);
		this.initContent(pane);
		this.setScene(scene);
	}
	
	public void initContent(GridPane pane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setGridLinesVisible(false);
		
		pane.add(new Label("Vælg startdato"), 0, 0);
		pane.add(datePickStartdato, 0, 1);
		datePickStartdato.setOnAction(action -> actionVaelgStartdato());
		
		pane.add(new Label("Vælg slutdato"), 1, 0);
		pane.add(datePickSlutdato, 1, 1);
		datePickSlutdato.setOnAction(action -> actionVaelgSlutdato());
		
		btnOpret.setMinWidth(100);
		btnOpret.setOnAction(event -> opretAction());
		btnAnnuller.setMinWidth(100);
		btnAnnuller.setOnAction(event -> this.hide());

		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(btnOpret);
		hbox.getChildren().add(btnAnnuller);
		pane.add(hbox, 0, 4, 2, 1);

		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 5, 2, 1);
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
	
	public void opretAction() {
		
		if(startdato == null && slutdato == null) {
			lblError.setText("Vælg venligst en start- og en slutdato for udlejningen.");
		
		} else if(startdato == null) {
			lblError.setText("Vælg venligst en startdato for udlejningen.");
		
		} else if(slutdato == null) {
			lblError.setText("Vælg venligst en slutdato for udlejningen.");
		
		} else if(slutdato.isBefore(startdato)) {
			lblError.setText("Slutdatoen kan ikke komme før startdatoen.");
		
		} else if(startdato.isBefore(LocalDate.now())) {
			
			lblError.setText("Startdatoen kan ikke komme før dags dato.");
			
		} else {
			udlejning.saetStartDato(startdato);
			udlejning.saetSlutDato(slutdato);
			this.hide();
		}
	}
} 	