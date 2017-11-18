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
				//sedeplacer1();
				//sedeplacer2();
				sedeplacer5();
				// sedeplacer3();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode sedeplacer2(), version congitif, deplacement intelligent , puis
	 * diagonale si tous les voisins sont découvert j'usqua attendre une zone ou
	 * ya des cases voisines non découverte Case mémoire pour mémoriser les
	 * voisins déjas traiter
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
		// déplacement diagonale)
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
				}

				// deplacement à gauche si voisin non decouvert est sur la
				// gauche
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);

					// deplacement en bas si voisin non decouvert est en bas
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);

					// deplacement en haut si voisin non decouvert est en haut
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);

				}

				// si tous les voisins sont decouvert
			} else {

				i = (int) (Math.random() * voisins.size());
				// on prend un voisin au hasard et on l'ajoute en memoire
				voisin = voisins.get(i);
				memoire.add(voisin);
				if (memoire.size() > 24) {

					// DEPLACEMENT Aléatoire
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
					// sleep(100);
				} 
			}
		}

		// sleep(100);
	}

	/**
	 * Version alétoire
	 */
	public void sedeplacer1() throws InterruptedException {
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;

		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		// tirage d'un nombre alétoire 4 (pour le deplacement diagonale)
		int v = (int) (Math.random() * 4);
		boolean bool = true;

		if (!voisin.isOccupee() && !voisin.isDecouverte()) {
			bool = false;

			// on teste voisinage a ordre 2
			// deplacement à droite si voisin non decouvert est sur la droite
			if (voisin.getX() - this.voiture.getXCourant() == 2) {
				this.voiture.avancer(1, 0);
			}

			// deplacement à gauche si voisin non decouvert est sur la gauche
			else if (voisin.getX() - this.voiture.getXCourant() == -2) {
				this.voiture.avancer(-1, 0);

				// deplacement en bas si voisin non decouvert est en bas
			} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
				this.voiture.avancer(0, 1);

				// deplacement en haut si voisin non decouvert est en haut
			} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
				this.voiture.avancer(0, -1);

			}
		} else {
			if (v == 0) {

				this.voiture.avancer(-1, -1);
				this.voiture.avancer(-1, -1);
				this.voiture.avancer(-1, -1);
				this.voiture.avancer(-1, -1);
			    
				// sleep(100);
			}

			if (v == 1) {

				this.voiture.avancer(-1, 1);
				this.voiture.avancer(-1, 1);
				this.voiture.avancer(-1, 1);
				this.voiture.avancer(-1, 1);
				// sleep(100);
			}
			if (v == 2) {

				this.voiture.avancer(1, -1);
				this.voiture.avancer(1, -1);
				this.voiture.avancer(1, -1);
				this.voiture.avancer(1, -1);
				// sleep(100);
			}
			if (v == 3) {

				this.voiture.avancer(1, 1);
				this.voiture.avancer(1, 1);
				this.voiture.avancer(1, 1);
				this.voiture.avancer(1, 1);
				// sleep(100);
			}
		}
		sleep(100);
	}
	
	public void sedeplacer5() throws InterruptedException {

		// Récuperer la liste des voisins dans une ArrayList
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;

		// Case mêmoire pour enregistrer les voisins déjas traité (dans
		// déplacement diagonale)
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
				//i = (int) (Math.random() * voisins.size());
				//voisin = voisins.get(i);
				//System.out.println(voisin);
				// on l'ajoute à la mémoire
				while (memoire.contains(voisin)){
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}
				memoire.add(voisin);
				
				// si tous les voisins sont tester, on fait un deplacement alétoire
				if (memoire.size()  == voisins.size()) {

					// DEPLACEMENT Aléatoire
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
					// sleep(100);
				} 
			}
		}

		// sleep(100);
	}

}
