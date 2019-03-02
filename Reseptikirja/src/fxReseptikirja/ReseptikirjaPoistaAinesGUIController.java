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
 * K‰yttˆliittym‰n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaPoistaAinesGUIController implements Initializable {

	
	@FXML private MenuBar menuBarPoistaAines;
	@FXML private ListChooser<Aines> chooserAineksetPoistaAines;
	@FXML private TextField hakuehto;
	
	
	/**
	 * K‰sittelij‰
	 */
	@FXML private void handleEtsi() {
		hae();
	}
	
	
	/**
	 * K‰sittelij‰
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
	 * K‰sittelij‰, sulkee aineksien muokkausikkunan
	 */
	@FXML private void handleSulje() {
		Stage stage = (Stage) menuBarPoistaAines.getScene().getWindow();
		stage.hide();
	}

	
	// K‰yttˆliittym‰‰n suoraan littym‰tˆnt‰ koodia
	//======================================================================================================================
	// K‰yttˆliittym‰‰n suoraan littym‰tˆnt‰ koodia
	
	
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
	 * yksi iso tekstikentt‰, johon voidaan tulostaa j‰senten tiedot.
	 * Alustetaan myˆs j‰senlistan kuuntelija 
	 */
	public void alusta() {
		naytaAineksetListassaPoistaAines();
		if (chooserAineksetPoistaAines == null) return;
		
		chooserAineksetPoistaAines.addSelectionListener(e -> naytaAines());
	}
	 
	
	/**
	 * Asettaa controllerille k‰sitet‰v‰n reseptin
	 * @param resepti jota k‰sitell‰‰n
	 */
	public void setResepti(Resepti resepti) {
		this.resepti = resepti;
	}
    
    
    /**
     * N‰ytt‰‰ listasta valitun reseptin tiedot, tilap‰isesti yhteen isoon edit-kentt‰‰n
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
	 * Asettaa k‰ytett‰v‰n reseptikirjan
	 * @param reseptikirja jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
	 */
    public void setReseptikirja(Reseptikirja reseptikirja) {
        this.reseptikirja  = reseptikirja;
    }
	
	
	/**
	 * Asettaa ylemm‰n controllerin
	 * @param control  controller jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
	 */
    public void setController(ReseptikirjaMuokkaaReseptiGUIController control) {
        this.controller  = control;
    }
    
    
    /**
     * Hakukent‰n toiminta. Sulkee aineket, jotka eiv‰t vastaa, pois listasta.
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
		// Lis‰t‰‰n listaan reseptit joiden nimen osa vastaa ehtoa
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

	