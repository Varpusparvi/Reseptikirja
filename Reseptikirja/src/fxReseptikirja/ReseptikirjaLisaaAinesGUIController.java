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
import reseptikirja.Reseptikirja;

/**
 * K�ytt�liittym�n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaLisaaAinesGUIController implements Initializable {

	
	@FXML private MenuBar menuBarLisaaAines;
	@FXML private ListChooser<Aines> chooserAineksetLisaaAines;
	@FXML private TextField hakuehto;
	
	
	/**
	 * K�sittelij�
	 */
	@FXML private void handleEtsi() {
		hae();
	}
	
	
	/**
	 * K�sittelij�
	 */
	@FXML private void handleValitse() {
		controller.lisaaAinesReseptiin(chooserAineksetLisaaAines.getSelectedObject());
	}
	
	
	/**
	 * K�sittelij�, sulkee aineksien muokkausikkunan
	 */
	@FXML private void handleSulje() {
		Stage stage = (Stage) menuBarLisaaAines.getScene().getWindow();
		stage.hide();
	}

	
	// K�ytt�liittym��n suoraan littym�t�nt� koodia
	//======================================================================================================================
	// K�ytt�liittym��n suoraan littym�t�nt� koodia
	
	
	private ReseptikirjaMuokkaaReseptiGUIController controller;
	private Reseptikirja reseptikirja;
	private Aines ainesKohdalla;
	
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();      
	}
	
	
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikentt�, johon voidaan tulostaa j�senten tiedot.
     * Alustetaan my�s j�senlistan kuuntelija 
     */
	public void alusta() {
		naytaAineksetListassaLisaaAines();
    	if (chooserAineksetLisaaAines == null) return;
    	
    	chooserAineksetLisaaAines.addSelectionListener(e -> naytaAines());
    }
    
    
    /**
     * N�ytt�� listasta valitun reseptin tiedot, tilap�isesti yhteen isoon edit-kentt��n
     */
    private void naytaAines() {
    	if (ainesKohdalla == null) return;
    	
        ainesKohdalla = chooserAineksetLisaaAines.getSelectedObject();
    }
    
    
    /**
     * Listaa ainekset ikkunan listaan
     */
    private void naytaAineksetListassaLisaaAines() {
    	if (reseptikirja == null) return;
    	if (chooserAineksetLisaaAines == null) return;
    	
    	chooserAineksetLisaaAines.clear();
    	
    	if (reseptikirja.getAineksetLuokka().getAinekset() == null || reseptikirja.getAineksetLuokka().getAinekset().length < 1) return;
    	for (int i = 0; i < reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getAineksetLuokka().getAinekset()[i] == null) break;
    		chooserAineksetLisaaAines.add(reseptikirja.getAineksetLuokka().getAinekset()[i]);
    	}
    }
    
    
	/**
	 * Asettaa k�ytett�v�n reseptikirjan
	 * @param reseptikirja jota k�ytet��n t�ss� k�ytt�liittym�ss�
	 */
    public void setReseptikirja(Reseptikirja reseptikirja) {
        this.reseptikirja  = reseptikirja;
    }
	
	
	/**
	 * Asettaa ylemm�n controllerin
	 * @param control  controller jota k�ytet��n t�ss� k�ytt�liittym�ss�
	 */
    public void setController(ReseptikirjaMuokkaaReseptiGUIController control) {
        this.controller  = control;
    }
    
    
    /**
     * Hakukent�n toiminta. Sulkee aineket, jotka eiv�t vastaa, pois listasta.
     */
	private void hae() {
		
		StringBuilder ehto = new StringBuilder(hakuehto.getText());
		ehto.insert(0, ".*");
		ehto.append(".*");
		String s = ehto.toString().toUpperCase();
		naytaAineksetListassaLisaaAines();
		List<Aines> ainekset = chooserAineksetLisaaAines.getObjects();
		chooserAineksetLisaaAines.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < ainekset.size(); i++) {
			if (!ainekset.get(i).getAinesNimi().toUpperCase().matches(s)) {
				ainekset.remove(i);
				i--;
			}
		}
		// Lis�t��n listaan reseptit joiden nimen osa vastaa ehtoa
		for (Aines aines : ainekset) {
			chooserAineksetLisaaAines.add(aines);
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

	