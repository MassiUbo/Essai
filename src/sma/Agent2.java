package sma;

import java.util.ArrayList;

import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit dedant le traitement que les agent font sur la
 * carte et cela via les méthodes sedeplacer(), sedeplacer2(), sedeplacer3()
 * 
 * @author MASSI
 *
 */
public class Agent2 extends Thread {

	private Vehicule voiture;

	// Constructure
	public Agent2(Vehicule voiture) {
		this.voiture = voiture;
	}

	// Getter
	public Vehicule getVoiture() {
		return voiture;
	}

	/**
	 * Méthode run() , lancement des agents
	 */

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				sedeplacer1();
				// sedeplacer2();
				// sedeplacer3();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void sedeplacer1() throws InterruptedException {

		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;
		ArrayList<Case> memoire = new ArrayList<>();
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		int v = (int) (Math.random() * 4);
		boolean bool = true;
		// Case voisin2 = null;

		while (bool) {
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {
				bool = false;
				// on teste voisinage a ordre 2

				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
				}

				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);

				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);

				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);

				}

			} else {
				i = (int) (Math.random() * voisins.size());
				voisin = voisins.get(i);
				memoire.add(voisin);
				if (memoire.size() > 24) {

					// DEPLACEMENT Aléatoire
					if (v == 0) {
						boolean bool1 = true;

						while (bool1) {

							if (voisin.isOccupee()) {
								break;
							}

							this.voiture.avancer(-1, -1);
							sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24 || voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 1) {
						boolean bool1 = true;
						while (bool1) {
							if (voisin.isOccupee()) {
								break;
							}
							this.voiture.avancer(1, 1);
							sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24 || voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 2) {
						boolean bool1 = true;
						while (bool1) {

							if (voisin.isOccupee()) {
								break;
							}

							this.voiture.avancer(-1, 1);
							sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24 || voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 3) {
						boolean bool1 = true;
						while (bool1) {
							if (voisin.isOccupee()) {
								break;
							}
							this.voiture.avancer(1, -1);
							sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24 ||voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					}
					bool = false;
					sleep(100);
				}
			}
		}

		sleep(100);
	}

}
