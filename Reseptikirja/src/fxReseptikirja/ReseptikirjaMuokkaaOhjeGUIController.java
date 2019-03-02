package fxReseptikirja;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reseptikirja.Ohje;
import reseptikirja.Resepti;
import reseptikirja.Reseptikirja;

/**
 * Kï¿½yttï¿½liittymï¿½n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com 
 * 18.4.2018
 */
public class ReseptikirjaMuokkaaOhjeGUIController implements Initializable {

	
	@FXML private ListChooser<Resepti> chooserReseptitOhje;
	@FXML private MenuBar menuBarMuokkaaOhje;
	@FXML private TextArea textAreaOhje;
	@FXML private TextField textFieldArvostelu;
	@FXML private TextField hakuehto;
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleTallenna() {
		setOhje();
		setArvostelu();
		
		naytaReseptitListassaOhje();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleEtsi() {
		hae();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handlePoista() {
		Dialogs.showQuestionDialog("Oletko Varma?", "Haluatko varmasti poistaa?", "Kyllä", "Ei");
	}
	
	
	/**
	 * Kï¿½sittelijï¿½, sulkee ohjeen muokkausikkunan
	 */
	@FXML private void handleLopetaMuokkaaOhje() {
		Stage stage = (Stage) menuBarMuokkaaOhje.getScene().getWindow();
		stage.hide();
		controller.naytaReseptitListassaMain();
	}
	
	
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	//======================================================================================================================
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	
	
	private ReseptikirjaGUIController controller;
	private Reseptikirja reseptikirja;
	private Resepti reseptiKohdalla;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alusta();
	}
	
	
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttï¿½, johon voidaan tulostaa jï¿½senten tiedot.
     * Alustetaan myï¿½s jï¿½senlistan kuuntelija 
     */
	public void alusta() {
    	naytaReseptitListassaOhje();
    	if (chooserReseptitOhje == null) return;
    	
    	chooserReseptitOhje.addSelectionListener(e -> naytaResepti());
    }
    
    
    /**
     * Nï¿½yttï¿½ï¿½ listasta valitun reseptin tiedot, tilapï¿½isesti yhteen isoon edit-kenttï¿½ï¿½n
     */
    private void naytaResepti() {
    	reseptiKohdalla = chooserReseptitOhje.getSelectedObject();
    	if (reseptiKohdalla == null) return;
        
        naytaOhjeKentassa();
    }
	
	
    /**
     * Listaa reseptit ohjeikkunan listaan
     */
    private void naytaReseptitListassaOhje() {
    	
    	if (reseptikirja == null) return;
    	if (chooserReseptitOhje == null) return;
    	
    	chooserReseptitOhje.clear();
    	
    	if (reseptikirja.getReseptitLuokka().getReseptit() == null || reseptikirja.getReseptitLuokka().getReseptit().length < 1) return;
    	for (int i = 0; i < reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getReseptitLuokka().getReseptit()[i] == null) break;
    		chooserReseptitOhje.add(reseptikirja.getReseptitLuokka().getReseptit()[i]);
    	}
    }
    
    
    /**
     * Nï¿½yttï¿½ï¿½ reseptin ohjeen tekstikentï¿½ssï¿½
     */
    private void naytaOhjeKentassa() {
    	if (reseptikirja == null) return;
    	if (chooserReseptitOhje == null) return;
    	reseptiKohdalla = chooserReseptitOhje.getSelectedObject();
    	reseptikirja.getReseptinOhje(reseptiKohdalla);
    	
    	textAreaOhje.setText(reseptikirja.getReseptinOhje(reseptiKohdalla));
    	textFieldArvostelu.setText(reseptikirja.getReseptinArvostelu(reseptiKohdalla));
    }
    
    
    /**
     * Asettaa reseptille ohjeen
     */
    public void setOhje() {
    	reseptiKohdalla = chooserReseptitOhje.getSelectedObject();
    	if (reseptikirja == null || chooserReseptitOhje == null || reseptiKohdalla == null) return;
    	
    	if (textAreaOhje.getText().contains("\n") || textAreaOhje.getText().contains("|")) {
    	    Dialogs.showMessageDialog("Ei saa käyttää rivivaihtoa, eikä '|'-merkkejä!");
    	    return;
    	}
    	
    	Ohje ohje = new Ohje(reseptikirja.getOhjeetLuokka());
    	reseptikirja.getOhjeetLuokka().getOhjeet().add(ohje);
    	ohje.setOhje(textAreaOhje.getText());
    	
    	reseptiKohdalla.setOhje(ohje.getOhjeId());
    	ohje.tallennaOhje();
    }
    
    
    /**
     * Asettaa reseptille arvostelun
     */
    public void setArvostelu() {
    	if (reseptikirja == null || chooserReseptitOhje == null || textFieldArvostelu == null) return;
    	
    	Resepti kohdeResepti = chooserReseptitOhje.getSelectedObject();
    	String s = textFieldArvostelu.getText();
    	if (s.isEmpty() == true) {
    		return;
    	}
    	
    	try {
    		if (Integer.valueOf(s) < 0 || Integer.valueOf(s) > 5) {
    			Dialogs.showMessageDialog("Arvostelu väärässä muodossa, yritä kokonaislukua 0-5");
    		} else {    			
    			kohdeResepti.setArvostelu(Integer.valueOf(textFieldArvostelu.getText()));
    		}
    	} catch (Exception e) {
    		Dialogs.showMessageDialog("Arvostelu väärässä muodossa, yritä kokonaislukua 0-5");
    	}
    }
	
	
	/**
	 * Asettaa kï¿½ytettï¿½vï¿½n reseptikirjan
	 * @param reseptikirja jota kï¿½ytetï¿½ï¿½n tï¿½ssï¿½ kï¿½yttï¿½liittymï¿½ssï¿½
	 */
    public void setReseptikirja(Reseptikirja reseptikirja) {
        this.reseptikirja  = reseptikirja;
    }
    
    
	/**
	 * Asettaa ylemmï¿½n controllerin
	 * @param control  controller jota kï¿½ytetï¿½ï¿½n tï¿½ssï¿½ kï¿½yttï¿½liittymï¿½ssï¿½
	 */
    public void setController(ReseptikirjaGUIController control) {
        this.controller  = control;
    }
    
    
	/**
	 * Hakee reseptien tiedot listaan
	 */
	private void hae() {
		
		StringBuilder ehto = new StringBuilder(hakuehto.getText());
		ehto.insert(0, ".*");
		ehto.append(".*");
		String s = ehto.toString().toUpperCase();
		naytaReseptitListassaOhje();
		List<Resepti> reseptit = chooserReseptitOhje.getObjects();
		chooserReseptitOhje.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < reseptit.size(); i++) {
			if (!reseptit.get(i).getReseptiNimi().toUpperCase().matches(s)) {
				reseptit.remove(i);
				i--;
			}
		}
		// Lisï¿½tï¿½ï¿½n listaan reseptit joiden nimen osa vastaa ehtoa
		for (Resepti resepti : reseptit) {
			chooserReseptitOhje.add(resepti);
		}
	}
	
    
	/**
	 * Avaa sovelluksen
	 * @return boolean
	 */
	public boolean avaa() {
		return true;
	}
	
	
	/**
	 * Tarkistetaan onko tallennus tehty
	 * @return true jos saa sulkea sovelluksen, false jos ei
	 */
	public boolean voikoSulkea() {
		handleTallenna();
		return true;
	}
}
