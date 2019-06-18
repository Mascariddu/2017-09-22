package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model = new Model();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Season> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	
    	Season anno = this.boxAnno.getValue();
    	txtResult.clear();
    	
    	if(anno != null) {
    		
    		model.creaGrafo(anno);
    		for(String string : model.getMigliore()) 
    			txtResult.appendText(string+"\n");
    		this.boxGara.getItems().clear();
    		this.boxGara.getItems().addAll(model.getRaces(anno));
    		
    	} else txtResult.appendText("Seleziona almneo ua stagione!");
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	
    	try {
    	Race race = this.boxGara.getValue();
    	double p = Double.parseDouble(textInputK.getText());
    	double t = Double.parseDouble(textInputK.getText());
    	txtResult.clear();
    	
    	if(race != null && p!= 0.0 && t!=0.0) {
    		
    		model.simula(race,p,t);
    		List<Driver> drivers = new ArrayList<Driver>(model.getPunteggi().keySet());
    		Collections.sort(drivers, new Comparator<Driver>() {

				@Override
				public int compare(Driver o1, Driver o2) {
					// TODO Auto-generated method stub
					return o1.getDriverId() - o2.getDriverId();
				}
    			
			});
    		
    		for(Driver driver : drivers)
    			txtResult.appendText(driver.toString()+" con punteggio: "+model.getPunteggi().get(driver)+"\n");
    		
    	} else txtResult.appendText("Inserisci i dati richiesti");
    	} catch (NumberFormatException e) {
			// TODO: handle exception
    		e.printStackTrace();
    		txtResult.appendText("Inserisci i dati richiesti");
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getSeasons());
	}
}
