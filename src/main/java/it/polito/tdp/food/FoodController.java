package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	int x;
        try {
        	x=Integer.parseInt(txtPassi.getText());
        	
        }
        catch(NumberFormatException e) {
        	txtResult.appendText("Inserire valore numerico");
        	return;
        }
        List<Vicino> cammino=new ArrayList<>(this.model.cercaCammino(x, boxPorzioni.getValue()));
        for(Vicino v: cammino) {
        	txtResult.appendText("\n"+v.getN());
        }
        txtResult.appendText("\nPeso totale: "+this.model.calcolaPeso(cammino));
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...");
    	if(this.model.getGrafo()==null||boxPorzioni.getValue()==null) {
    		txtResult.appendText("\nCreare grafo e selezionare tipo porzione");
    	}
    	else {
    		List<Vicino> vicini=this.model.connessi(boxPorzioni.getValue());
    		for(Vicino v:vicini) {
    			txtResult.appendText("\n Tipo: "+v.getN()+" peso: "+v.getPeso());
    		}
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	int x;
        try {
        	x=Integer.parseInt(txtCalorie.getText());
        	
        }
        catch(NumberFormatException e) {
        	txtResult.appendText("Inserire valore numerico");
        	return;
        }
        this.model.creaGrafo( Integer.parseInt(txtCalorie.getText()));
        txtResult.setText("Vertici: "+this.model.getGrafo().vertexSet().size()+"  Archi"+this.model.getGrafo().edgeSet().size());
        List<String> nomi=new ArrayList<String>();
        for(String s:this.model.getGrafo().vertexSet())
        		nomi.add(s);
        Collections.sort(nomi);
    	for(String s:nomi ) {
    		boxPorzioni.getItems().add(s);
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
