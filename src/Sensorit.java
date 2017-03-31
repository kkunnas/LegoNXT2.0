import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Sensorit implements Runnable {

	// Alustukset
	private int viivaVari;
	private int viivaMin;
	private int viivaMax;
	private int jyrkkaMin;
	private int jyrkkaMax;

	private Ajastin ajastin;
	private UltrasonicSensor uSensori;
	private ColorSensor cSensori;
	private Ajaja ajaja;

	Sensorit(ColorSensor cSensori, UltrasonicSensor uSensori, Ajaja ajaja,
			Ajastin ajastin) {
		this.cSensori = cSensori;
		this.uSensori = uSensori;
		this.ajaja = ajaja;
		this.ajastin = ajastin;
	}

	// Tallennetaan valoarvot muuttujaan, lasketaan tarvittavat minimi ja
	// maksimi arvot
	public void asetaValoArvot() {
		// Tallennetaan viivan arvo muuttujaan
		LCD.drawString("Lue viivan", 2, 3);
		LCD.drawString("valoarvo", 3, 4);
		Button.waitForAnyPress();
		viivaVari = cSensori.getLightValue();
		Button.waitForAnyPress();
		LCD.clear();

		// Lasketaan kaartamista varten minimi ja maksimi arvot muuttujiin
		viivaMin = viivaVari - 2;
		viivaMax = viivaVari + 2;
		jyrkkaMin = viivaVari - 15;
		jyrkkaMax = viivaVari + 15;
	}

	public void run() {
		// Laitetaan RGB Sensorin punainen valo p��lle
		cSensori.setFloodlight(true);

		// Tallennetaan valoarvot muuttujaan
		asetaValoArvot();

		// Asetetaan kumman puolen seuraaja
		// Puoli 1 == vasemman puolen seuraaja ja puoli 2 == oikean puolen
		ajaja.setPuoli(1);

		// Asetetaan vaihteenksi 1, jolloin VariSensorin if-lauseet on k�yt�ss�
		ajaja.setVaihe(1);

		// Aloitetaan ajanotto
		ajastin.aloitusaika();

		while (!Button.ESCAPE.isDown()) {

			// Robotti seuraa viivaa ja tutkii ultra��nell� onko edess� estett�
			while (ajaja.getVaihe() == 1) {

				// Vasemman puolen viivan seuraajan kaartamiset
				if (cSensori.getLightValue() > viivaMax
						&& cSensori.getLightValue() <= jyrkkaMax
						&& ajaja.getPuoli() == 1) {
					ajaja.kaarraOikealle();
				}
				if (cSensori.getLightValue() < viivaMin
						&& cSensori.getLightValue() >= jyrkkaMin
						&& ajaja.getPuoli() == 1) {
					ajaja.kaarraVasemmalle();
				}
				if (cSensori.getLightValue() > jyrkkaMax
						&& ajaja.getPuoli() == 1) {
					ajaja.jyrkastiOikealle();
				}
				if (cSensori.getLightValue() < jyrkkaMin
						&& ajaja.getPuoli() == 1) {
					ajaja.jyrkastiVasemmalle();
				}

				// Oikean puolen viivan seuraajan kaartamiset
				if (cSensori.getLightValue() < viivaMin
						&& cSensori.getLightValue() >= jyrkkaMin
						&& ajaja.getPuoli() == 2) {
					ajaja.kaarraOikealle();
				}
				if (cSensori.getLightValue() > viivaMax
						&& cSensori.getLightValue() <= jyrkkaMax
						&& ajaja.getPuoli() == 2) {
					ajaja.kaarraVasemmalle();
				}
				if (cSensori.getLightValue() > jyrkkaMax
						&& ajaja.getPuoli() == 2) {
					ajaja.jyrkastiVasemmalle();
				}
				if (cSensori.getLightValue() < jyrkkaMin
						&& ajaja.getPuoli() == 2) {
					ajaja.jyrkastiOikealle();
				}

				// Jos valoarvo viivaMin ja viivaMax arvojen v�liss�, robotti
				// liikkuu suoraan eteenp�in
				if (cSensori.getLightValue() > viivaMin
						&& cSensori.getLightValue() < viivaMax) {
					ajaja.liiku();
				}

				// Jos havaitaan este, siirryt��n vaiheeseen 2
				if (uSensori.getDistance() < 20) {
					ajaja.setVaihe(2);
				}
			}

			// Robotti v�ist�� estett�
			if (ajaja.getVaihe() == 2) {

				// Jos robotti on vasemman puolen seuraaja, v�istet��n
				// vasemmalta
				if (ajaja.getPuoli() == 1) {
					ajaja.vaistoVasemmalle();

					// V�ist�n j�lkeen, siirryt��n vaiheeseen 3
					ajaja.setVaihe(3);
				}

				// Jos robotti on oikean puolen seuraaja, v�istet��n oikealta
				if (ajaja.getPuoli() == 2) {
					ajaja.vaistoOikealle();

					// V�ist�n j�lkeen, siirryt��n vaiheeseen 3
					ajaja.setVaihe(3);
				}
			}

			// Palataan takaisin viivalle v�ist�n j�lkeen
			if (ajaja.getVaihe() == 3) {

				// Kun valoarvo on valkoisella eli yli viivaMax arvon, robotti
				// liikkuu suoraan eteenp�in
				while (cSensori.getLightValue() > viivaMax) {
					ajaja.liiku();
				}

				// Viivalle palaamisen j�lkeen, siirryt��n takaisin vaiheeseen 1
				ajaja.setVaihe(1);
			}

			// Robotti pys�htyy
			if (ajaja.getVaihe() == 0) {
				ajaja.pysahdy();

				// Lopetetaan ajanotto
				ajastin.lopetusaika();

				// Lasketaan kulunut aika
				LCD.drawString("Aikaa kului", 2, 3);
				LCD.drawString(ajastin.kulunutaika(), 5, 4);
			}

		}
	}

}
