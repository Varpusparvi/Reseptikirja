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
 * K�ytt�liittym�n controller
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
	 * K�sittelij�
	 */
	@FXML private void handleTallenna() {
			controller.naytaReseptitListassaMain();
	}
	
	
	/**
	 * K�sittelij�
	 */
	@FXML private void handleEtsi() {
		hae();
	}
	
	
	/**
	 * K�sittelij�
	 */
	@FXML private void handleLisaa() {
		String lisattavanimi = Dialogs.showInputDialog("Anna reseptille nimi", "");
		lisaaResepti(lisattavanimi);
	}
	
	
	/**
	 * K�sittelij� lis�� aineksen reseptist�
	 */
	@FXML private void handleLisaaAines() {
		if (chooserReseptitResepti.getSelectedObject() == null) return;
		lisaaAinesReseptiin();
	}

	
	/**
	 * K�sittelij� poistaa aineksen reseptist�
	 */
	@FXML private void handlePoistaAines() {
		if (chooserReseptitResepti.getSelectedObject() == null) return;
		poistaAinesReseptista();
	}


	/**
	 * K�sittelij�
	 */
	@FXML private void handlePoista() {
		if (Dialogs.showQuestionDialog("Oletko Varma?", "Haluatko varmasti poistaa?", "Kyll�", "Ei")) {			
			poistaResepti();
		}
	}
	
	
	/**
	 * K�sittelij�, sulkee reseptin muokkausikkunan
	 */
	@FXML private void handleLopetaMuokkaaResepti() {
		Stage stage = (Stage) menuBarMuokkaaResepti.getScene().getWindow();
		stage.hide();
		controller.naytaReseptitListassaMain();
	}
	
	
	// K�ytt�liittym��n suoraan littym�t�nt� koodia
	//======================================================================================================================
	// K�ytt�liittym��n suoraan littym�t�nt� koodia
	
	
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
            secondaryStage.setTitle("Lis�� aines");
            
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
     * yksi iso tekstikentt�, johon voidaan tulostaa j�senten tiedot.
     * Alustetaan my�s j�senlistan kuuntelija 
     */
    public void alusta() {
    	naytaReseptitListassaResepti();
    	if (chooserReseptitResepti == null) return;
    	
    	chooserReseptitResepti.addSelectionListener(e -> naytaResepti());
    }
    
    
    /**
     * N�ytt�� listasta valitun reseptin tiedot, tilap�isesti yhteen isoon edit-kentt��n
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
     * Listaa reseptit p��ikkunan listaan
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
     * Lis�� reseptin taulukkoon annetulla nimell�
     * @param nimi
     */
    private void lisaaResepti(String nimi) {
    	if (reseptikirja == null) return;
    	if (nimi == null) return;
    	
        if (nimi.contains("|")) {
            Dialogs.showMessageDialog("Ei voi sis�lt�� '|'-merkkej�");
            return;
        }
    	
    	Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), nimi);
    	resepti.tallennaResepti();
    	reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti);
    	naytaReseptitListassaResepti();
    }
    
    
    /**
     * Aloittaa aineksen lis��misen reseptiin
     */
	private void lisaaAinesReseptiin() {
		lisaaAinesIkkuna(lisaaAinesTaso);
	}
    
    
    /**
     * Lis�� aineksen reseptiin
     * @param aines aines joka lis�t��n reseptiin
     */
	public void lisaaAinesReseptiin(Aines aines) {
		lisaaAinesTaso.hide();
		
		reseptiKohdalla = chooserReseptitResepti.getSelectedObject();
		reseptiKohdalla.setAines(reseptiKohdalla.getAineslista().size()+1, aines);
		naytaResepti();
	}
    
	
	/**
	 * Aloittaa aineksen poistamisen reseptist�
	 */
	private void poistaAinesReseptista() {
		reseptiKohdalla = chooserReseptitResepti.getSelectedObject();
		poistaAinesIkkuna(poistaAinesTaso, reseptiKohdalla);
	}
	
	
	/**
	 * Poistaa reseptist� aineksen
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
		// Lis�t��n listaan reseptit joiden nimen osa vastaa ehtoa
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
