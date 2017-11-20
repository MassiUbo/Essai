package sma;

import java.util.ArrayList;
import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit dedant le traitement que les agent font sur la
 * carte et cela via les méthodes sedeplacer1(), sedeplacer2(), sedeplacer3()
 * 
 * @author MASSINISSA
 *
 */

public class Agent2 extends Thread {

	private Vehicule voiture;
	private static ArrayList<Case> voisinCOm = new ArrayList<Case>();

	public Agent2(Vehicule voiture) {
		this.voiture = voiture;
	    voisinCOm.add(new Case(voiture.getXCourant(), voiture.getYCourant()));
		System.out.println(voisinCOm);
	}

	public Vehicule getVoiture() {
		return voiture;
	}

	/**
	 * Méthode run() , lancement des agents Choix de l'une dé méthode de
	 * comportement: sedeplacer1(), sedeplacer2(), sedeplacer3()
	 */

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				 //sedeplacer1();
				//sedeplacer2();
				// sedeplacer3();
				 sedeplacer4();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Version (1) deplacement haut, bas , gauche, droite puis aléatoire si
	 * toutes les cases voisines sont dévouvertes
	 */

	public void sedeplacer1() throws InterruptedException {
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;

		/**
		 * Mémoire propre à lagent pour sauvgarder les voisins dejas traités
		 */
		ArrayList<Case> memoire = new ArrayList<>();
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);
		boolean bool = true;

		/**
		 * Tirage nombre aléatoire pour designer un comportement aléatoire
		 */
		int v = (int) (Math.random() * 4);

		while (bool) {
			/**
			 * On limite les voisins au voisins visibles à deux
			 */
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {

				/**
				 * Des qu'il ya un deplacement on sort de la boucle
				 */
				bool = false;

				/**
				 * Deplacement à droite si voisin tiré est de coté droit
				 */
				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
					memoire.add(voisin);
				}
				/**
				 * deplacement à gauche si voisin tiré est sur la gauche
				 */
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);
					memoire.add(voisin);
					/**
					 * deplacement en bas si voisin non decouvert est en bas
					 */
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					memoire.add(voisin);
					/**
					 * deplacement en haut si voisin non decouvert est en haut
					 */
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
					memoire.add(voisin);
				}

			} else {

				/**
				 * si non on ajoute le voisin à la mémoire
				 */
				while (memoire.contains(voisin)) {
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}
				memoire.add(voisin);

				/**
				 * Si tous les voisins sont traité et qu'il sont tous decouvert,
				 * deplacement alétoire
				 */

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

			// sleep(100);
		}
	}

	/**
	 * Méthode sedeplacer2(), version congitif, deplacement intelligent , puis
	 * diagonale si tous les voisins sont découvert j'usqua atteindre une zone
	 * ou ya des cases voisines non découverte et reprendre le comportement
	 * intelligent Case mémoire pour mémoriser les voisins déjas traiter
	 * 
	 * @throws InterruptedException
	 *             (si le thread est interomppu)
	 */
	public void sedeplacer2() throws InterruptedException {

		// Récuperer la liste des voisins dans une ArrayList
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		System.out.println(voisins.size());
		Case voisin = null;
		ArrayList<Case> memoire = new ArrayList<>();

		/**
		 * tirage d'un nombre alétoire i parmis (pour tiré un voisin)
		 */
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		/**
		 * tirage d'un nombre alétoire 4 (pour le deplacement diagonale)
		 */
		int v = (int) (Math.random() * 4);
		boolean bool = true;

		while (bool) {

			/**
			 * on minimise le nombre de voisins au voisins à deux distance
			 */
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {
				bool = false;

				/**
				 * deplacement à droite si voisin non decouvert est sur la
				 * droite gauche, haut bas
				 */

				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
					memoire.add(voisin);
				}

				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);
					memoire.add(voisin);

				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					memoire.add(voisin);

				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
					memoire.add(voisin);

				}

			} else {
				/**
				 * si non Tirage voisin et ajout à la mémoire
				 */
				while (memoire.contains(voisin)) {
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}
				memoire.add(voisin);

				/**
				 * si tous les voisins sont tester, on fait un deplacement en
				 * diagonale j'usqua trouver un voisin non decouvert, frontière
				 * ou zone occuper par un autre agent pour eviter blocage
				 */

				if (memoire.size() == voisins.size()) {

					/**
					 * Déplacement aléa 1
					 */

					boolean bool1 = true;
					while (bool1) {

						// Deplacement alétoire en diagonale

						if (v == 0)
							this.voiture.avancer(-1, -1);
						else if (v == 1)
							this.voiture.avancer(1, 1);
						else if (v == 2)
							this.voiture.avancer(-1, 1);
						else if (v == 3)
							this.voiture.avancer(1, -1);

						// dé que la voiture à un voisin non decouvert elle
						// reprend le comportement intelligent
						// On sort aussi, si on arrive au frontères ou un des
						// voisin est
						// est occupé par un autre véhicule
						ArrayList<Case> voisins2 = this.voiture.getVoisinage();
						for (int j = 0; j < voisins2.size(); j++) {
							if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
									|| voisins2.get(j).isOccupee()) {
								bool1 = false;
								break;

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
	 * Version 3 : se deviser la carte par les 4 vehicule, chaqu'un s'occupe
	 * dune partie, les vehicules vont explorer toute la carte dans un temps
	 * record Nb: cette verssion s'adapte a toute les les carte et fonctionne
	 * avec 4 vehicule placé au milieu !
	 * 
	 * @throws InterruptedException
	 */
	public void sedeplacer3() throws InterruptedException {

		// rouge vert bleu jaune
		// agent1.start(); //(rouge)0
		// agent2.start(); // verte 1
		// agent3.start(); // bleu 2
		// agent4.start(); // jaune 3

		ArrayList<Case> voisins2 = this.voiture.getVoisinage();
		/**
		 * Récupération de la case courante du vehicule
		 */
		Case case1 = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());

		/**
		 * Agent 1 s'occupe de la voiture 0 et parcours sa partie de la carte 24
		 * : c'est le nombre des voisinage si on est pas au frontières
		 */
		if (this.voiture.getID() == 0) {
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

		/**
		 * Agent 2 s'occupe de la voiture 1 et parcours sa partie de la carte
		 */
		if (this.voiture.getID() == 1) {
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
		/**
		 * Agent 3 s'occupe de la voiture 2 et parcours sa partie de la carte
		 */
		if (this.voiture.getID() == 2) {
			while (true) {
				while (true) {
					this.voiture.avancer(-1, 0);
					sleep(1);
					voisins2 = this.voiture.getVoisinage();
					System.out.println(this.voiture.getYCourant());
					if (voisins2.size() < 24 && this.voiture.getYCourant() != case1.getY() * 2 - 1)
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

		/**
		 * Agent 4 s'occupe de la voiture 3 et parcours sa partie de la carte
		 */
		if (this.voiture.getID() == 3) {

			while (true) {
				while (true) {
					this.voiture.avancer(1, 0);
					sleep(1);
					System.out.println("cc");
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != case1.getY() * 2 - 1)
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

	}

	/**
	 * 
	 * TEST****************************************************************************************
	 * 
	 */
	public void sedeplacer4() throws InterruptedException {

		// Récuperer la liste des voisins dans une ArrayList
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		Case voisin = null;
		ArrayList<Case> memoire = new ArrayList<>();
		Case case1;
		Case case2;
		Case case3;
		Case case4;
		Case caseM;

		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		int v = (int) (Math.random() * 4);
		boolean bool = true;

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
					caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
					voisinCOm.add(caseM);
				}

				// deplacement à gauche si voisin non decouvert est sur la
				// gauche
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);
					memoire.add(voisin);
					caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
					voisinCOm.add(caseM);

					// deplacement en bas si voisin non decouvert est en bas
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					memoire.add(voisin);
					caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
					voisinCOm.add(caseM);

					// deplacement en haut si voisin non decouvert est en haut
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
					memoire.add(voisin);
					caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
					voisinCOm.add(caseM);

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
					 * Deplacemment aléatoire (en diagonale) j'usqu'a attendre
					 * une zone ou ya une zone non decouverte ou frontière ou
					 * l'une des case voisine est occuper par un autre vehicule
					 * (eviter interblocage)
					 */
                  boolean boolc = true;
                  while (boolc== true && voisins.size()==24){
					case1 = new Case(this.voiture.getXCourant() + 1, this.voiture.getYCourant() + 1);
					System.out.println(case1);
					case2 = new Case(this.voiture.getXCourant() - 1, this.voiture.getYCourant() - 1);
					case3 = new Case(this.voiture.getXCourant() + 1, this.voiture.getYCourant() - 1);
					case4 = new Case(this.voiture.getXCourant() - 1, this.voiture.getYCourant() + 1);

					if (!voisinCOm.contains(case1)) {
						this.voiture.avancer(1, 1);
						caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
						voisinCOm.add(caseM);
						voisins = this.voiture.getVoisinage();
					} else if (!voisinCOm.contains(case2)) {
						this.voiture.avancer(-1, -1);
						caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
						voisinCOm.add(caseM);
						voisins = this.voiture.getVoisinage();
					} else if (!voisinCOm.contains(case3)) {
						this.voiture.avancer(+1, -1);
						caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
						voisinCOm.add(caseM);
						voisins = this.voiture.getVoisinage();
					} else if (!voisinCOm.contains(case4)) {
						this.voiture.avancer(-1, 1);
						caseM = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());
						voisinCOm.add(caseM);
						voisins = this.voiture.getVoisinage();
					}
					else {
						boolc = false;
				     	}
                  }
					
					boolean bool1 = true;
					while (bool1) {
						ArrayList<Case> voisins2 = this.voiture.getVoisinage();
						for (int j = 0; j < voisins2.size(); j++) {
							if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
									|| voisins2.get(j).isOccupee()) {
								bool1 = false;
								break;

							}
						}
					
							if (v == 0)
							    this.voiture.avancer(-1, -1);
							else if (v == 1)
								this.voiture.avancer(1, 1);
							else if (v == 2)
								this.voiture.avancer(-1, 1);
							else if (v == 3)
								this.voiture.avancer(1, -1);
						
						}

					bool = false;
				}
			}
		}
		// sleep(100);
	}

}
