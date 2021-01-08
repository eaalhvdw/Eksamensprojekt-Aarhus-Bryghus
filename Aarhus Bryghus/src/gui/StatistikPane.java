package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import applikation.model.Betalingsform;
import applikation.model.BetalingsformEnum;
import applikation.model.Pris;
import applikation.model.Salg;
import applikation.service.Service;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StatistikPane extends GridPane implements HarUpdate {
	
	private Service service;
	private ComboBox<String> cbVaelgStatistik;
	private ListView<String> lstStatistik;
	private String[] statistikTyper = new String[] {"Dagens salg", "Solgte klip i en periode", "Forbrugte klip i en periode", "Alle salg", "Alle solgte klip", "Alle brugte klip"};
	private Label lblAntal;
	private Label lblPrisSum;
	private Label lblStartDato;
	private Label lblSlutDato;
	private DatePicker dpStartDato;
	private DatePicker dpSlutDato;
	private LocalDate startdato;
	private LocalDate slutdato;
	private HBox hboxStart;
	private HBox hboxSlut;
	private DecimalFormat prisFormat;
	private DateTimeFormatter dateTimeFormat;
	private ListView<String> lstIndhold;
	
	public StatistikPane() {
		
		service = Service.hentService();
		cbVaelgStatistik = new ComboBox<String>();
		lstStatistik = new ListView<String>();
		lblAntal = new Label();
		lblPrisSum = new Label();
		lblStartDato = new Label("Vælg startdato");
		lblSlutDato = new Label("Vælg slutdato");
		dpStartDato = new DatePicker();
		dpSlutDato = new DatePicker();
		hboxStart  = new HBox(10);
		hboxSlut  = new HBox(10);
		prisFormat = new DecimalFormat("#0.00");
		dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		
		this.setPadding(new Insets(20));
		this.setHgap(10);
		this.setVgap(10);
		this.setGridLinesVisible(false);
		
		cbVaelgStatistik.getItems().setAll(this.statistikTyper);
		this.add(cbVaelgStatistik, 0, 0);
		this.cbVaelgStatistik.setOnAction(action -> this.actionVaelgStatistik());
		
		dpStartDato.setOnAction(action -> this.actionVaelgStartdato());
		dpSlutDato.setOnAction(action -> this.actionVaelgSlutdato());
		
		this.add(lstStatistik, 1, 0, 3, 4);
		lstStatistik.setMinWidth(400);
		
		HBox hbox = new HBox(10);
		hbox.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(lblAntal);
		hbox.getChildren().add(lblPrisSum);
		this.add(hbox, 1, 5);
		
		this.add(hboxStart, 4, 0);
		this.add(hboxSlut, 4, 1);
		
		// Start med at vise en statistik
		cbVaelgStatistik.getSelectionModel().selectFirst();
		actionVaelgStatistik();	
		
		
	}
	
	// Alle salg der er blevet lavet idag
	private void visDagensSalg() {
		
		// ListView
		lstStatistik.getItems().clear();
		
		ArrayList<String> resultat = new ArrayList<String>();
		int salgSum = 0;
		
		for (Salg salg : service.hentSalg()) {
			if(salg.hentKoebsTidspunkt().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())){
				resultat.add("Id: " + salg.hentID() + " Købstidspunkt: " + salg.hentKoebsTidspunkt().format(dateTimeFormat) + " Solgt for: " + prisFormat.format(salg.samletPris()));
				salgSum += salg.samletPris();
			}
		}
		
		lstStatistik.getItems().setAll(resultat);
		
		// lblAntal
		this.lblAntal.setText("Der er foretaget " +  resultat.size() +" salg i dag.");
		
		// lblSum
		
		this.lblPrisSum.setText("Samlet sum: " + prisFormat.format(salgSum) + " dkk");
	}
	
	private void visAlleSalg(){
		lstStatistik.getItems().clear();
		int salgSum = 0;
		
		for (Salg salg : service.hentSalg()) {
			lstStatistik.getItems().add(salg.toString());
			salgSum += salg.samletPris();
		}
		
		// lblAntal
		this.lblAntal.setText("Der er " + service.hentSalg().size() + " salg i alt.");
		
		// lblSum
		this.lblPrisSum.setText("Samlet sum: " + prisFormat.format(salgSum) + " dkk");
	}
	
	// Klippekort der er brugt over altid
	private void visAlleKlipBrugt() {	
		// ListView
		lstStatistik.getItems().clear();
		int sum = 0;
		
		for (Salg salg : service.hentSalg()) {
			for (Betalingsform betalingsform : salg.hentBetalingsformer()) {
				if(betalingsform.hentBetalingsformEnum().equals(BetalingsformEnum.KLIPPEKORT)) {
					lstStatistik.getItems().add("Klip brugt: " + betalingsform.hentBetaling() + " Dato: " + salg.hentKoebsTidspunkt().format(dateTimeFormat));
					sum += betalingsform.hentBetaling();
				}
			}
		}
		
		// lblAntal
		lblAntal.setText("Der er blevet brugt " + sum + " klip i alt.");
		this.lblPrisSum.setText("");
	}
	
	private void visKlipBrugtIEnPeriode() {
		// ListView
		lstStatistik.getItems().clear();
		int sum = 0;
		
		for (Salg salg : service.hentSalg()) {
			if(erIPerioden(salg.hentKoebsTidspunkt().toLocalDate())) {
				for (Betalingsform betalingsform : salg.hentBetalingsformer()) {
					if(betalingsform.hentBetalingsformEnum().equals(BetalingsformEnum.KLIPPEKORT)) {
						lstStatistik.getItems().add("Klip brugt: " + betalingsform.hentBetaling() + ". Dato: " + salg.hentKoebsTidspunkt() + ".");
						sum += betalingsform.hentBetaling();
					}
				}
			}
		}
		
		// lblAntal
		lblAntal.setText("Der er blevet brugt " + sum + " klip i perioden.");
	}
	
	private void visKlipSolgtIEnPeriode() {
		// ListView
		lstStatistik.getItems().clear();
		int sum = 0;
		
		for (Salg salg : service.hentSalg()) {
			if(erIPerioden(salg.hentKoebsTidspunkt().toLocalDate())) { //TODO NullPointerException
				for (Pris pris : salg.hentPriser()) {
					if(pris.hentProdukt().hentNavn().equals("Klippekort")) {
					sum += 4;	// fire klip for et klippekort
					}
				}
			}
		}
		lblAntal.setText("Der er blevet solgt " + sum + " klip i perioden.");
	}
	
	private boolean erIPerioden(LocalDate dato) {
		
		if(startdato != null && slutdato != null && dato != null) {
		
			if(dato.isEqual(startdato) || dato.isAfter(startdato)) {
				if(dato.isEqual(slutdato) || dato.isBefore(slutdato)) {
					return true;
				}
			}
		}		
		return false;
	}
	
	private void tilfoejDatePickers() {
		
		if(!hboxStart.getChildren().contains(lblStartDato) && !hboxStart.getChildren().contains(dpStartDato)) {
		
		hboxStart.alignmentProperty().set(Pos.BOTTOM_CENTER);
		hboxStart.getChildren().add(lblStartDato);
		hboxStart.getChildren().add(dpStartDato);
		} 
		if(!hboxSlut.getChildren().contains(lblSlutDato) && !hboxSlut.getChildren().contains(dpSlutDato)) {
			
			hboxSlut.alignmentProperty().set(Pos.BOTTOM_CENTER);
			hboxSlut.getChildren().add(lblSlutDato);
			hboxSlut.getChildren().add(dpSlutDato);
		}	
	}
	
	private void visSolgteKlip() {
		// ListView
		lstStatistik.getItems().clear();
		int sum = 0;
		
		for (Salg salg : service.hentSalg()) {
			for (Pris pris : salg.hentPriser()) {
				if(pris.hentProdukt().hentNavn().equals("Klippekort")) {
					sum += 4;	// fire klip for et klippekort
				}
			}
		}
		// lblAntal
		lblAntal.setText("Der er blevet solgt " + sum + " klip i alt");
	}
	
	private void fjernDatePickers() {
		
		hboxStart.getChildren().remove(lblStartDato);
		hboxStart.getChildren().remove(dpStartDato);
		hboxSlut.getChildren().remove(lblSlutDato);
		hboxSlut.getChildren().remove(dpSlutDato);
	}
	
	// Actions
	private void actionVaelgStatistik() {
		
		fjernDatePickers();	// fjerner datepickers hvis der er nogen		
		
		switch (cbVaelgStatistik.getSelectionModel().getSelectedItem()) {
			case "Dagens salg":
				visDagensSalg();
				break;
			
			case "Solgte klip i en periode":
				tilfoejDatePickers();
				visKlipSolgtIEnPeriode();
				break;
				
			case "Forbrugte klip i en periode":
				tilfoejDatePickers();
				visKlipBrugtIEnPeriode();
				break;
				
			case "Alle salg":
				visAlleSalg();
				break;

			case "Alle solgte klip":
				tilfoejDatePickers();
				visSolgteKlip();
				break;
				
			case "Alle brugte klip":
				visAlleKlipBrugt();
				break;
			
			default:
				lstStatistik.getItems().clear();
				break;
		}
	}
	
	private void actionVaelgStartdato() {
		LocalDate dato = dpStartDato.getValue();
		
		if(dato != null)
			startdato = dato;
		
		if(startdato != null && slutdato != null) {
			this.visKlipBrugtIEnPeriode();
		}
	}
	
	private void actionVaelgSlutdato() {
		LocalDate dato = dpSlutDato.getValue();
		
		if(dato != null)
			slutdato = dato;
		
		if(startdato != null && slutdato != null) {
			this.visKlipBrugtIEnPeriode();
		}
	}
	
	
	@Override
	public void update() {
		actionVaelgStatistik();	
	}
	
}