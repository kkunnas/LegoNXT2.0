import java.io.File;

import lejos.nxt.LCD;
import lejos.nxt.Sound;

/**
 * Ajastin laskee kuluneen ajan kahden pisteen v�lilt� ja n�ytt�� tuloksen
 * 
 * @author Toni Lavonen
 * @version 1.0
 * @since 29.3.2017
 */
public class Ajastin {

	// Alustukset
	private long aloitus;
	private long lopetus;
	private long erotus;
	private long minuutit;
	private long sekunnit;
	private long kulunutaika;

	/** 
	 * L�hdet��nliikkeelle viiden sekunnin j�lkeen
	 * N�ytet��n j�ljell� olevat sekunnit n�yt�ll�
	 */
	
	// Suoritetaan viiden sekunnin l�ht�laskenta
	public void liikkeelleLahto() {

		LCD.drawString("Aloittamiseen", 2, 2);
		for (int i = 5; i > 0; i--) {
			Sound.playSample(new File("luku.wav"));
			LCD.drawString("aikaa " + i + " sek", 3, 3);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LCD.clear();
	}

	/**
	 * Hakee ajan millisekuntteina kutsumalla System.currentTimeMillis() ja
	 * sijoittaa sen muuttujaan long aloitus.
	 */

	// Aloitetaan ajanotto
	public void aloitusaika() {
		aloitus = System.currentTimeMillis();
	}

	/**
	 * Hakee ajan millisekuntteina kutsumalla System.currentTimeMillis() ja
	 * sijoittaa sen muuttujaan long lopetus.
	 */

	// Lopetetaan ajanotto
	public void lopetusaika() {
		lopetus = System.currentTimeMillis();
	}

	/**
	 * Laskee muuttujien long lopetus ja long aloitus erotuksen ja sijoittaa
	 * arvon muuttujaan double erotus. Jakaa muuttujan double erotus arvon
	 * tuhannella ja asettaa arvon muuttujaan double kulunutaika. N�ytt�� ajan
	 * robotin n�yt�ll�.
	 * 
	 * @return palauttaa muuttujen kulunutaika
	 */

	// Lasketaan kulunut aika
	public String kulunutaika() {
		erotus = lopetus - aloitus;
		
		// Jaetaan arvo tuhannella.
		kulunutaika = erotus / 1000;
		minuutit = (kulunutaika / 60);
		sekunnit = (kulunutaika) - (minuutit * 60);
		
		return minuutit + " min " + sekunnit + " sek";
		
	}

}
