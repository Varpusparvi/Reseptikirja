package reseptikirja;

/**
 * Hallitsee reseptej‰
 * 
 * @author Mikko Karkee, Kusti Janatuinen
 * mikko.karkee1@gmail.com, kustijanatuinen@gmail.com
 * 18.4.2018
 */
public class Reseptit {
	private Resepti[] reseptit = new Resepti[3];
	private int reseptiIdGeneraattori;
	private int lkm = 0;
	
	
	/**
	 * Constructor Reseptit luokalle
	 */
	public Reseptit() {
		Lukija lukija = new Lukija();
		
		if (lukija.lueTiedosto(lukija.getReseptit()) == null) lukija.luoTiedosto(lukija.getReseptit());
		StringBuilder idLuku = new StringBuilder(lukija.lueTiedosto(lukija.getReseptit()));
		
		if (idLuku.length() < 1) reseptiIdGeneraattori = 0;
		else {
			String idLukuString = idLuku.subSequence(0,(idLuku.indexOf("\n"))).toString();
			reseptiIdGeneraattori = Integer.valueOf(idLukuString);
		}
		
		// Asettaa taulukon alkiot nulliksi
		for(int i = 0; i < reseptit.length; i++) {
			reseptit[i]	= null;
		}
	}
	
	
	/**
	 * Palauttaa reseptit taulun
	 * @return reseptit[]
	 */
	public Resepti[] getReseptit() {
		return reseptit;
	}
	
	
	/**
	 * Palauttaa IdGeneraattorin
	 * @return kokonaisluku, jonka perusteella annetaan Id oliolle
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * reseptikirja.getReseptitLuokka().getReseptiIdGeneraattori() === 0;
	 * Resepti velli = new Resepti(reseptikirja.getReseptitLuokka(), "velli");
	 * Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(),"perunamuussi");
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(velli);
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1);
	 * reseptikirja.getReseptitLuokka().getReseptiIdGeneraattori() === 2;
	 * </pre>
	 */
	public int getReseptiIdGeneraattori() {
		return reseptiIdGeneraattori;
	}
	
	
	/**
	 * Nostaa IdGen lukua yhdell‰.
	 */
	public void nostaReseptiIdGeneraattori() {
		reseptiIdGeneraattori++;
		
	}
	
	
	/**
	 * Lis‰‰ reseptin ensimm‰iselle null kohdalle reseptit taulukkoon
	 * @param resepti Resepti
	 */
	public void lisaaReseptiTaulukkoon(Resepti resepti) {
		if (reseptit.length == laskeTaulukonAlkiot()) {
			Resepti[] reseptitIsompi = new Resepti[reseptit.length + 1];
			for (int i = 0; i < reseptit.length; i++) {
				reseptitIsompi[i] = reseptit[i];
			}
			reseptit = reseptitIsompi;
		}
		
		for (int i = 0 ; i < reseptit.length; i++) {
			if (reseptit[i] == null) {
				setResepti(i, resepti);
				break;
			}			
		}
		lkm++;
	}
	
	
	/**
	 * Palauttaa reseptikirjan reseptien lukum‰‰r‰n.
	 * @return reseptien lukum‰‰r‰
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 * 
	 * reseptikirja.getReseptitLuokka().getLkm() === 0;
	 * Resepti velli = new Resepti(reseptikirja.getReseptitLuokka(), "velli");
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(velli);
	 * reseptikirja.getReseptitLuokka().getLkm() === 1;
	 * </pre>
	 */
	public int getLkm( ) {
		return lkm;
	}
	
	
	/**
	 * Laskee reseptit taulukon alkioiden m‰‰r‰n
	 * @return ei null alkioiden m‰‰r‰
	 * @example
	 * <pre name="test">
	 * Reseptikirja reseptikirja = new Reseptikirja();
	 *
	 * reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot() === 0;
	 * Resepti aines = new Resepti(reseptikirja.getReseptitLuokka(), "vesi");
	 * reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(aines);
	 * reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot() === 1;
	 * 
	 * </pre>
	 */
	public int laskeTaulukonAlkiot() {
		int summa = 0;
		for (int i = 0; i < reseptit.length; i++) {
			if (reseptit[i] != null) {
				summa++;
			}
		}
		return summa;
	}
	
	
	/**
	 * J‰rjest‰‰ taulukosta nullat per‰lle
	 */
	public void jarjestaReseptit() {
		if (reseptit == null || reseptit.length < 1) return;
		
		for (int i = 0; i < reseptit.length - 1; i++) {
			if (reseptit [i] == null) {
				reseptit[i] = reseptit[i + 1];
				reseptit[i + 1] = null;
			}
		}
	}
	
	
	/**
	 * Asettaa reseptin annettuun reseptit taulukon indeksiin
	 * @param indeksi paikka johon lis‰t‰‰n
	 * @param resepti resepti joka lis‰t‰‰n
	 */
	public void setResepti(int indeksi, Resepti resepti) {
		reseptit[indeksi] = resepti;
	}
}
