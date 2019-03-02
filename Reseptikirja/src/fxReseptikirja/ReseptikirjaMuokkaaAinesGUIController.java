package fxReseptikirja;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import reseptikirja.Aines;
import reseptikirja.Lukija;
import reseptikirja.Reseptikirja;

/**
 * Kï¿½yttï¿½liittymï¿½n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaMuokkaaAinesGUIController implements Initializable {

	
	@FXML private MenuBar menuBarMuokkaaAines;
	@FXML private ListChooser<Aines> chooserAineksetAines;
	@FXML private TextField hakuehto;
	@FXML private Text textAines;
	@FXML private ComboBoxChooser<String> cbGluteeni;
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleTallenna() {
		tallennaAines();
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
		String lisattavanimi = Dialogs.showInputDialog("Anna ainekselle nimi", "");
		
		lisaaAines(lisattavanimi);
		naytaAineksetListassaAines();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½  
	 * Aineksen poistaminen ei saisi hï¿½vittï¿½ï¿½ ainesta tiedostosta, vaan pelkï¿½stï¿½ï¿½n reseptistï¿½.
	 */
	@FXML private void handlePoista() {
		if (Dialogs.showQuestionDialog("Oletko Varma?", "Haluatko varmasti poistaa?", "Kyllä", "Ei")) {			
			poistaAines();
		}
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleGluteeni() {
		if ((ainesKohdalla = chooserAineksetAines.getSelectedObject()) == null) return;
		
		if (ainesKohdalla.getGluteeniton() == true) { 
			ainesKohdalla.setGluteeniton(false);
			
		}
		else ainesKohdalla.setGluteeniton(true);
		naytaAines();
	}
				
		
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleLaktoosi() {
		if ((ainesKohdalla = chooserAineksetAines.getSelectedObject()) == null) return;
		
		if (ainesKohdalla.getLaktoositon() == true) ainesKohdalla.setLaktoositon(false);
		else ainesKohdalla.setLaktoositon(true);
		naytaAines();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleVegaaninen() {
		if ((ainesKohdalla = chooserAineksetAines.getSelectedObject()) == null) return;
		
		if (ainesKohdalla.getVegaaninen() == true) ainesKohdalla.setVegaaninen(false);
		else ainesKohdalla.setVegaaninen(true);
		naytaAines();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½, sulkee aineksien muokkausikkunan
	 */
	@FXML private void handleLopetaMuokkaaAines() {
		tallennaAines();
		Stage stage = (Stage) menuBarMuokkaaAines.getScene().getWindow();
		stage.hide();
		controller.naytaReseptitListassaMain();
	}

	
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	//======================================================================================================================
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	
	
	private ReseptikirjaGUIController controller;
	private Reseptikirja reseptikirja;
	private Aines ainesKohdalla;
	
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();      
	}
	
	
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttï¿½, johon voidaan tulostaa jï¿½senten tiedot.
     * Alustetaan myï¿½s jï¿½senlistan kuuntelija 
     */
	public void alusta() {
    	naytaAineksetListassaAines();
    	if (chooserAineksetAines == null) return;
    	
    	chooserAineksetAines.addSelectionListener(e -> naytaAines());
    }
    
    
    /**
     * Nï¿½yttï¿½ï¿½ listasta valitun aineksen tiedot
     */
    private void naytaAines() {
        ainesKohdalla = chooserAineksetAines.getSelectedObject();
        if (ainesKohdalla == null) return;
     
        textAines.setText( "Gluteeniton: " +    ainesKohdalla.getGluteeniton() + "\n" +
                           "Laktoositon: " +    ainesKohdalla.getLaktoositon() + "\n" +
                           "Vegaaninen: " +     ainesKohdalla.getVegaaninen());
    }
    
    
    /**
     * Listaa ainekset ikkunan listaan
     */
    private void naytaAineksetListassaAines() {
    	if (reseptikirja == null) return;
    	if (chooserAineksetAines == null) return;
    	
    	chooserAineksetAines.clear();
    	
    	if (reseptikirja.getAineksetLuokka().getAinekset() == null || reseptikirja.getAineksetLuokka().getAinekset().length < 1) return;
    	for (int i = 0; i < reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getAineksetLuokka().getAinekset()[i] == null) break;
    		chooserAineksetAines.add(reseptikirja.getAineksetLuokka().getAinekset()[i]);
    	}
    }
    
    
    /**
     * Lisï¿½ï¿½ aineksen taulukkoon annetulla nimellï¿½
     * @param nimi aineksen nimi
     */
    public void lisaaAines(String nimi) {
    	if (reseptikirja == null) return;
    	if (nimi == null) return;
    	
    	if (nimi.contains("|")) {
    	    Dialogs.showMessageDialog("Ei voi sisätää '|'-merkkejä!");
    	    return;
    	}
    	
    	Aines aines = new Aines(reseptikirja.getAineksetLuokka(), nimi, false, false, false);
    	aines.tallennaAines();
    	reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
    	naytaAineksetListassaAines();
    }
    
    
    /**
     * Poistaa valitun aineksen
     */
    public void poistaAines() {
    	if (reseptikirja == null || chooserAineksetAines == null || chooserAineksetAines.getSelectedObject() == null) return;
    	
    	int poistettavaId = chooserAineksetAines.getSelectedObject().getAinesId();
    	
    	if (reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot() > 0) {
    		for (int i = 0; i < reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot(); i++) {
    			if (reseptikirja.getReseptitLuokka().getReseptit()[i].getAineslista().contains(poistettavaId)) {
    				while (reseptikirja.getReseptitLuokka().getReseptit()[i].getAineslista().contains(poistettavaId)) {
    					int index = reseptikirja.getReseptitLuokka().getReseptit()[i].getAineslista().indexOf(poistettavaId);
    					reseptikirja.getReseptitLuokka().getReseptit()[i].getAineslista().remove(index);
    				}
    			}
    		}
    	}
    	
    	for (int i = 0; i < reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getAineksetLuokka().getAinekset()[i].getAinesId() == poistettavaId) {
    			reseptikirja.getAineksetLuokka().getAinekset()[i] = null;
    			break;
    		}
    	}
    	reseptikirja.getAineksetLuokka().jarjestaAinekset();
    	naytaAineksetListassaAines();
    }
    
    
    /**
     * Tallentaa ainekset tiedostoon ikkuna suljettaessa
     */
    private void tallennaAines() {
    	naytaAineksetListassaAines();
    	if (chooserAineksetAines.getObjects().isEmpty()) return;
    	
    	Lukija lukija = new Lukija(reseptikirja);
		String rivi = "";
    	try (FileReader lukija1 = new FileReader(lukija.getAinekset())) {
    	    try (BufferedReader pakkaus = new BufferedReader(lukija1)) {
    	        try {
    	            rivi = pakkaus.readLine();
    	            pakkaus.close();
    	            lukija1.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	        pakkaus.close();
    	        lukija1.close();
    	    }
			
		} catch (Exception e) {
		    Dialogs.showMessageDialog("Virhe tallennuksessa!");
			e.printStackTrace();
		}
    	
		try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(lukija.getAinekset().toString(), false))) {
			kirjoittaja.println(rivi);
			List<Aines> ainesLista = chooserAineksetAines.getObjects();
			for (int i = 0; i < ainesLista.size(); i++) {
				Aines aines= ainesLista.get(i);
				aines.tallennaAines();
			}
			kirjoittaja.close();
        } catch (FileNotFoundException ex) {
        	System.err.println("Tiedosto ei aukea: " + ex.getMessage());
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
     * Hakukentï¿½n toiminta. Sulkee aineket, jotka eivï¿½t vastaa, pois listasta.
     */
	private void hae() {
		
		StringBuilder ehto = new StringBuilder(hakuehto.getText());
		ehto.insert(0, ".*");
		ehto.append(".*");
		String s = ehto.toString().toUpperCase();
		naytaAineksetListassaAines();
		List<Aines> ainekset = chooserAineksetAines.getObjects();
		chooserAineksetAines.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < ainekset.size(); i++) {
			if (!ainekset.get(i).getAinesNimi().toUpperCase().matches(s)) {
				ainekset.remove(i);
				i--;
			}
		}
		// Lisï¿½tï¿½ï¿½n listaan reseptit joiden nimen osa vastaa ehtoa
		for (Aines aines : ainekset) {
			chooserAineksetAines.add(aines);
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

	