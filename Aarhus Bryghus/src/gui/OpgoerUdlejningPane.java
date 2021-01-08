package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import applikation.model.Udlejning;
import applikation.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class OpgoerUdlejningPane extends GridPane implements HarUpdate {
	
	private Service service; 
	private ListView<Udlejning> lstUdlejninger;
	private Button btnBetal;
	private Label lblVaelgOversigt;
	private ComboBox<String> comboBox;
	private Label lblStartDato;
	private Label lblSlutDato;
	private DatePicker dpStartDato;
	private DatePicker dpSlutDato;
	private LocalDate startdato;
	private LocalDate slutdato;
	private DecimalFormat prisFormat;
	private Label lblError;
	private Button btnLever;
	
	public OpgoerUdlejningPane() {
		
		service = Service.hentService();
		lstUdlejninger = new ListView<Udlejning>();	
		btnBetal = new Button("Betal");
		lblVaelgOversigt = new Label("Vælg oversigt for udlejninger");
		comboBox = new ComboBox<String>();
		lblStartDato = new Label("Vælg startdato");
		lblSlutDato = new Label("Vælg slutdato");
		dpStartDato = new DatePicker();
		dpSlutDato = new DatePicker();
		prisFormat = new DecimalFormat("#0.00");
		lblError = new Label();
		btnLever = new Button("Udlevér");
		
		this.setPadding(new Insets(20));
		this.setHgap(10);
		this.setVgap(10);
		this.setGridLinesVisible(false);
		lstUdlejninger.setCellFactory(new UdlejningCellFactory());
		lstUdlejninger.getItems().setAll(service.hentUdlejninger());
		this.add(lstUdlejninger, 0, 1, 2, 2);
		lstUdlejninger.setMinWidth(300);

		btnBetal.setOnAction(action -> actionBetal());
		
		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_LEFT);
		hbox.getChildren().add(lblVaelgOversigt);
		hbox.getChildren().add(comboBox);
		this.add(hbox, 0, 0);
		
		comboBox.getItems().add("I dag");
		comboBox.getItems().add("Denne uge");
		comboBox.getItems().add("Alle");
		comboBox.setOnAction(action -> selectionChangedListView());
		// Start med at vise en statistik
		comboBox.getSelectionModel().selectFirst();	
		
		dpStartDato.setOnAction(action -> this.actionVaelgStartdato());
		dpSlutDato.setOnAction(action -> this.actionVaelgSlutdato());
		
		HBox hbox1 = new HBox(10);
		hbox1.alignmentProperty().set(Pos.BOTTOM_LEFT);
		hbox1.getChildren().add(btnBetal);
		hbox1.getChildren().add(btnLever);
		this.add(hbox1, 0, 3);
		
		btnBetal.setOnAction(action -> this.actionBetal());
		btnLever.setOnAction(action -> this.actionLeveret());
		
		lblError.setTextFill(Color.RED);
		
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();
		row3.setValignment(VPos.BOTTOM);
		getRowConstraints().addAll(row1, row2, row3);	
	}
	
	private void selectionChangedListView() {
		switch(this.comboBox.getSelectionModel().getSelectedItem()) {
			case "I dag":
				lstUdlejninger.getItems().setAll(service.hentUdlejninger(LocalDate.now()));
				break;
			case "Denne uge":
				seDenneUge();
				break;
			case "Alle":
				lstUdlejninger.getItems().setAll(service.hentUdlejninger());
				break;
			default:
				break;
		}
	}
	
	private void seDenneUge() {
		lstUdlejninger.getItems().clear();
		ArrayList<Udlejning> resultat = new ArrayList<Udlejning>();
		
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
		int weekNumber = LocalDate.now().get(woy);
		
		for (Udlejning udlejning : service.hentUdlejninger()) {
			
			// Check efter om udlejning starter i denne uge
			if(udlejning.hentStartDato().get(woy) == weekNumber || udlejning.hentSlutDato().get(woy) == weekNumber) {
				resultat.add(udlejning);
			}
		}
		lstUdlejninger.getItems().setAll(resultat);
	}
	
	public void update() {
		selectionChangedListView(); 
	}
	
	private void actionVaelgStartdato() {
		LocalDate dato = dpStartDato.getValue();
		
		if(dato != null)
			startdato = dato;
	}
	
	private void actionVaelgSlutdato() {
		LocalDate dato = dpSlutDato.getValue();
		
		if(dato != null)
			slutdato = dato;
	}

	//--------------Button actions-------------------------------------------------------------------------------
	
	private void actionLeveret() {
		Udlejning udlejning = this.lstUdlejninger.getSelectionModel().getSelectedItem();
		
		if(udlejning != null) {
			udlejning.udlever();
		}
		update();
	}
	
	private void actionBetal() {
		
		Udlejning udlejning = this.lstUdlejninger.getSelectionModel().getSelectedItem();
		
		if(udlejning != null) {
			OpretKoebDialog dia = new OpretKoebDialog(udlejning); 
			dia.showAndWait();
			update();
		}
	}	
}
