package reseptikirja;

import java.io.*;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luo olion joka hoitaa tiedostojen luomisen, lukemisen, ja niihin kirjoittamisen
 * 
 * @author Mikko Karkee, Kusti Janatuinen
 * mikko.karkee1@gmail.com 
 * 18.4.2018
 */
public class Lukija {

	
	private Reseptikirja reseptikirja;
	// Tiedostojen alustamista
	private File reseptit = new File("reseptit.dat");
	private File ainekset = new File("ainekset.dat");
	private File ohjeet = new File("ohjeet.txt");

	
	/**
	 * Constructor
	 * @param reseptikirja johon kuuluu
	 */
	public Lukija(Reseptikirja reseptikirja) {
		this.reseptikirja = reseptikirja;
	}
	
	
	/**
	 * Constructor
	 */
	public Lukija() {
		this.reseptikirja = null;
	}
	
	
	/**
	 * Luo tiedoston 'tiedosto'
	 * @param tiedosto joka luodaan
	 */
	public void luoTiedosto(File tiedosto) {
		if(tiedosto.exists()) {
			return;
		}
		String tiedostoNimi = "" + tiedosto + "";
		try ( PrintStream kirjoittaja = new PrintStream(tiedostoNimi) ) {
			kirjoittaja.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Poistaa kaikki tiedostot
	 */
	public void poistaTiedostot() {
	    reseptit.delete();
	    ainekset.delete();
	    ohjeet.delete();
	}
	
	
	/**
	 * Lukee tiedostosta ja palauttaa sisällön
	 * @param tiedosto joka luetaan
	 * @return tiedoston sisältö
	 * @example
	 * <pre name="test">
	 * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
	 * lukija.luoTiedosto(lukija.getReseptit());
	 * lukija.luoTiedosto(lukija.getAinekset());
	 * lukija.luoTiedosto(lukija.getOhjeet());
	 * lukija.tallennaTiedostoon(lukija.getReseptit(), "Toimii!");
	 * lukija.tallennaTiedostoon(lukija.getOhjeet(), "Toimii!");
	 * lukija.tallennaTiedostoon(lukija.getAinekset(), "Toimii!");
	 * 
	 * String eka = lukija.lueTiedosto(lukija.getReseptit());
	 * String toka = lukija.lueTiedosto(lukija.getOhjeet());
	 * String kolmas = lukija.lueTiedosto(lukija.getAinekset());
	 * 
	 * lukija.poistaTiedostot();
	 * 
	 * eka === "Toimii!\n";
	 * toka === "Toimii!\n";
	 * kolmas === "Toimii!\n";
	 * </pre>
	 */
	public String lueTiedosto(File tiedosto) {
		StringBuilder sisalto = new StringBuilder();
		String rivi;
		
		if (!tiedosto.exists()) luoTiedosto(tiedosto);
		
		try (FileReader lukija = new FileReader(tiedosto)) {
			try (BufferedReader pakkaus = new BufferedReader(lukija)) {
			    try {
			        while((rivi = pakkaus.readLine()) != null) {
			            sisalto.append(rivi);
			            sisalto.append("\n");
			        }
			        pakkaus.close();
			        lukija.close();
			    } catch (IOException e) {
			        sisalto.append("Input Output Exception Lukija.java:");
			        e.printStackTrace();
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			Dialogs.showMessageDialog("Virhe luettaessa tiedostoa!");
		}
		return sisalto.toString();
	}
	
	
	/**
	 * Tallentaa annetun id luvun annettuun tiedostoon
	 * @param tiedosto johon tallennetaan
	 * @param tallennettava teksti joka tallennetaan
	 * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * lukija.luoTiedosto(lukija.getReseptit());
     * lukija.luoTiedosto(lukija.getAinekset());
     * lukija.luoTiedosto(lukija.getOhjeet());
     * 
     * lukija.tallennaIdTiedostoon(lukija.getReseptit(), "3");
     * lukija.tallennaIdTiedostoon(lukija.getOhjeet(), "1");
     * lukija.tallennaIdTiedostoon(lukija.getAinekset(), "2");
     * lukija.tallennaTiedostoon(lukija.getReseptit(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getOhjeet(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getAinekset(), "Toimii!");
     * 
     * String eka = lukija.lueTiedosto(lukija.getReseptit());
     * String toka = lukija.lueTiedosto(lukija.getOhjeet());
     * String kolmas = lukija.lueTiedosto(lukija.getAinekset());
     * 
     * lukija.poistaTiedostot();
     * 
     * eka === "3\nToimii!\n";
     * toka === "1\nToimii!\n";
     * kolmas === "2\nToimii!\n";
     * </pre>
	 */
	public void tallennaIdTiedostoon(File tiedosto, String tallennettava) {
		StringBuilder insertId = new StringBuilder(lueTiedosto(tiedosto));
		if (insertId.length() < 1) {
			insertId.append(tallennettava + "\n");
			String muutettu = insertId.toString();
			
			try (PrintWriter kirjoittaja = new PrintWriter(tiedosto)) {
			    try (BufferedWriter paketti = new BufferedWriter(kirjoittaja)) {
			        paketti.write(muutettu);
			        paketti.close();
			    }
				kirjoittaja.close();
			} catch (IOException ex) {
				System.err.println("Tiedosto ei aukea: " + ex.getMessage());
				ex.printStackTrace();
			}
		} else {
			insertId.replace(0, insertId.indexOf("\n"), tallennettava);
			String muutettu = insertId.toString();
			
			try (PrintWriter kirjoittaja = new PrintWriter(tiedosto)) {
				try (BufferedWriter paketti = new BufferedWriter(kirjoittaja)) {
				    paketti.write(muutettu);
				    paketti.close();
				}
				kirjoittaja.close();
			} catch (IOException ex) {
				System.err.println("Tiedosto ei aukea: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Tallentaa annetun string olion annettuun tiedostoon
	 * @param tiedosto johon tallennetaan
	 * @param tallennettava teksti joka tallennetaan
	 * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * lukija.luoTiedosto(lukija.getReseptit());
     * lukija.luoTiedosto(lukija.getAinekset());
     * lukija.luoTiedosto(lukija.getOhjeet());
     * 
     * lukija.tallennaTiedostoon(lukija.getReseptit(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getOhjeet(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getAinekset(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getReseptit(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getOhjeet(), "Toimii!");
     * lukija.tallennaTiedostoon(lukija.getAinekset(), "Toimii!");
     * 
     * String eka = lukija.lueTiedosto(lukija.getReseptit());
     * String toka = lukija.lueTiedosto(lukija.getOhjeet());
     * String kolmas = lukija.lueTiedosto(lukija.getAinekset());
     * 
     * lukija.poistaTiedostot();
     * 
     * eka === "Toimii!\nToimii!\n";
     * toka === "Toimii!\nToimii!\n";
     * kolmas === "Toimii!\nToimii!\n";
     * </pre>
	 */
	public void tallennaTiedostoon(File tiedosto, String tallennettava) {
		try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(tiedosto.toString(), true))) {
			kirjoittaja.println(tallennettava);
			kirjoittaja.close();
        } catch (FileNotFoundException ex) {
        	System.err.println("Tiedosto ei aukea: " + ex.getMessage());
        }
	}
	
	
	/**
	 * Parsee reseptille ominaisuudet tiedostosta
	 */
	public void parseReseptit() {
		try( BufferedReader fi = new BufferedReader(new FileReader(reseptit))) {
			
			String rivi = fi.readLine();
			
			while ( (rivi = fi.readLine()) != null) {
				StringBuffer sb = new StringBuffer(rivi);
				rivi = rivi.trim();
				Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), Mjonot.erota(sb, '|'));
				resepti.parseResepti(rivi);
				reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Parsee ainekselle ominaisuudet tiedostosta
	 */
	public void parseAinekset( ) {
		try( BufferedReader fi = new BufferedReader(new FileReader(ainekset))) {
			
			String rivi = fi.readLine();
			
			while ( (rivi = fi.readLine())  != null) {
				StringBuffer sb = new StringBuffer(rivi);
				rivi = rivi.trim();
				Aines aines = new Aines(reseptikirja.getAineksetLuokka(), Mjonot.erota(sb, '|'));
				aines.parseAines(rivi);
				reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	
	/**
	 * Parsee ohjeelle ominaisuudet tiedostosta
	 */
	public void parseOhjeet( ) {
		try( BufferedReader fi = new BufferedReader(new FileReader(ohjeet))) {
			
			String rivi = fi.readLine();
			
			while ( (rivi = fi.readLine())  != null) {
				rivi = rivi.trim();
				Ohje ohje = new Ohje(reseptikirja.getOhjeetLuokka());
				ohje.parseOhje(rivi);
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	
	/**
	 * Palauttaa reseptit tiedoston
	 * @return File
	 */
	public File getReseptit() {
		return reseptit;
	}
	
	
	/**
	 * Palauttaa ainekset tiedoston
	 * @return File
	 */
	public File getAinekset() {
		return ainekset;
	}


	/**
	 * Palauttaa ohjeet tiedoston
	 * @return File
	 */
	public File getOhjeet() {
		return ohjeet;
	}
	
	
	/**
	 * Testipääohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Reseptikirja reseptikirja = new Reseptikirja();
		reseptikirja.getReseptitLuokka();
		System.out.println("TODO: Testipääohjelma");
	}
}
