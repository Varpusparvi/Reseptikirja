package testit;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import reseptikirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2018.04.18 15:24:57 // Generated by ComTest
 *
 */
public class ReseptitTest {



  // Generated by ComTest BEGIN
  /** testGetReseptiIdGeneraattori51 */
  @Test
  public void testGetReseptiIdGeneraattori51() {    // Reseptit: 51
    Reseptikirja reseptikirja = new Reseptikirja(); 
    assertEquals("From: Reseptit line: 54", 0, reseptikirja.getReseptitLuokka().getReseptiIdGeneraattori()); 
    Resepti velli = new Resepti(reseptikirja.getReseptitLuokka(), "velli"); 
    Resepti resepti1 = new Resepti(reseptikirja.getReseptitLuokka(),"perunamuussi"); 
    reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(velli); 
    reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(resepti1); 
    assertEquals("From: Reseptit line: 59", 2, reseptikirja.getReseptitLuokka().getReseptiIdGeneraattori()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetLkm103 */
  @Test
  public void testGetLkm103() {    // Reseptit: 103
    Reseptikirja reseptikirja = new Reseptikirja(); 
    assertEquals("From: Reseptit line: 106", 0, reseptikirja.getReseptitLuokka().getLkm()); 
    Resepti velli = new Resepti(reseptikirja.getReseptitLuokka(), "velli"); 
    reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(velli); 
    assertEquals("From: Reseptit line: 109", 1, reseptikirja.getReseptitLuokka().getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLaskeTaulukonAlkiot121 */
  @Test
  public void testLaskeTaulukonAlkiot121() {    // Reseptit: 121
    Reseptikirja reseptikirja = new Reseptikirja(); 
    assertEquals("From: Reseptit line: 124", 0, reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot()); 
    Resepti aines = new Resepti(reseptikirja.getReseptitLuokka(), "vesi"); 
    reseptikirja.getReseptitLuokka().lisaaReseptiTaulukkoon(aines); 
    assertEquals("From: Reseptit line: 127", 1, reseptikirja.getReseptitLuokka().laskeTaulukonAlkiot()); 
  } // Generated by ComTest END
}