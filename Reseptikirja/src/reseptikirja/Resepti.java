package reseptikirja;

import java.util.ArrayList;
import java.util.List;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luo olion, jolla on nimi, sek‰ lista aineksista.
 * 
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com,kustijanatuinen@gmail.com
 * 17.4.2018
 */
public class Resepti {

	
	private Reseptit reseptitLuokka;
	private int reseptiId;
	private String reseptinNimi;
	private int arvostelu;
	private List<Integer> aineslista = new ArrayList<Integer>();
	private int ohje = -1;

	
	/**
	 * M‰‰ritt‰‰ reseptin ominaisuudet
	 * @param reseptit Reseptit instanssi johon kuuluu
	 * @param reseptinnimi String muotoinen reseptin nimi
	 */
	public Resepti(Reseptit reseptit, String  reseptinnimi) {
		reseptitLuokka = reseptit;
		this.reseptiId = reseptit.getReseptiIdGeneraattori() + 1;
		reseptit.nostaReseptiIdGeneraattori(); 			// Nostaa IdGen lukua yhdell‰
		this.reseptinNimi =  reseptinnimi;
		this.arvostelu = -1;
		
	}
	
	
	/**
	 * Tallentaa reseptin tiedostoon
	 */
	public void tallennaResepti() {
		Lukija lukija = new Lukija();
		lukija.luoTiedosto(lukija.getReseptit());
		lukija.tallennaIdTiedostoon(lukija.getReseptit(), String.valueOf(reseptitLuokka.getReseptiIdGeneraattori()));
		lukija.tallennaTiedostoon(lukija.getReseptit(), this.toStringTallennus());
	}
	
	
	/**
	 * Palauttaa reseptin Id:n
	 * @return reseptiId
	 * @example
	 * <pre name="test">
	 * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
	 * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
	 * 
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[0].getReseptiId() === 1;
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].getReseptiId() === 2;
	 * </pre>
	 */
	public int getReseptiId() {
		return reseptiId;
	}
	
	
	/**
	 * Palauttaa reseptin nimen
	 * @return reseptin nimi
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
	 * 
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[0].getReseptiNimi() === "spyd‰ri";
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].getReseptiNimi() === "keitto";
	 * </pre>
	 */
	public String getReseptiNimi() {
		return reseptinNimi;
	}
	
	
	/**
	 * Palauttaa reseptin arvostelun
	 * @return arvostelu v‰lilt‰ 1-5
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
	 * 
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].setArvostelu(3);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[0].getArvostelu() === -1;
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].getArvostelu() === 3;
	 * </pre>
	 */
	public int getArvostelu() {
		return arvostelu;
	}
	
	
	/**
	 * Palauttaa reseptin ohjeen
	 * @return reseptin ohje
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
     * 
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
	 * 
	 * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * Ohje ohje2 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * ohje1.setOhje("Ohje");
	 * ohje2.setOhje("Hyv‰");
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].setOhje(1);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[0].getOhje() === -1;
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].getOhje() === 1;
	 * </pre>
	 */
	public int getOhje() {
		return ohje;
	}
	
	
	/**
	 * Palauttaa reseptin aineslistan
	 * @return aineslista
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
     * resepti2.setAines(0,aines1);
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
     * 
     * String.valueOf(resepti1.getAineslista()) === "[]";
     * String.valueOf(resepti2.getAineslista()) === "[1]";
     * resepti2.setAines(1,aines1);
     * String.valueOf(resepti2.getAineslista()) === "[1, 1]";
	 * </pre>
	 */
	public List<Integer> getAineslista() {
		return aineslista;
	}
	
	
	/**
	 * Asettaa reseptin Id:n
	 * @param Id reseptin Id luku	
	 * @example
	 * <pre name="test">
	 * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.getReseptiId() === 1;
     * resepti1.setReseptiId(6);
     * resepti1.getReseptiId() === 6;
	 * </pre>
	 */
	public void setReseptiId(int Id) {		
		reseptiId = Id;
	}
	
	
	/**
	 * Asettaa reseptin nimien
	 * @param nimi reseptin nimi
	 * @example
	 * <pre name="test">
	 * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.getReseptiNimi() === "spyd‰ri";
     * resepti1.setReseptiNimi("Kala");
     * resepti1.getReseptiNimi() === "Kala";
	 * </pre>
	 */
	public void setReseptiNimi(String nimi) {
		reseptinNimi = nimi;
	}
	
	
	/**
	 * Asettaa arvostelun
	 * @param i 1-5 kokonaisluku
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.getArvostelu() === -1;
     * resepti1.setArvostelu(5);
     * resepti1.getArvostelu() === 5;
	 * </pre>
	 */
	public void setArvostelu(int i) {
		arvostelu = i;
	}
	
	
	/**
	 * Asettaa aineksen reseptin aineslistaan
	 * @param indeksi Listan paikka johon aines lis‰t‰‰n
	 * @param aines Aines joka lis‰t‰‰n
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
     * 
     * String.valueOf(resepti2.getAineslista()) === "[]";
     * resepti2.setAines(0,aines1);
     * String.valueOf(resepti2.getAineslista()) === "[1]";
     * resepti2.setAines(1,aines1);
     * String.valueOf(resepti2.getAineslista()) === "[1, 1]";
	 * </pre>
	 */
	public void setAines(int indeksi, Aines aines) {
		try {
			aineslista.set(indeksi, aines.getAinesId());
		} catch (Exception e) {
			aineslista.add(aines.getAinesId());
		}
	}
		
	
	/**
	 * Asettaa reseptin ohjeen
	 * @param ohje string joka asetetaan
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.getOhje() === -1;
     * resepti1.setOhje(55);
     * resepti1.getOhje() === 55;
	 * </pre>
	 */
	public void setOhje(int ohje) {
		this.ohje = ohje;
	}
	
	
	/**
	 * Muuttaa olion ominaisuudet String muotoon
	 * @return tallennusmuodossa oleva olio
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * Resepti resepti2 = new Resepti(reseptikirja.getReseptitLuokka(), "keitto");
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
     * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"makkara");
     * 
     * resepti1.setAines(0, aines1);
     * resepti1.setAines(1, aines2);
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti2);
     * 
	 * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * Ohje ohje2 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * 
	 * ohje1.setOhje("Ohje");
	 * ohje2.setOhje("Hyv‰");
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].setOhje(1);
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].setArvostelu(3);
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptit()[0].toStringTallennus() === "1|spyd‰ri|-1|-1|[1, 2]";
	 * reseptikirja.getReseptitLuokka().getReseptit()[1].toStringTallennus() === "2|keitto|3|1|[]";
	 * </pre>
	 */
	public String toStringTallennus() {
		return "" + 
				getReseptiId() + "|" +
				getReseptiNimi() + "|" +
				getArvostelu() + "|" + 
				getOhje() + "|" +
				getAineslista();
	}
	
	
	/**
	 * Palauttaa reseptin nimen
	 * @return reseptinNimi
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.toString() === "spyd‰ri";
     * resepti1.setReseptiNimi("Pallo");
     * resepti1.toString() === "Pallo";
	 * </pre>
	 */
	@Override
    public String toString() {
		return reseptinNimi;
	}

	
	/**
	 * Selvitt‰‰ reseptin tiedot '|' erotellusta merkkijonosta
	 * @param rivi joka parsetaan
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(), "spyd‰ri");
     * 
     * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
     * 
     * resepti1.parseResepti("5|Kakku|5|4|[]");
     * resepti1.toStringTallennus() === "5|Kakku|5|4|[]";
     * 
     * resepti1.parseResepti("5777|Kakku13|5|47777|[1, 5, 555, 99999, 2324]");
     * resepti1.toStringTallennus() === "5777|Kakku13|5|47777|[1, 5, 555, 99999, 2324]";
	 * </pre>
	 */
	public void parseResepti(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		setReseptiId(Integer.valueOf(Mjonot.erota(sb, '|')));
		setReseptiNimi(Mjonot.erota(sb, '|'));
		setArvostelu(Integer.valueOf(Mjonot.erota(sb, '|')));
		setOhje(Integer.valueOf(Mjonot.erota(sb, '|')));
		
		sb.deleteCharAt(sb.indexOf("["));
		sb.deleteCharAt(sb.indexOf("]"));
		
		while (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.indexOf(","));
		}
		
		if (sb.length() < 1) return;
		int apu = 0;
		while ((apu = Mjonot.erotaInt(sb, -1)) != -1) {
			aineslista.add(apu);
		}
	}
}
