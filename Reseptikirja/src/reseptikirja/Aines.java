package reseptikirja;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Reseptin aines, sis‰lt‰‰ id, nimen, sek‰ kolme booleania GLV
 * 
 * @author Kusti Janatuinen, Mikko Karkee
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class Aines {

	
	private Ainekset aineksetLuokka;
	private int ainesId;
	private String aineksenNimi;
	private boolean vegaaninen;
	private boolean laktoositon;
	private boolean gluteeniton;
	
	
	/**
	 * M‰‰ritt‰‰ aineksen ominaisuudet
	 * @param ainekset Ainekset instanssi johon kuuluu
	 * @param aineksennimi String muotoinen aineksen nimi
	 * @param vegaaninen boolean
	 * @param laktoositon boolean
	 * @param gluteeniton boolean
	 */
	public Aines(Ainekset ainekset, String aineksennimi ,boolean vegaaninen, boolean laktoositon, boolean gluteeniton) {
		aineksetLuokka = ainekset;
		this.ainesId = ainekset.getAinesIdGeneraattori() + 1;
		ainekset.nostaAinesIdGeneraattori(); 				//Nostaa IdGen lukua yhdell‰
		this.aineksenNimi = aineksennimi;
		this.vegaaninen = vegaaninen;
		this.laktoositon = laktoositon;
		this.gluteeniton = gluteeniton;		
	}
	
	
	/**
	 * M‰‰ritt‰‰ aineksen ominaisuudet
	 * @param ainekset yl‰luokka johon kuuluu
	 * @param aineksennimi aineksen nimi
	 */
	public Aines(Ainekset ainekset, String aineksennimi) {
		aineksetLuokka = ainekset;
		this.ainesId = ainekset.getAinesIdGeneraattori() + 1;
		ainekset.nostaAinesIdGeneraattori(); 				//Nostaa IdGen lukua yhdell‰
		this.aineksenNimi = aineksennimi;
		this.vegaaninen = false;
		this.laktoositon = false;
		this.gluteeniton = false;	
	}
	
	
	/**
	 * Tallentaa aineksen tiedostoon.
	 */
	public void tallennaAines() {
		Lukija lukija = new Lukija();
		lukija.luoTiedosto(lukija.getAinekset());
		lukija.tallennaIdTiedostoon(lukija.getAinekset(), String.valueOf(aineksetLuokka.getAinesIdGeneraattori()));
		lukija.tallennaTiedostoon(lukija.getAinekset(), this.toStringTallennus());
	}
	
	
	/**
	 * Palauttaa aineksen Id:n
	 * @return ainesId
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 *
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"kaali");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].getAinesId() === 1;
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].getAinesId() === 2;
	 * </pre>
	 */
	public int getAinesId() {
		return ainesId;
	}
	
	
	/**
	 * Palauttaa aineksen nimen
	 * @return nimi
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"kaali");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].getAinesNimi() === "peruna";
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].getAinesNimi() === "kaali";
	 * </pre>
	 */
	public String getAinesNimi() {
		return aineksenNimi;
	}

	
	/**
	 * Palauttaa onko aines vegaaninen
	 * @return boolean
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 *
	 * Aines aines1= new Aines(reseptikirja.getAineksetLuokka(),"peruna");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"kaali");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].getVegaaninen() === false;
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].getVegaaninen() === false;
	 * 
	 * 
	 * </pre>
	 */
	public boolean getVegaaninen() {
		return vegaaninen;
	}
	
	
	/**
	 * Palauttaa onko aines laktoositon
	 * @return boolean
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"kaali");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].getLaktoositon() === false;
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].getLaktoositon() === false;
	 * </pre>
	 */
	public boolean getLaktoositon() {
		return laktoositon;
	}
	
	
	/**
	 * Palauttaa onko aines gluteeniton
	 * @return boolean
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 *
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"kaali");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].getGluteeniton() === false;
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].getGluteeniton() === false;
	 * 
	 * 
	 * </pre>
	 */
	public boolean getGluteeniton() {
		return gluteeniton;
	}
	
	
	/**
	 * Asettaa ainekselle Id:n
	 * @param id joka ainekselle asetetaan
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
     * 
     * aines1.getAinesId() === 1;
     * aines1.setAinesId(4444);
     * aines1.getAinesId() === 4444;
     * </pre>
     */
	public void setAinesId(int id) {
		ainesId = id;
	}
	
	
	/**
	 * Asettaa aineksen nimen
	 * @param nimi haluttu nimi
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna");
     * 
     * aines1.getAinesId() === 1;
     * aines1.setAinesNimi("kurkku");
     * aines1.getAinesNimi() === "kurkku";
     * </pre>
     */
	public void setAinesNimi(String nimi) {
		aineksenNimi = nimi;
	}

	
	/**
	 * Asettaa onko aines vegaaninen
	 * @param b boolean
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"porkkana");
     * 
     * aines1.getAinesId() === 1;
     * aines1.setVegaaninen(true);
     * aines1.getVegaaninen() === true;
     * </pre>
     */
	public void setVegaaninen(boolean b) {
		vegaaninen = b;
	}
	
	
	/**
	 * Asettaa onko aines laktoositon
	 * @param b boolean
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"laktoositon maito");
     * 
     * aines1.getAinesId() === 1;
     * aines1.setLaktoositon(true);
     * aines1.getLaktoositon() === true;
     * </pre>
     */
	public void setLaktoositon(boolean b) {
		laktoositon = b;
	}
	
	
	/**
	 * Asettaa onko aines gluteeniton
	 * @param b boolean
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja reseptikirja = new Reseptikirja();
     * 
     * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"gluteeniton maito");
     * 
     * aines1.getAinesId() === 1;
     * aines1.setGluteeniton(true);
     * aines1.getGluteeniton() === true;
     * </pre>
     */
	public void setGluteeniton(boolean b) {
		gluteeniton = b;
	}
	
	
	/**
	 * Muuttaa olion ominaisuudet String muotoon
	 * @return tallennusmuodossa oleva olio
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"1|vesi|true|true|true");
	 * Aines aines2 = new Aines(reseptikirja.getAineksetLuokka(),"2|pasta|true|true|false");
	 * 
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines2);
	 * 
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].toString() === "1|vesi|true|true|true";
	 * reseptikirja.getAineksetLuokka().getAinekset()[1].toString() === "2|pasta|true|true|false";
	 * </pre>
	 */
	public String toStringTallennus() {
		String vegaaninen_string   = String.valueOf(vegaaninen);
		String laktoositon_string  = String.valueOf(laktoositon);
		String gluteeniton_string  = String.valueOf(gluteeniton);
		return String.format("%s|%s|%s|%s|%s",ainesId, aineksenNimi, vegaaninen_string, laktoositon_string, gluteeniton_string);
	}
	
	
	/**
	 * Palauttaa aineksen nimen
	 * @return aineksenNimi
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"1|kaali|true|true|true");
	 *  
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
	 *  
	 * reseptikirja.getAineksetLuokka().getAinekset()[0].toString() === "1|kaali|true|true|true";
	 */
	@Override
	public String toString() {
		return aineksenNimi;
	}
	
	
	/**
	 * Parsee aineksen tiedostosta
	 * @param rivi josta parsetaan
     * @example
     * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
     * 
     * Reseptikirja rk = new Reseptikirja();
     * Aines aines = new Aines(rk.getAineksetLuokka(), "kaali");
     * 
     * aines.toStringTallennus() === "1|kaali|false|false|false";
     * aines.parseAines("13|kaali|true|true|true");
     * aines.toStringTallennus() === "13|kaali|true|true|true";
     * </pre>
     */
	public void parseAines(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		setAinesId(Integer.valueOf(Mjonot.erota(sb, '|')));
		setAinesNimi(Mjonot.erota(sb, '|'));
		setVegaaninen(Boolean.valueOf(Mjonot.erota(sb, '|')));
		setLaktoositon(Boolean.valueOf(Mjonot.erota(sb, '|')));
		setGluteeniton(Boolean.valueOf(Mjonot.erota(sb, '|')));
	}
	
	
	/**
	 * Testip‰‰ohjelma
	 * @param args ei k‰ytˆss‰
	 */
	public static void main(String[] args) {
		Reseptikirja reseptikirja = new Reseptikirja();
		Aines aines = new Aines(reseptikirja.getAineksetLuokka(),"Peruna", true, true, false);
		Aines uusi = new Aines(reseptikirja.getAineksetLuokka(), "Testi");
		uusi.parseAines(aines.toString());
		
		System.out.println(aines.toString());
		System.out.println(uusi.toString());
	}
}
