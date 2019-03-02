package reseptikirja;

import java.util.ArrayList;
import java.util.List;

/**
 * Luo reseptikirjan ytimen. Kokoaa Reseptit,Ainekset ja Ohjeet. Hakee tietoja olioista.
 * 
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com 
 * 17.4.2018
 */
public class Reseptikirja {

	
	private Reseptit reseptit;
	private Ainekset ainekset;
	private Ohjeet ohjeet;
	
	
	/**
	 * Constructor reseptikirjalle
	 */
	public Reseptikirja() {
		reseptit = new Reseptit();
		ainekset = new Ainekset();
		ohjeet = new Ohjeet();
	}
	
	
	/**
	 * Palauttaa reseptit luokan
	 * @return reseptit luokka
	 */
	public Reseptit getReseptitLuokka() {
		return reseptit;
	}
	
	
	/**
	 * Palauttaa ainekset luokan
	 * @return ainekset luokka
	 */
	public Ainekset getAineksetLuokka() {
		return ainekset;
	}
	
	
	/**
	 * Palauttaa ohjeet luokan
	 * @return ohjeet luokka
	 */
	public Ohjeet getOhjeetLuokka() {
		return ohjeet;
	}
	
	
	/**
	 * Palauttaa aineslistan joka sisältyy reseptiin
	 * @param resepti jota tutkitaan
	 * @return Lista ainesten id:stä
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), "Perunamuussi");
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti);
	 * 
	 * Aines peruna = new Aines(reseptikirja.getAineksetLuokka(), "Peruna");
	 * Aines vesi = new Aines(reseptikirja.getAineksetLuokka(), "Vesi");
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(peruna);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(vesi);
	 * 
	 * resepti.setAines(0, peruna);
	 * resepti.setAines(1, vesi);
	 * 
	 * reseptikirja.getReseptinAinesLista(resepti).toString() === "[Peruna, Vesi]";
	 * 
     * lukija.poistaTiedostot();
	 * </pre>
	 */
	public List<Aines> getReseptinAinesLista(Resepti resepti) {
		List<Aines> lista = new ArrayList<Aines>();
		List<Integer> ainesId = resepti.getAineslista();
		
		for (int i = 0; i < ainesId.size(); i++) {
			for (int j = 0; j < getAineksetLuokka().laskeTaulukonAlkiot(); j++) {
				if (ainesId.get(i) == getAineksetLuokka().getAinekset()[j].getAinesId()) {
					lista.add(getAineksetLuokka().getAinekset()[j]);
				}
			}
		}
		return lista;
	}
	
	
	/**
	 * Etsii reseptin ohjeen kun resepti on annettu
	 * @param resepti annettu
	 * @return String ohje
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), "Perunamuussi");
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti);
     * 
     * Ohje ohje = new Ohje(reseptikirja.getOhjeetLuokka());
     * ohje.setOhje("Keitä!");
     * 
     * resepti.setOhje(ohje.getOhjeId());
     * resepti.getOhje() === 1;
     * 
     * ohje = new Ohje(reseptikirja.getOhjeetLuokka());
     * ohje.setOhje("Keitä2!");
     * 
     * resepti.setOhje(ohje.getOhjeId());
     * resepti.getOhje() === 2;
     * 
     * lukija.poistaTiedostot();
     * </pre>
	 */
	public String getReseptinOhje(Resepti resepti) {
		int ohjeId = resepti.getOhje();
		if (ohjeId == -1) return "";
		
		String ohje = "";
		
		for (int i = 0; i < ohjeet.getOhjeet().size(); i++) {
			if (ohjeet.getOhjeet().get(i).getOhjeId() == ohjeId) {
				ohje = ohjeet.getOhjeet().get(i).getOhje();
			}
		}
		return ohje;
	}
	
	
	/**
	 * Palauttaa aineen jota id täsmää
	 * @param id jolla etsitään
	 * @return aines joka täsmää
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Aines aines = new Aines(reseptikirja.getAineksetLuokka(), "Peruna");
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(), "Liha");
     * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(), "Vesi");
     * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
     * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
     * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
     * 
     * reseptikirja.getAinesIdsta(1).toString() === "Peruna";
     * reseptikirja.getAinesIdsta(2).toString() === "Liha";
     * reseptikirja.getAinesIdsta(3).toString() === "Vesi";
     * aines.getAinesId() === 1;
     * aines1.getAinesId() === 2;
     * aines2.getAinesId() === 3;
     * 
     * lukija.poistaTiedostot();
     * </pre>
	 */
	public Aines getAinesIdsta(int id) {
		if (id == -1) return null;
		for (int i = 0; i < getAineksetLuokka().laskeTaulukonAlkiot(); i++) {
			if (getAineksetLuokka().getAinekset()[i].getAinesId() == id) {
				return getAineksetLuokka().getAinekset()[i];
			}
		}
		return null;
	}
	
	
	/**
	 * Etsii reseptin arvostelun kun resepti on annettu
	 * @param resepti annettu
	 * @return int arvostelu
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti = new Resepti(reseptikirja.getReseptitLuokka(), "Makkarakeitto");
     * reseptikirja.getReseptinArvostelu(resepti) === "-1";
     * resepti.setArvostelu(5);
     * reseptikirja.getReseptinArvostelu(resepti) === "5";
     * 
     * lukija.poistaTiedostot();
     * </pre>
	 */
	public String getReseptinArvostelu(Resepti resepti) {
		
		int arvostelu = resepti.getArvostelu();
		if (arvostelu == -1) return "-1";
		
		return String.valueOf(resepti.getArvostelu());
	}
	
	
	/**
	 * Palauttaa stringin joka kertoo reseptin GLV statuksen
	 * @param resepti jota analysoidaan
	 * @return analysoinnin tulos
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja rk = new Reseptikirja();
     * Resepti r = new Resepti(rk.getReseptitLuokka(), "Makkarakeitto");
     * rk.getReseptitLuokka().lisaaReseptiTaulukkoon(r);
     * 
     * Aines a1 = new Aines(rk.getAineksetLuokka(), "Peruna");
     * Aines a2 = new Aines(rk.getAineksetLuokka(), "Makkara");
     * rk.getAineksetLuokka().lisaaAinesTaulukkoon(a1);
     * rk.getAineksetLuokka().lisaaAinesTaulukkoon(a2);
     * 
     * r.setAines(0, a1);
     * r.setAines(1, a2);
     * 
     * rk.analysoiGLV(r) === "Gluteenia, Laktoosia, Ei vegaaninen";
     * a1.setGluteeniton(true);
     * a1.setLaktoositon(true);
     * a2.setGluteeniton(true);
     * a2.setLaktoositon(true);
     * 
     * rk.analysoiGLV(r) === "Ei vegaaninen";
     * a2.setVegaaninen(true);
     * rk.analysoiGLV(r) === "Ei vegaaninen";
     * a1.setVegaaninen(true);
     * rk.analysoiGLV(r) === "";
     * 
     * lukija.poistaTiedostot();
     * </pre>
	 */
	public String analysoiGLV(Resepti resepti) {
		StringBuilder tulos = new StringBuilder();
		
		for (int i = 0; i < resepti.getAineslista().size(); i++) {
			if (getAinesIdsta(resepti.getAineslista().get(i)).getGluteeniton() == false) {
				tulos.append("Gluteenia, ");
				break;
			}
		}
		for (int i = 0; i < resepti.getAineslista().size(); i++) {
			if (getAinesIdsta(resepti.getAineslista().get(i)).getLaktoositon() == false) {
				tulos.append("Laktoosia, ");
				break;
			}
		}
		for (int i = 0; i < resepti.getAineslista().size(); i++) {
			if (getAinesIdsta(resepti.getAineslista().get(i)).getVegaaninen() == false) {
				tulos.append("Ei vegaaninen");
				break;
			}
		}
		return tulos.toString();
	}
}
