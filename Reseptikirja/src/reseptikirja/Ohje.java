package reseptikirja;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luo olion, jolla on id sek‰ string ohje.
 * 
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 17.4.2018
 */
public class Ohje {
    
    
	private Ohjeet ohjeetLuokka;
	private int ohjeId;
	private String ohje;
	
	
	/**
	 * Constructor ohjeelle
	 * @param ohjeet yl‰luokka johon kuuluu
	 */
	public Ohje(Ohjeet ohjeet) {
		ohjeetLuokka = ohjeet;
		this.ohjeId = ohjeet.getOhjeIdGeneraattori() + 1;
		ohjeet.nostaOhjeIdGeneraattori(); 			// Nostaa IdGen lukua yhdell‰
		this.ohje =  "";
		ohjeet.getOhjeet().add(this);
	}
	
	
	/**
	 * Tallentaa ohjeen tiedostoon
	 */
	public void tallennaOhje() {
		Lukija lukija = new Lukija();
		lukija.luoTiedosto(lukija.getOhjeet());
		lukija.tallennaIdTiedostoon(lukija.getOhjeet(), String.valueOf(ohjeetLuokka.getOhjeIdGeneraattori()));
		lukija.tallennaTiedostoon(lukija.getOhjeet(), this.toString());
	}
	
	
	/**
	 * Muuttaa ohjeen string muotoon jotta se voidaan tallentaa
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
	 * Reseptikirja rk = new Reseptikirja();
	 * Ohje ohje = new Ohje(rk.getOhjeetLuokka());
	 * 
	 * ohje.toString() === "1|";
	 * ohje.setOhje("Keit‰");
	 * ohje.toString() === "1|Keit‰";
	 * </pre>
	 */
	@Override
    public String toString( ) {
		return "" + 
				getOhjeId() + "|" +
				getOhje();
	}
	
	
	/**
	 * Parsee ohjeen tiedostosta
	 * @param rivi joka pyrit‰‰n parseemaan
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja rk = new Reseptikirja();
     * Ohje ohje = new Ohje(rk.getOhjeetLuokka());
     * 
     * ohje.toString() === "1|";
     * ohje.parseOhje("55567|Keit‰ perunat ja paista makkarinot");
     * ohje.toString() === "55567|Keit‰ perunat ja paista makkarinot";
	 * </pre>
	 */
	public void parseOhje(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		setOhjeId(Integer.valueOf(Mjonot.erota(sb, '|')));
		setOhje(Mjonot.erota(sb, '|'));
	}
	
	
	/**
	 * Palauttaa olion string muotoisen ohjeen
	 * @return ohje
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * Ohje ohje2 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * 
	 * reseptikirja.getOhjeetLuokka().getOhjeet().get(0).getOhje() === "";
	 * 
	 * ohje1.setOhje("Testi1");
	 * ohje2.setOhje("Testi2");
	 * 
	 * reseptikirja.getOhjeetLuokka().getOhjeet().get(0).getOhje() === "Testi1";
	 * reseptikirja.getOhjeetLuokka().getOhjeet().get(1).getOhje() === "Testi2";
	 * </pre>
	 */
	public String getOhje() {
		return ohje;
	}
	
	
	/**
	 * Asettaa ohjeelle id:n
	 * @param ohjeId joka asetetaan
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
     * 
     * ohje1.getOhjeId() === 1;
     * ohje1.setOhjeId(4444);
     * ohje1.getOhjeId() === 4444;
	 * </pre>
	 */
	public void setOhjeId(int ohjeId) {
		this.ohjeId = ohjeId;
	}
	
	
	/**
	 * Asettaa oliolle string ohjeen
	 * @param ohje string ohje
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
     * 
     * ohje1.getOhje() === "";
     * ohje1.setOhje("Keit‰");
     * ohje1.getOhje() === "Keit‰";
	 * </pre>
	 */
	public void setOhje(String ohje) {
		this.ohje = ohje;
	}
	
	
	/**
	 * Palauttaa ohjeen Id:n
	 * @return ohjeId
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * Ohje ohje2 = new Ohje(reseptikirja.getOhjeetLuokka());
	 * 
	 * ohje1.getOhjeId() === 1;
	 * ohje2.getOhjeId() === 2;
	 * ohje1.setOhjeId(4444);
	 * ohje1.getOhjeId() === 4444;
	 * </pre>
	 */
	public int getOhjeId() {
		return ohjeId;
	}
}
