package fxReseptikirja;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import reseptikirja.Lukija;
import reseptikirja.Reseptikirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * Reseptikokoelma
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class ReseptikirjaMain extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("resources/ReseptikirjaGUIview.fxml"));
            final Pane root = (Pane)ldr.load();
            final ReseptikirjaGUIController reseptikirjaCtrl = (ReseptikirjaGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("Reseptikirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reseptikirja");
            
            Reseptikirja reseptikirja = new Reseptikirja();  
            reseptikirjaCtrl.setReseptikirja(reseptikirja);
            Lukija lukija = new Lukija(reseptikirja);
            lukija.parseAinekset();
            lukija.parseReseptit(); 
            lukija.parseOhjeet();
            reseptikirjaCtrl.naytaReseptitListassaMain();
            primaryStage.show();
            
            // Platform.setImplicitExit(false); // tätä ei kai saa laittaa

            primaryStage.setOnCloseRequest((event) -> {
                    if ( !reseptikirjaCtrl.voikoSulkea() ) event.consume();
                });
            
            if ( !reseptikirjaCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	
	/**
	 * Pääohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) 
	{
		launch(args);		// Aukaisee käyttöliittymän
	}
}
