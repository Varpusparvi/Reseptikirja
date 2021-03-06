package fxReseptikirja;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reseptikirja.Aines;
import reseptikirja.Resepti;
import reseptikirja.Reseptikirja;

/**
 * Käyttöliittymän controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaPoistaAinesGUIController implements Initializable {

	
	@FXML private MenuBar menuBarPoistaAines;
	@FXML private ListChooser<Aines> chooserAineksetPoistaAines;
	@FXML private TextField hakuehto;
	
	
	/**
	 * Käsittelijä
	 */
	@FXML private void handleEtsi() {
		hae();
	}
	
	
	/**
	 * Käsittelijä
	 */
	@FXML private void handleValitse() {
		if (chooserAineksetPoistaAines.getSelectedObject() == null) {
			Stage stage = (Stage) menuBarPoistaAines.getScene().getWindow();
			stage.hide();
			return;
		}
		controller.poistaAinesReseptista(chooserAineksetPoistaAines.getSelectedObject());
	}
	
	
	/**
	 * Käsittelijä, sulkee aineksien muokkausikkunan
	 */
	@FXML private void handleSulje() {
		Stage stage = (Stage) menuBarPoistaAines.getScene().getWindow();
		stage.hide();
	}

	
	// Käyttöliittymään suoraan littymätöntä koodia
	//======================================================================================================================
	// Käyttöliittymään suoraan littymätöntä koodia
	
	
	private ReseptikirjaMuokkaaReseptiGUIController controller;
	private Reseptikirja reseptikirja;
	private Aines ainesKohdalla;
	private Resepti resepti;
	
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();      
	}
	
	
	/**
	 * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
	 * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
	 * Alustetaan myös jäsenlistan kuuntelija 
	 */
	public void alusta() {
		naytaAineksetListassaPoistaAines();
		if (chooserAineksetPoistaAines == null) return;
		
		chooserAineksetPoistaAines.addSelectionListener(e -> naytaAines());
	}
	 
	
	/**
	 * Asettaa controllerille käsitetävän reseptin
	 * @param resepti jota käsitellään
	 */
	public void setResepti(Resepti resepti) {
		this.resepti = resepti;
	}
    
    
    /**
     * Näyttää listasta valitun reseptin tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaAines() {
    	if (ainesKohdalla == null) return;
    	
        ainesKohdalla = chooserAineksetPoistaAines.getSelectedObject();
    }
    
    
    /**
     * Listaa ainekset ikkunan listaan
     */
    private void naytaAineksetListassaPoistaAines() {
    	if (reseptikirja == null) return;
    	if (chooserAineksetPoistaAines == null) return;
    	
    	chooserAineksetPoistaAines.clear();
    	List<Integer> ainekset = resepti.getAineslista();
    	
    	for (int i = 0; i < ainekset.size(); i++) {
    		chooserAineksetPoistaAines.add(reseptikirja.getAinesIdsta(ainekset.get(i)));
    		}
    	}
    
    
	/**
	 * Asettaa käytettävän reseptikirjan
	 * @param reseptikirja jota käytetään tässä käyttöliittymässä
	 */
    public void setReseptikirja(Reseptikirja reseptikirja) {
        this.reseptikirja  = reseptikirja;
    }
	
	
	/**
	 * Asettaa ylemmän controllerin
	 * @param control  controller jota käytetään tässä käyttöliittymässä
	 */
    public void setController(ReseptikirjaMuokkaaReseptiGUIController control) {
        this.controller  = control;
    }
    
    
    /**
     * Hakukentän toiminta. Sulkee aineket, jotka eivät vastaa, pois listasta.
     */
	private void hae() {
		
		StringBuilder ehto = new StringBuilder(hakuehto.getText());
		ehto.insert(0, ".*");
		ehto.append(".*");
		String s = ehto.toString().toUpperCase();
		naytaAineksetListassaPoistaAines();
		List<Aines> ainekset = chooserAineksetPoistaAines.getObjects();
		chooserAineksetPoistaAines.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < ainekset.size(); i++) {
			if (!ainekset.get(i).getAinesNimi().toUpperCase().matches(s)) {
				ainekset.remove(i);
				i--;
			}
		}
		// Lisätään listaan reseptit joiden nimen osa vastaa ehtoa
		for (Aines aines : ainekset) {
			chooserAineksetPoistaAines.add(aines);
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
		return true;
	}
}

	