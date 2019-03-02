 package fxReseptikirja;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import reseptikirja.Lukija;
import reseptikirja.Resepti;
import reseptikirja.Reseptikirja;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;

/**
 * Käyttï¿½liittymï¿½n controller
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaGUIController implements Initializable {
	

	@FXML private MenuBar menuBarMuokkaaResepti;
	@FXML private MenuBar menuBarMuokkaaAines;
	@FXML private MenuBar menuBarMuokkaaOhje;
	@FXML private Text areaReseptiMain;
	@FXML private TextField hakuehto;
	@FXML private ListChooser<Resepti> chooserReseptitMain;
	
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();      
	}

	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleMuokkaaAines() {
		MuokkaaAinesIkkuna(muokkaaAinesTaso);
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleMuokkaaOhje() {
		MuokkaaOhjeIkkuna(muokkaaOhjeTaso);
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleMuokkaaResepti() {
		MuokkaaReseptiIkkuna(muokkaaReseptiTaso);
	}
	
	
	/**
	 * Kï¿½sittelijï¿½ hakukentï¿½lle ja hakutoiminnolle
	 */
	@FXML private void handleEtsi() {
		hae();
	}

	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleTallenna() {
		tallennaResepti();
	}
	
	
	/**
	 * Kï¿½sittelijï¿½, sammuttaa ohjelman
	 */
	@FXML private void handleLopeta() {
		tallennaResepti();
		Platform.exit();
	}

	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleApua() {
		Dialogs.showMessageDialog("Apua ei löytynyt.");
	}
	
	
	/**
	 * Kï¿½sittelijï¿½
	 */
	@FXML private void handleTietoja() {
        areaReseptiMain.setText("2018 Ohjelmointi 2 Harjoitustyö\nTekijät: Janatuinen Kusti, Karkee Mikko");
	}
	
	
	@FXML private void handleTyhjennaTiedostot() {
	    if (Dialogs.showQuestionDialog("Tyhjennä tiedostot", "Haluatko varmasti tyhjentää tiedostot?", "Kyllä", "Ei")) {
	        tyhjennaTiedostot();
	    }
	}
	
	
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	//======================================================================================================================
	// Kï¿½yttï¿½liittymï¿½ï¿½n suoraan littymï¿½tï¿½ntï¿½ koodia
	
	
	private Reseptikirja reseptikirja;
	private Resepti reseptiKohdalla;
	
	private Stage muokkaaReseptiTaso = new Stage();
	private Stage muokkaaAinesTaso = new Stage();
	private Stage muokkaaOhjeTaso = new Stage();
	
    
    /**
     * Alustetaan jï¿½senlistan kuuntelija 
     */
    public void alusta() {
    	if (chooserReseptitMain == null) return;
    	
    	chooserReseptitMain.addSelectionListener(e -> naytaResepti());
    	if (areaReseptiMain == null) return;
    	areaReseptiMain.setText("2018 Ohjelmointi 2 Harjoitustyö\nTekijät: Janatuinen Kusti, Karkee Mikko");
    }
    
    
	/**
	 * Avaa reseptinmuokkaus ikkunan
	 * @param secondaryStage
	 */
	private void MuokkaaReseptiIkkuna(Stage secondaryStage)
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaMuokkaaReseptiGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaMuokkaaReseptiGUIController reseptiCtrl = (ReseptikirjaMuokkaaReseptiGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Resepti");
            
            reseptiCtrl.setReseptikirja(reseptikirja);
            reseptiCtrl.setController(this);
            
            // Platform.setImplicitExit(false); // tï¿½tï¿½ ei kai saa laittaa

            secondaryStage.setOnCloseRequest((event) -> {
                    if ( !reseptiCtrl.voikoSulkea() ) event.consume();
                });
            
            secondaryStage.show();
            reseptiCtrl.alusta();
            
            if ( !reseptiCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Avaa ohjeenmuokkaus ikkunan
	 * @param secondaryStage
	 */
	private void MuokkaaOhjeIkkuna(Stage secondaryStage)
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaMuokkaaOhjeGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaMuokkaaOhjeGUIController ohjeCtrl = (ReseptikirjaMuokkaaOhjeGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Resepti");
            
            ohjeCtrl.setReseptikirja(reseptikirja);
            ohjeCtrl.setController(this);
            
            // Platform.setImplicitExit(false); // tï¿½tï¿½ ei kai saa laittaa

            secondaryStage.setOnCloseRequest((event) -> {
                    if ( !ohjeCtrl.voikoSulkea() ) event.consume();
                });
            
            secondaryStage.show();
            ohjeCtrl.alusta();
            
            if ( !ohjeCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Avaa aineksenmuokkaus ikkunan
	 * @param secondaryStage
	 */
	private void MuokkaaAinesIkkuna(Stage secondaryStage)
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaMuokkaaAinesGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaMuokkaaAinesGUIController ainesCtrl = (ReseptikirjaMuokkaaAinesGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Resepti");
            
            ainesCtrl.setReseptikirja(reseptikirja);
            ainesCtrl.setController(this);
            
            // Platform.setImplicitExit(false); // tï¿½tï¿½ ei kai saa laittaa

            secondaryStage.setOnCloseRequest((event) -> {
                    if ( !ainesCtrl.voikoSulkea() ) event.consume();
                });
            
            secondaryStage.show();
            ainesCtrl.alusta();
            
            if ( !ainesCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * 
	 * @param reseptikirja jota kï¿½ytetï¿½ï¿½n tï¿½ssï¿½ kï¿½yttï¿½liittymï¿½ssï¿½
	 */
    public void setReseptikirja(Reseptikirja reseptikirja) {
        this.reseptikirja  = reseptikirja;
    }
    
    
    /**
     * Listaa reseptit pï¿½ï¿½ikkunan listaan
     */
    public void naytaReseptitListassaMain() {
    	
    	if (reseptikirja == null || chooserReseptitMain == null) return;
    	
    	chooserReseptitMain.clear();
    	
    	if (reseptikirja.getReseptitLuokka().getReseptit() == null || reseptikirja.getReseptitLuokka().getReseptit().length < 1) return;
    	for (int i = 0; i < reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot(); i++) {
    		if (reseptikirja.getReseptitLuokka().getReseptit()[i] == null) break;
    		chooserReseptitMain.add(reseptikirja.getReseptitLuokka().getReseptit()[i]);
    	}
    }
    
    
    /**
     * Nï¿½yttï¿½ï¿½ listasta valitun reseptin tiedot, tilapï¿½isesti yhteen isoon edit-kenttï¿½ï¿½n
     */
    private void naytaResepti() {
        reseptiKohdalla = chooserReseptitMain.getSelectedObject();

        if (reseptiKohdalla == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reseptiKohdalla.getAineslista().size(); i++) {
        	sb.append(reseptikirja.getAinesIdsta(reseptiKohdalla.getAineslista().get(i)));
        	sb.append(", ");
        }
        
        areaReseptiMain.setText("Reseptin ominaisuudet: " + "\n" + reseptikirja.analysoiGLV(reseptiKohdalla) + "\n" + "\n" + "Arvostelu:" + "\n" + reseptiKohdalla.getArvostelu() + "\n" + "\n" + "Ainekset:" + "\n" + sb.toString() + "\n" 
        							+ "\n" + "Ohje:" + "\n" + reseptikirja.getReseptinOhje(reseptiKohdalla));
    }
    
    
    /**
     * Tallentaa reseptit tiedostoon ohjelma suljettaessa
     */
    private void tallennaResepti() {
    	naytaReseptitListassaMain();
    	if (chooserReseptitMain.getObjects().isEmpty()) return;
    	
    	Lukija lukija = new Lukija(reseptikirja);
		String rivi = "";
    	try (FileReader lukija1 = new FileReader(lukija.getReseptit())) {
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
    	
		try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(lukija.getReseptit().toString(), false))) {
			kirjoittaja.println(rivi);
			List<Resepti> reseptiLista = chooserReseptitMain.getObjects();
			for (int i = 0; i < reseptiLista.size(); i++) {
				Resepti resepti = reseptiLista.get(i);
				resepti.tallennaResepti();
			}
			kirjoittaja.close();
        } catch (FileNotFoundException ex) {
        	System.err.println("Tiedosto ei aukea: " + ex.getMessage());
        }
    }
    
    
	/**
	 * Kysytï¿½ï¿½n tiedoston nimi ja luetaan se
	 * @return true jos onnistui, false jos ei
	 */
	public boolean avaa() {
	//	String uusinimi = kirjannimi;
	//	if (uusinimi == null) return false;
	//	lueTiedosto(uusinimi);
		return true;
	}
	
	
	/**
	 * Hakee reseptien tiedot listaan
	 */
	private void hae() {
		
		StringBuilder ehto = new StringBuilder(hakuehto.getText());
		ehto.insert(0, ".*");
		ehto.append(".*");
		String s = ehto.toString().toUpperCase();
		naytaReseptitListassaMain();
		List<Resepti> reseptit = chooserReseptitMain.getObjects();
		chooserReseptitMain.clear();
		// Regular expression tarkistaa vastaako reseptin nimi ehtoa, jos ei, poistetaan listasta
		for (int i = 0; i < reseptit.size(); i++) {
			if (!reseptit.get(i).getReseptiNimi().toUpperCase().matches(s)) {
				reseptit.remove(i);
				i--;
			}
		}
		// Lisï¿½tï¿½ï¿½n listaan reseptit joiden nimen osa vastaa ehtoa
		for (Resepti resepti : reseptit) {
			chooserReseptitMain.add(resepti);
		}
	}
	
	
	private void tyhjennaTiedostot() {
	    for (int i = 0; i < reseptikirja.getReseptitLuokka().getReseptit().length; i++) {
	        reseptikirja.getReseptitLuokka().getReseptit()[i] = null;
	    }
	    
	    for (int i = 0; i < reseptikirja.getAineksetLuokka().getAinekset().length; i++) {
	        reseptikirja.getAineksetLuokka().getAinekset()[i] = null;
	    }
	    
	    reseptikirja.getOhjeetLuokka().getOhjeet().clear();;
	    Lukija lukija = new Lukija();
	    lukija.poistaTiedostot();
	    lukija.luoTiedosto(lukija.getReseptit());
	    lukija.luoTiedosto(lukija.getAinekset());
	    lukija.luoTiedosto(lukija.getOhjeet());
	    
	    areaReseptiMain.setText("2018 Ohjelmointi 2 Harjoitustyö\nTekijät: Janatuinen Kusti, Karkee Mikko");
	    naytaReseptitListassaMain();
	    
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
