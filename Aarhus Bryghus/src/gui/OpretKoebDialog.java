package gui;

import java.util.ArrayList;

import applikation.model.Betalingsform;
import applikation.model.BetalingsformEnum;
import applikation.model.Salg;
import applikation.model.TilstandEnum;
import applikation.model.Udlejning;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretKoebDialog extends Stage {

	private Salg salg;
	private ArrayList<CheckBox> ckBoxBetalingsformer;
	private ArrayList<TextField> txfBetalingInputList;
	private ArrayList<Betalingsform> valgteBetalingsformer;
	private Label lblSalgsPris;
	private Button btnOpret;
	private Button btnAnnuller;
	private Label lblError;
	
	public OpretKoebDialog(Salg salg) {
		
		this.salg = salg;
		ckBoxBetalingsformer = new ArrayList<CheckBox>();
		txfBetalingInputList = new ArrayList<TextField>();
		valgteBetalingsformer = new ArrayList<Betalingsform>();
		lblSalgsPris = new Label("Salgspris: ");
		btnOpret = new Button("Betal");
		btnAnnuller = new Button("Annuller");
		lblError = new Label();
		
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle("Betaling");
		
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
		
		if(salg instanceof Udlejning && ((Udlejning) salg).hentForudBetalt() == false) {
			if(((Udlejning) salg).hentTilstand().equals(TilstandEnum.HJEMME)){
				this.lblSalgsPris.setText("Beløb: " + Double.toString(((Udlejning) salg).samletPantPris()) + " dkk");
			}
		} else {
			this.lblSalgsPris.setText("Beløb: " + Double.toString(salg.samletPris()) + " dkk");
		}
		
		pane.add(lblSalgsPris, 0, 0);
		
		for (int i = 0; i < BetalingsformEnum.values().length; i++) {
			CheckBox checkbox = new CheckBox(BetalingsformEnum.values()[i].name());
			pane.add(checkbox, i, 1);
			ckBoxBetalingsformer.add(checkbox);
			
			Label lblFormNavn = new Label();
			pane.add(lblFormNavn, i, 2);
			TextField txfBetalInput = new TextField();
			pane.add(txfBetalInput, i, 3);
			txfBetalingInputList.add(txfBetalInput);	
		}
		
		btnOpret.setOnAction(action -> actionBetal());
		pane.add(btnOpret, 0, 4);
		btnAnnuller.setOnAction(action -> this.hide());
		pane.add(btnAnnuller, 1, 4);
		
		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 5, 4, 1);
	}
	
	
	// skal lige gøre det hele generelt lidt pænere
	private boolean kanBetale() {
		boolean checkForm = false;	// minimum en betalingsform
		
		valgteBetalingsformer.clear();	
		
		for (int i = 0; i < ckBoxBetalingsformer.size(); i++) {
			if(ckBoxBetalingsformer.get(i).isSelected()) {
				checkForm = true;
				
				for (TextField textField : txfBetalingInputList) {
					
					if(!textField.getText().isEmpty()) {
						
						BetalingsformEnum betalingsformEnum = BetalingsformEnum.values()[i];
						double betaling = Double.parseDouble(textField.getText());
						
						this.valgteBetalingsformer.add(new Betalingsform(betalingsformEnum, betaling));
					}
				}
			}
		}
		
		if(checkForm) {
			if(salg instanceof Udlejning) {
				if(((Udlejning) salg).kanBetalesPant(valgteBetalingsformer) 
						&& ((Udlejning)salg).hentForudBetalt() == false) {
					
					return true;
				}
				
			} if(salg.kanBetales(valgteBetalingsformer)) {
				return true;
			
			} else {
				lblError.setText("Betalingen fordelt mellem betalingsmidlerne går ikke op med salgets beløb.");
				return false;
			}
		} 
		
		System.out.println(checkForm);
		this.lblError.setText("Vælg venligst mindst én betalingsmetode.");
		return false;
	}
	
	// Button Actions
	
	private void actionBetal() {
		if(kanBetale()) {
			salg.betal(this.valgteBetalingsformer);
			this.hide();
		}
	}
}
