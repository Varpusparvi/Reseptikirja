package fxReseptikirja;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import reseptikirja.Aines;
import reseptikirja.Resepti;
import reseptikirja.Reseptikirja;

/**
 * Kï¿½yttï¿½liittymï¿½n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaMuokkaaReseptiGUIController implements Initializable {

	
	@FXML private MenuBar menuBarMuokkaaResepti;
	@FXML private ListChooser<Resepti> chooserReseptitResepti;
	@FXML private ListChooser<Aines> chooserAineksetResepti;
	@FXML private StringGrid<Aines> stringGridResepti;
	@FXML private TextField hakuehto;
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleTallenna() {
			controller.naytaReseptitListassaMain();
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
	@FXML private void handleLisaa() {
		String lisattavanimi = Dialogs.showInputDialog("Anna reseptille nimi", "");
		lisaaResepti(lisattavanimi);
	}
	
	
	/**
	 * Kï¿½sittelijï¿½ lisï¿½ï¿½ aineksen reseptistï¿½
	 */
	@FXML private void handleLisaaAines() {
		if (chooserReseptitResepti.getSelectedObject() == null) return;
		lisaaAinesReseptiin();
	}

	
	/**
	 * Kï¿½sittelijï¿½ poistaa aineksen reseptistï¿½
	 */
	@FXML private void handlePoistaAines() {
		if (chooserReseptitResepti.getSelectedObject() == null) return;
		poistaAinesReseptista();
	}


	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handlePoista() {
		if (Dialogs.showQuestionDialog("Oletko Varma?", "Haluatko varmasti poistaa?", "Kyllä", "Ei")) {			
			poistaResepti();
		}
	}
	
	
	/**
	 * Kï¿½sittelijï¿½, sulkee reseptin muokkausikkunan
	 */
	@FXML private void handleLopetaMuokkaaResepti() {
		Stage stage = (Stage) menuBarMuokkaaResepti.getScene().getWindow();
		stage.hide();
		controller.naytaReseptitListassaMain();
	}
	
	
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	//======================================================================================================================
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	
	
	private Reseptikirja reseptikirja;
	private ReseptikirjaGUIController controller;
	private Stage lisaaAinesTaso = new Stage(); 
	private Stage poistaAinesTaso = new Stage();
	private Resepti reseptiKohdalla;


	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();      
	}
	
	
	/**
	 * Avaa aineksen valinta ikkunan
	 * @param secondaryStage
	 */
	private void lisaaAinesIkkuna(Stage secondaryStage)
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaLisaaAinesGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaLisaaAinesGUIController lisaaAinesCtrl = (ReseptikirjaLisaaAinesGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Lisää aines");
            
            lisaaAinesCtrl.setReseptikirja(reseptikirja);
            lisaaAinesCtrl.setController(this);

            secondaryStage.setOnCloseRequest((event) -> {
                    if ( !lisaaAinesCtrl.voikoSulkea() ) event.consume();
                });
            
            secondaryStage.show();
            lisaaAinesCtrl.alusta();
            
            if ( !lisaaAinesCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Avaa aineksen valinta ikkunan
	 * @param secondaryStage
	 */
	private void poistaAinesIkkuna(Stage secondaryStage, Resepti resepti)
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaPoistaAinesGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaPoistaAinesGUIController poistaAinesCtrl = (ReseptikirjaPoistaAinesGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Poista aines");
            
            poistaAinesCtrl.setReseptikirja(reseptikirja);
            poistaAinesCtrl.setController(this);
            poistaAinesCtrl.setResepti(resepti);

            secondaryStage.setOnCloseRequest((event) -> {
                    if ( !poistaAinesCtrl.voikoSulkea() ) event.consume();
                });
            
            secondaryStage.show();
            poistaAinesCtrl.alusta();
            
            if ( !poistaAinesCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttï¿½, johon voidaan tulostaa jï¿½senten tiedot.
     * Alustetaan myï¿½s jï¿½senlistan kuuntelija 
     */
    public void alusta() {
    	naytaReseptitListassaResepti();
    	if (chooserReseptitResepti == null) return;
    	
    	chooserReseptitResepti.addSelectionListener(e -> naytaResepti());
    }
    
    
    /**
     * Nï¿½yttï¿½ï¿½ listasta valitun reseptin tiedot, tilapï¿½isesti yhteen isoon edit-kenttï¿½ï¿½n
     */
    private void naytaResepti() {
    	if (reseptikirja == null || chooserReseptitResepti == null) return;
    	
    	reseptiKohdalla = chooserReseptitResepti.getSelectedObject();
    	if (reseptiKohdalla == null) return;
    	chooserAineksetResepti.clear();
    	
    	List<Aines> ainekset = reseptikirja.getReseptinAinesLista(reseptiKohdalla);
    	
    	for (int i = 0; i < ainekset.size(); i++) {
    		chooserAineksetResepti.add(ainekset.get(i));
    	}
    }
    
    
    /**
     * Listaa reseptit pï¿½ï¿½ikkunan listaan
     */
    private void naytaReseptitListassaResepti() {
    	if (reseptikirja == null) return;
    	if (chooserReseptitResepti == null) return;
    	
    	chooserReseptitResepti.clear();
    	
    	if (reseptikirja.getReseptitLuokka().getReseptit() == null || reseptikirja.getReseptitLuokka().getReseptit().length < 1) return;
    	for (int i = 0; i < reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getReseptitLuokka().getReseptit()[i] == null) break;
    		chooserReseptitResepti.add(reseptikirja.getReseptitLuokka().getReseptit()[i]);
    	}
    }
    
    
    /**
     * Lisï¿½ï¿½ reseptin taulukkoon annetulla nimellï¿½
     * @param nimi
     */
    private void lisaaResepti(String nimi) {
    	if (reseptikirja == null) return;
    	if (nimi == null) return;
    	
        if (nimi.contains("|")) {
            Dialogs.showMessageDialog("Ei voi sisältää '|'-merkkejä");
            return;
        }
    	
    	Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), nimi);
    	resepti.tallennaResepti();
    	reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti);
    	naytaReseptitListassaResepti();
    }
    
    
    /**
     * Aloittaa aineksen lisï¿½ï¿½misen reseptiin
     */
	private void lisaaAinesReseptiin() {
		lisaaAinesIkkuna(lisaaAinesTaso);
	}
    
    
    /**
     * Lisï¿½ï¿½ aineksen reseptiin
     * @param aines aines joka lisï¿½tï¿½ï¿½n reseptiin
     */
	public void lisaaAinesReseptiin(Aines aines) {
		lisaaAinesTaso.hide();
		
		reseptiKohdalla = chooserReseptitResepti.getSelectedObject();
		reseptiKohdalla.setAines(reseptiKohdalla.getAineslista().size()+1, aines);
		naytaResepti();
	}
    
	
	/**
	 * Aloittaa aineksen poistamisen reseptistï¿½
	 */
	private void poistaAinesReseptista() {
		reseptiKohdalla = chooserReseptitResepti.getSelectedObject();
		poistaAinesIkkuna(poistaAinesTaso, reseptiKohdalla);
	}
	
	
	/**
	 * Poistaa reseptistï¿½ aineksen
	 * @param aines joka poistetaan
	 */
	public void poistaAinesReseptista(Aines aines) {
		poistaAinesTaso.hide();
		
		reseptiKohdalla = chooserReseptitResepti.getSelectedObject();

		for (int i = 0; i < reseptiKohdalla.getAineslista().size(); i++) {
			if (reseptiKohdalla.getAineslista().get(i) == aines.getAinesId()) {
				reseptiKohdalla.getAineslista().remove(i);
			}
		}
		naytaResepti();
	}
    
	
    /**
     * Poistaa valitun reseptin
     */
    private void poistaResepti() {
    	if (reseptikirja == null || chooserReseptitResepti == null || chooserReseptitResepti.getSelectedObject() == null) return;
    	
    	int poistettavaId = chooserReseptitResepti.getSelectedObject().getReseptiId();
    	for (int i = 0; i < reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getReseptitLuokka().getReseptit()[i].getReseptiId() == poistettavaId) {
    			reseptikirja.getReseptitLuokka().getReseptit()[i] = null;
    			break;
    		}
    	}
    	reseptikirja.getReseptitLuokka().jarjestaReseptit();
    	naytaReseptitListassaResepti();
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
		naytaReseptitListassaResepti();
		List<Resepti> reseptit = chooserReseptitResepti.getObjects();
		chooserReseptitResepti.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < reseptit.size(); i++) {
			if (!reseptit.get(i).getReseptiNimi().toUpperCase().matches(s)) {
				reseptit.remove(i);
				i--;
			}
		}
		// Lisï¿½tï¿½ï¿½n listaan reseptit joiden nimen osa vastaa ehtoa
		for (Resepti resepti : reseptit) {
			chooserReseptitResepti.add(resepti);
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
