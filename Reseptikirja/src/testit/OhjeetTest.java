package testit;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

import reseptikirja.Lukija;
import reseptikirja.Ohje;
import reseptikirja.Reseptikirja;

/**
 * Test class made by ComTest
 * @version 2018.04.17 15:00:21 // Generated by ComTest
 *
 */
public class OhjeetTest {



  // Generated by ComTest BEGIN
  /** testGetOhjeIdGeneraattori46 */
  @Test
  public void testGetOhjeIdGeneraattori46() {    // Ohjeet: 46
    Lukija lukija = new Lukija(); 
    lukija.poistaTiedostot(); 
    Reseptikirja reseptikirja = new Reseptikirja(); 
    assertEquals("From: Ohjeet line: 51", 0, reseptikirja.getOhjeetLuokka().getOhjeIdGeneraattori()); 
    Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka()); 
    ohje1.getOhjeId(); 
    assertEquals("From: Ohjeet line: 55", 1, reseptikirja.getOhjeetLuokka().getOhjeIdGeneraattori()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetOhjeet67 */
  @Test
  public void testGetOhjeet67() {    // Ohjeet: 67
    Lukija lukija = new Lukija(); 
    lukija.poistaTiedostot(); 
    Reseptikirja reseptikirja = new Reseptikirja(); 
    assertEquals("From: Ohjeet line: 72", "[]", reseptikirja.getOhjeetLuokka().getOhjeet().toString()); 
    Ohje ohje = new Ohje(reseptikirja.getOhjeetLuokka()); 
    ohje.setOhje("Keit�"); 
    assertEquals("From: Ohjeet line: 76", "[1|Keit�]", reseptikirja.getOhjeetLuokka().getOhjeet().toString()); 
    Ohje ohje1 = new Ohje(reseptikirja.getOhjeetLuokka()); 
    ohje1.setOhje("Kaada"); 
    assertEquals("From: Ohjeet line: 80", "[1|Keit�, 2|Kaada]", reseptikirja.getOhjeetLuokka().getOhjeet().toString()); 
  } // Generated by ComTest END
}