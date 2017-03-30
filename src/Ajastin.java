import lejos.nxt.Button;

/**
 * <h1>Ajastin</h1>
 * <p>
 * Ajastinta käytetään ajan laskemiseen.
 * </p>
 * <h2>Luokan muuttujat:</h2>
 * <p>
 * private long aloitus private long lopetus private double erotus private
 * double kulunutaika
 * <p>
 * <h2>Luokkan metodit:/
 * <h2>
 * <p>
 * aloitusaika() Sijoittaa sen hetkisen järjestelmäajan millisekunteina
 * muuttujaan aloitus lopetusaika() Sijoittaa sen hetkisen järjestelmäajan
 * millisekunteina muuttujaan lopetus kulunutaika() Sijoittaa muuttujien aloitus
 * ja lopetus erotuksen muuttujaan erotus Erotus jaetaan tuhannella ja
 * sijoitetaan muuttujaan kulunutaika
 * </p>
 * 
 * @author Toni Lavonen
 * @version 1.0
 * @since 29.3.2017
 */
public class Ajastin {

	// Alustukset
	private long aloitus;
	private long lopetus;
	private double erotus;
	private double kulunutaika;

	// Aloitetaan ajanotto
	public void aloitusaika() {
		// System.currentTimeMillis() palauttaa järjestelmäajan millisekunteina.
		aloitus = System.currentTimeMillis();
	}

	// Lopetetaan ajanotto
	public void lopetusaika() {
		lopetus = System.currentTimeMillis();
	}

	// Lasketaan kulunut aika
	public void kulunutaika() {
		erotus = lopetus - aloitus;
		// Erotus on millisekuntteina. Muutetaan erotus sekunneiksi jakamalla
		// arvo tuhannella.
		kulunutaika = erotus / 1000;
		System.out.println(kulunutaika);
		Button.waitForAnyPress();
	}

}
