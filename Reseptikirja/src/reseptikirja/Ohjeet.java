package reseptikirja;

import java.util.ArrayList;
import java.util.List;

/**
 * Hallitsee ohjeita
 * 
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 17.4.2018
 */
public class Ohjeet {
    
    
	private List<Ohje> ohjeet = new ArrayList<Ohje>(1);
	private int ohjeIdGeneraattori;
	
	
	/**
	 * Constructor Ohjeet oliolle
	 */
	public Ohjeet() {
		Lukija lukija = new Lukija();
		
		if (lukija.lueTiedosto(lukija.getOhjeet()) == null) lukija.luoTiedosto(lukija.getOhjeet());
		
		StringBuilder idLuku = new StringBuilder(lukija.lueTiedosto(lukija.getOhjeet()));
		
		if (idLuku.length() < 1) ohjeIdGeneraattori = 0;
		else {
			String idLukuString = idLuku.subSequence(0, idLuku.indexOf("\n")).toString();
			ohjeIdGeneraattori = Integer.valueOf(idLukuString);
		}
		
		// Asettaa listan alkion nullaksi
		for(int i = 0; i < ohjeet.size(); i++) {
			ohjeet.set(i, null);
		}
	}
	
	
	/**
	 * Palauttaa IdGeneraattorin
	 * @return kokonaisluku, jonka perusteella annetaan Id oliolle
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * reseptikirja.getOhjeetLuokka().getOhjeIdGeneraattori() === 0;
     * 
     * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
     * ohje1.getOhjeId();
     * reseptikirja.getOhjeetLuokka().getOhjeIdGeneraattori() === 1;
	 * </pre>
	 */
	public int getOhjeIdGeneraattori() {
		return ohjeIdGeneraattori;
	}

	
	/**
	 * Palauttaa listan ohjeista
	 * @return List ohje
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * reseptikirja.getOhjeetLuokka().getOhjeet().toString() === "[]";
     * 
     * Ohje ohje = new Ohje(reseptikirja.getOhjeetLuokka());
     * ohje.setOhje("Keitä");
     * reseptikirja.getOhjeetLuokka().getOhjeet().toString() === "[1|Keitä]";
     * 
     * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
     * ohje1.setOhje("Kaada");
     * reseptikirja.getOhjeetLuokka().getOhjeet().toString() === "[1|Keitä, 2|Kaada]";
	 * </pre>
	 */
	public List<Ohje> getOhjeet() {
		return ohjeet;
	}
	
	
	/**
	 * Nostaa IdGen lukua yhdellä.
	 */
	public void nostaOhjeIdGeneraattori() {
		ohjeIdGeneraattori++;
	}
}
