package reseptikirja;

/**
 * Hallitsee aineksia
 * 
 * @author Mikko Karkee, Kusti Janatuinen
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 17.4.2018
 */
public class Ainekset {

	
	private Aines[] ainekset = new Aines[3];
	private int ainesIdGeneraattori;
	
	
	/**
	 * Constructor
	 */
	public Ainekset() {
		Lukija lukija = new Lukija();
		
		if (lukija.lueTiedosto(lukija.getAinekset()) == null) lukija.luoTiedosto(lukija.getAinekset());
		
		StringBuilder idLuku = new StringBuilder(lukija.lueTiedosto(lukija.getAinekset()));
		
		if (idLuku.length() < 1) ainesIdGeneraattori = 0;
		else {
			String idLukuString = idLuku.subSequence(0, idLuku.indexOf("\n")).toString();
			ainesIdGeneraattori = Integer.valueOf(idLukuString);
		}
		
		// asettaa taulukon alkiot nulliksi
		for(int i = 0; i < ainekset.length; i++) {
			ainekset[i]	= null;
		}
	}
	
	
	/**
	 * Palauttaa ainekset taulun
	 * @return ainekset[]
	 */
	public Aines[] getAinekset() {
		return ainekset;
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
	 * 
	 * reseptikirja.getAineksetLuokka().getAinesIdGeneraattori() === 0;
	 * Aines aines = new Aines(reseptikirja.getAineksetLuokka(),"peruna",true,true,true);
	 * Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"peruna",true,true,true);
	 * reseptikirja.getAineksetLuokka().getAinesIdGeneraattori() === 2;
	 * aines.setGluteeniton(true);
	 * aines1.setGluteeniton(true);
	 * 
     * lukija.poistaTiedostot();
	 * </pre>
	 */
	public int getAinesIdGeneraattori() {
		return ainesIdGeneraattori;
	}
	
	
	/**
	 * Nostaa IdGen lukua yhdell‰.
	 */
	public void nostaAinesIdGeneraattori() {
		ainesIdGeneraattori++;
	}
	
	
	/**
	 * Lis‰‰ aineksen ensimm‰iselle null kohdalle ainekset taulukkoon
	 * @param aines Aines
	 */
	public void lisaaAinesTaulukkoon(Aines aines) {
		if (ainekset.length == laskeTaulukonAlkiot()) {
			Aines[] aineksetIsompi = new Aines[ainekset.length + 1];
			for (int i = 0; i < ainekset.length; i++) {
				aineksetIsompi[i] = ainekset[i];
			}
			ainekset = aineksetIsompi;
		}
		
		for (int i = 0 ; i < ainekset.length; i++) {
			if (ainekset[i] == null) {
				setAines(i, aines);
				break;
			}
		}
	}
	
	
	/**
	 * Asettaa aineksen annettuun ainekset taulukon indeksiin
	 * @param indeksi paikka johon lis‰t‰‰n
	 * @param aines joka lis‰t‰‰n
	 */
	public void setAines(int indeksi, Aines aines) {
		ainekset[indeksi] = aines;
	}
	
	
	/**
	 * Laskee taulukon ei null alkiot
	 * @return ei null alkioiden m‰‰r‰
	 * @example
	 * <pre name="test">
     * Lukija lukija = new Lukija();
     * lukija.poistaTiedostot();
	 * 
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 *
	 * reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot() === 0;
	 * Aines aines = new Aines(reseptikirja.getAineksetLuokka(), "vesi", true, true, true);
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
	 * reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot() === 1;
	 * reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
	 * reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot() === 2;
	 * 
     * lukija.poistaTiedostot();
	 * </pre>
	 */
	public int laskeTaulukonAlkiot() {
		int summa = 0;
		for (int i = 0; i < ainekset.length; i++) {
			if (ainekset[i] != null) {
				summa++;
			}
		}
		return summa;
	}
	
	
	/**
	 * J‰rjest‰‰ taulukosta nullat per‰lle
	 */
	public void jarjestaAinekset() {
		if (ainekset == null || ainekset.length < 1) return;
		
		for (int i = 0; i < ainekset.length - 1; i++) {
			if (ainekset [i] == null) {
				ainekset[i] = ainekset[i + 1];
				ainekset[i + 1] = null;
			}
		}
	}
	
	
	/**
	 * Testip‰‰ohjelma
	 * @param args ei k‰ytˆss‰
	 */
	public static void main(String[] args) {
		Reseptikirja reseptikirja = new Reseptikirja();
		reseptikirja.getReseptitLuokka();
		
		Aines aines = new Aines(reseptikirja.getAineksetLuokka(),"peruna",true,true,true);
		Aines aines1 = new Aines(reseptikirja.getAineksetLuokka(),"kaali",true,true,true);
		reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines);
		reseptikirja.getAineksetLuokka().lisaaAinesTaulukkoon(aines1);
		reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot();
		
		int summa = reseptikirja.getAineksetLuokka().laskeTaulukonAlkiot();
		System.out.println(summa);
		
	}
}
