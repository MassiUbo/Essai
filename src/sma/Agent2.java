package sma;

import java.util.ArrayList;
import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit dedant le traitement que les agent font sur la
 * carte et cela via les méthodes sedeplacer1(), sedeplacer2(), sedeplacer3()
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
				// sedeplacer1();
				 sedeplacer2();
				//sedeplacer3();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Version alétoire
	 */

	public void sedeplacer1() throws InterruptedException {
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;

		ArrayList<Case> memoire = new ArrayList<>();
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);
		// tirage d'un nombre alétoire 4 (pour le deplacement diagonale)
		boolean bool = true;
		int v = (int) (Math.random() * 4);

		while (bool) {
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {
				bool = false;

				// on teste voisinage a ordre 2
				// deplacement à droite si voisin non decouvert est sur la
				// droite
				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
					memoire.add(voisin);
				}
				// deplacement à gauche si voisin non decouvert est sur la
				// gauche
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);
					memoire.add(voisin);
					// deplacement en bas si voisin non decouvert est en bas
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					memoire.add(voisin);
					// deplacement en haut si voisin non decouvert est en haut
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
					memoire.add(voisin);
				}

			} else {
				while (memoire.contains(voisin)) {
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}
				memoire.add(voisin);

				if (memoire.size() == voisins.size()) {
					if (v == 1)
						this.voiture.avancer(1, 1);
					else if (v == 2)
						this.voiture.avancer(1, -1);
					else if (v == 3)
						this.voiture.avancer(-1, 1);
					else if (v == 0)
						this.voiture.avancer(-1, -1);
					// sleep(100);
					bool = false;
				}
			}
			// sleep(1);
		}
	}

	/**
	 * Méthode sedeplacer2(), version congitif, deplacement intelligent , puis
	 * diagonale si tous les voisins sont découvert j'usqua atteindre une zone ou
	 * ya des cases voisines non découverte
	 *  Case mémoire pour mémoriser les voisins déjas traiter
	 * 
	 * @throws InterruptedException
	 *             (si le thread est interomppu)
	 */
	public void sedeplacer2() throws InterruptedException {

		// Récuperer la liste des voisins dans une ArrayList
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;

		// Case mêmoire pour enregistrer les voisins déjas traité (dans
		ArrayList<Case> memoire = new ArrayList<>();

		// tirage d'un nombre alétoire i parmis (pour tiré un voisin)
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		// tirage d'un nombre alétoire 4 (pour le deplacement diagonale)
		int v = (int) (Math.random() * 4);
		boolean bool = true;

		// On sort de la boucle une fois un des deux test à été fait

		while (bool) {

			// on minimise le nombre de voisins au voisins à deux distance
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {
				bool = false;

				// on teste voisinage a ordre 2
				// deplacement à droite si voisin non decouvert est sur la
				// droite
				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
					memoire.add(voisin);
				}

				// deplacement à gauche si voisin non decouvert est sur la
				// gauche
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);
					memoire.add(voisin);

					// deplacement en bas si voisin non decouvert est en bas
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					memoire.add(voisin);

					// deplacement en haut si voisin non decouvert est en haut
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
					memoire.add(voisin);

				}

			} else {
				// on prend un autre voisin au hazard
				// on l'ajoute à la mémoire
				while (memoire.contains(voisin)) {
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}
				memoire.add(voisin);

				// si tous les voisins sont tester, on fait un deplacement
				// alétoire
				if (memoire.size() == voisins.size()) {

					/**
					 * Deplacemment aléatoire (en diagonale) j'usqu'a attendre une zone ou
					 * ya une zone non decouverte
					 * ou frontière
					 * ou l'une des case voisine est occuper par un autre vehicule (eviter interblocage)
					 */
					if (v == 0) {
						boolean bool1 = true;
						while (bool1) {
							this.voiture.avancer(-1, -1);
							// sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
										|| voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 1) {
						boolean bool1 = true;
						while (bool1) {

							this.voiture.avancer(1, 1);
							// sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
										|| voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 2) {
						boolean bool1 = true;
						while (bool1) {

							this.voiture.avancer(-1, 1);
							// sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
										|| voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					} else if (v == 3) {
						boolean bool1 = true;
						while (bool1) {

							this.voiture.avancer(1, -1);
							// sleep(100);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j < voisins2.size(); j++) {
								if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
										|| voisins2.get(j).isOccupee()) {
									bool1 = false;
									break;

								}
							}
						}
					}
					bool = false;
				}
			}
		}

		// sleep(100);
	}

	/**
	 * Version supplémentaire : deviser la carte par les 4 vehicule, chaqu'un
	 * s'occupe dune partie quelque soit la carte , les vehicules vont explorer
	 * toute la carte dans un temps record Nb: cette verssion s'adapte a toute
	 * les les carte mais fonctionne que avec 4 vehicule !
	 * 
	 * @throws InterruptedException
	 */
	public void sedeplacer3() throws InterruptedException {

		// rouge vert bleu jaune
		// agent1.start(); //(rouge) 0
		// agent2.start(); // verte 1
		// agent3.start(); // bleu 2
		// agent4.start(); // jaune 3

		ArrayList<Case> voisins2 = this.voiture.getVoisinage();

		Case case1 = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
		if (this.voiture.getID() == 0) {

			System.out.println("yyyyyyyy" + case1.getY());
			while (true) {
				while (true) {
					this.voiture.avancer(-1, 0);
					sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != 0)
						break;
				}

				this.voiture.avancer(0, -1);
				sleep(1);
				this.voiture.avancer(0, -1);

				while (true) {
					this.voiture.avancer(1, 0);
					sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, -1);
				sleep(1);
				this.voiture.avancer(0, -1);

			}

		}

		if (this.voiture.getID() == 1) {

			System.out.println("yyyyyyyy2" + case1.getY());
			while (true) {
				while (true) {
					this.voiture.avancer(+1, 0);
					sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != 0)
						break;
				}

				this.voiture.avancer(0, -1);
				sleep(1);
				this.voiture.avancer(0, -1);

				while (true) {
					this.voiture.avancer(-1, 0);
					sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, -1);
				sleep(1);
				this.voiture.avancer(0, -1);

			}

		}

		if (this.voiture.getID() == 2) {
			System.out.println("yyyyyyyy3" + case1.getY());
			while (true) {
				while (true) {
					this.voiture.avancer(-1, 0);
					sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24)
						break;
				}

				this.voiture.avancer(0, +1);
				sleep(1);
				this.voiture.avancer(0, +1);

				while (true) {
					this.voiture.avancer(1, 0);
					sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, +1);
				sleep(1);
				this.voiture.avancer(0, +1);

				System.out.println(this.voiture.getYCourant());
			}

		}

		if (this.voiture.getID() == 3) {

			while (true) {
				while (true) {
					this.voiture.avancer(1, 0);
					sleep(1);
					System.out.println("cc");
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24)
						break;
				}

				this.voiture.avancer(0, 1);
				sleep(1);
				this.voiture.avancer(0, 1);

				while (true) {
					this.voiture.avancer(-1, 0);
					sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, 1);

				this.voiture.avancer(0, 1);
				sleep(1);

			}

		}

		sleep(1);
	}

}
