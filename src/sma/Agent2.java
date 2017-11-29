package sma;

import java.util.ArrayList;
import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit le comportement que les agents vont éffectuer sur
 * la carte et cela via les méthodes sedeplacer1(), sedeplacer2(), sedeplacer3()
 * sedeplcaer4();
 * 
 * @author MASSINISSA
 *
 */

public class Agent2 extends Thread {

	private Vehicule voiture;
	private MailBox mailb = new MailBox();

	public Agent2(Vehicule voiture) throws InterruptedException {
		this.voiture = voiture;

		if (this.voiture.getID() == 0)
			this.mailb.add(0);
		if (this.voiture.getID() == 1)
			this.mailb.add(1);
		if (this.voiture.getID() == 2)
			this.mailb.add(2);
		if (this.voiture.getID() == 3)
			this.mailb.add(3);
	}

	// getter
	public Vehicule getVoiture() {
		return voiture;
	}

	/**
	 * Méthode run() , lancement des agents On choisit une dé méthode de
	 * comportement, sedeplacer1(), sedeplacer2(), sedeplacer3(), sedeplacer4()
	 */

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// sedeplacer0();
				// sedeplacer1();
				// sedeplacer2();
				sedeplacer3();
				// sedeplacer5();
				// sedeplacer4();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*******************************
	 * VERSION 1 Agent Réactifs
	 ****************************************/

	/**
	 * Dans cette version, les agent sont réactifs doté d'un peu d'intelligence
	 * Un agent tire un des ces voisin des 4 coté : haut , bas , droit , gauche
	 * si le coté ou se situe ce voisin est pas encore découvrt il va l'explorer
	 * 
	 * si se voisin est dejas decouvrt , l'agent réagis d'une manière aléatoire
	 * 
	 * @throws InterruptedException
	 */

	public void sedeplacer0() throws InterruptedException {
		
		System.out.println("VERSION 1 Agents Réactifs runing ...");

		/* voisins : Liste de voisins d'un Véhicule */
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		Case voisin = null;

		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		/* Tirage nombre aléatoire pour designer un comportement aléatoire */
		int v = (int) (Math.random() * 4);

		/* On limite les voisins au voisins visibles à deux */
		if (!voisin.isOccupee() && !voisin.isDecouverte()) {

			/* Deplacement à droite si voisin tiré est de coté droit */
			if (voisin.getX() - this.voiture.getXCourant() == 2) {
				this.voiture.avancer(1, 0);
			}
			/* Deplacement à gauche si voisin tiré est sur la gauche */
			else if (voisin.getX() - this.voiture.getXCourant() == -2) {
				this.voiture.avancer(-1, 0);

				/* Deplacement en bas si voisin non decouvert est en bas */
			} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
				this.voiture.avancer(0, 1);
				/* deplacement en haut si voisin non decouvert est en haut */
			} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
				this.voiture.avancer(0, -1);
			}

		} else {

			/*
			 * Si tous les voisins sont traité et qu'il sont tous decouvert,
			 * deplacement alétoire (ici deplacement en diagonale)
			 */

			if (v == 1)
				this.voiture.avancer(1, 1);
			else if (v == 2)
				this.voiture.avancer(1, -1);
			else if (v == 3)
				this.voiture.avancer(-1, 1);
			else if (v == 0)
				this.voiture.avancer(-1, -1);

		}
		 sleep(10);
	}

	/**
	 * Version 2 réactifs deplacement intélligent ( haut, bas , gauche, droite )
	 * + case memoire pour sauvgarder voisins Si le voisin tiré est pas
	 * découvert, l'agent déplace le véhicule du coté du voisin 7 Si le voisin
	 * tiré est découvert l'agent l'enregistre dans sa mémoire et tire un autre
	 * il repete ce processus jusqua savoir traiter tous ces voisins
	 * 
	 * si tous voisins traité : deplacement aléatoire
	 * 
	 */

	/*******************************
	 * VERSION 2 Agents Réactifs V2
	 ****************************************/

	public void sedeplacer1() throws InterruptedException {

		System.out.println("VERSION 2 Agents Réactifs runing ...");
		/* voisins : Liste de voisins d'un Véhicule */
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		Case voisin = null;

		/* Mémoire propre à lagent pour sauvgarder les voisins dejas traités */
		ArrayList<Case> memoire = new ArrayList<>();

		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);

		boolean bool = true;
		/* Tirage nombre aléatoire pour designer un comportement aléatoire */
		int v = (int) (Math.random() * 4);

		while (bool) {
			/* On limite les voisins au voisins visibles à deux */
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {

				/* Des qu'il ya un deplacement on sort de la boucle */
				bool = false;

				/* Deplacement à droite si voisin tiré est de coté droit */
				if (voisin.getX() - this.voiture.getXCourant() == 2) {
					this.voiture.avancer(1, 0);
				}
				/* deplacement à gauche si voisin tiré est sur la gauche */
				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);

					/* deplacement en bas si voisin non decouvert est en bas */
				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);
					/*
					 * deplacement en haut si voisin non decouvert est en haut
					 */
				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);
				}

			} else {

				/* Si non on ajoute le voisin à la mémoire et on tire un autre */

				while (memoire.contains(voisin)) {
					i = (int) (Math.random() * voisins.size());
					voisin = voisins.get(i);
				}

				memoire.add(voisin);

				/*
				 * Si tous les voisins sont traité et qu'il sont tous decouvert,
				 * deplacement alétoire (ici deplacement en diagonale)
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

					/* on sort de la boucle après deplacement */
					bool = false;
				}
			}

		}
		 sleep(10);
	}

	/*******************************
	 * VERSION 3 Agents Cognitifs
	 ****************************************/
	/**
	 * Méthode sedeplacer2(), version congitif, deplacement intelligent (pareil
	 * que version 2 , puis) deplacement alétoire continue si tous les voisins
	 * sont découvert j'usqua atteindre une zone ou ya des cases voisines non
	 * découverte et reprendre le comportement intelligent /ou  attendre bordure
	 * de la carte /ou : se trouver dans une zone ou il ya un voison ocuupé (pas
	 * de risque de deplacement aléatoire dans ce cas)
	 * 
	 * + Déviser la carte en deux partie, une partie pour les véhicules 1 et 2
	 * L'autre partie est pour le véhicule 3 et 4
	 *  
	 * 
	 * @throws InterruptedException
	 * 
	 */
	
	public void sedeplacer2() throws InterruptedException {
		System.out.println("VERSION 3 Agents Cognitifs runing ...");
		/* Même règles que le version 2 réactif */
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		Case voisin = null;
		ArrayList<Case> memoire = new ArrayList<>();
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);
		int v = (int) (Math.random() * 8);
		boolean bool = true;

		while (bool) {
			if (!voisin.isOccupee() && !voisin.isDecouverte()) {
				bool = false;

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
		       /* se déviser la carte en deux partie */
				
				/* parcours ordonné en commancant par voisin 1 au dernier voisin */
				if (voiture.getID() == 0 || voiture.getID() == 1) {
					for (i = 0; i < voisins.size(); i++) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
					/* parcours ordonné en commancant par dernier voisin au voisin 1 */	
				} else if (voiture.getID() == 2 || voiture.getID() == 3) {
					for (i = voisins.size() - 1; i >= 0; i--) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
				}
				memoire.add(voisin);

				/**
				 * si tous les voisins sont tester, on fait un deplacement
				 * aléatoire continu j'usqua trouver un voisin non decouvert,
				 * frontière ou zone occuper par un autre agent pour eviter
				 * blocage
				 */

				if (memoire.size() == voisins.size()) {

					boolean bool1 = true;

					while (bool1) {

						/**
						 * Deplacement alétoire continu Le véhicule peut se
						 * deplacer dans toute les positions
						 **/

						if (v == 0)
							this.voiture.avancer(1, 1);
						else if (v == 1)
							this.voiture.avancer(-1, -1);
						else if (v == 2)
							this.voiture.avancer(-1, 1);
						else if (v == 3)
							this.voiture.avancer(1, -1);
						else if (v == 4)
							this.voiture.avancer(0, -1);
						else if (v == 5)
							this.voiture.avancer(0, 1);
						else if (v == 6)
							this.voiture.avancer(1, 0);
						else if (v == 7)
							this.voiture.avancer(-1, 0);

						/*
						 * dès que la voiture à un voisin non Decouvert elle
						 * reprend le comportement intelligent, Aussi, on sort
						 * du comportement continue si: * on arrive au frontères
						 * de la carte * ou un des voisin est est occupé par un
						 * autre véhicule (pour éviter blockage)
						 */

						/* Récupreation de la liste des voisins du véhicule */
						ArrayList<Case> voisins2 = this.voiture.getVoisinage();
						for (int j = 0; j < voisins2.size(); j++) {
							if (voisins2.get(j).isDecouverte() == false || voisins2.size() < 24
									|| voisins2.get(j).isOccupee()) {

								bool1 = false;
								break;
							}
						}
					}

					/*
					 * fin du comportemant aléatoire continu et reprise du
					 * comportement intelligent
					 */
					bool = false;
				}
			}
		}
		sleep(10);
	}

	/*******************************
	 * VERSION 4 cooperative (partage de la carte pour résoudre le problème)
	 ****************************************/

	/**
	 * Version 4 : se deviser la carte par les 4 vehicule, chaqu'un s'occupe
	 * dune partie, les vehicules vont explorer toute la carte dans un temps record 
	 * Nb: cette verssion s'adapte a toute les les carte et fonctionne
	 * avec 4 vehicule placé au milieu
	 * 
	 * La carte sera devier en 4 chaque vehicule explore une des 4 partie
	 * 
	 * @throws InterruptedException
	 */

	public void sedeplacer3() throws InterruptedException {
		System.out.println("VERSION 4 Agents Coopératifs runing ...");
		ArrayList<Case> voisins2 = this.voiture.getVoisinage();
		/*
		 * Récupération de la case courante du vehicule
		 */
		Case case1 = new Case(this.voiture.getXCourant(), this.voiture.getYCourant());

		/*
		 * Agent 1 s'occupe de la voiture 0 et parcours sa partie de la carte
		 * 24: c'est le nombre des voisinage si on est pas au frontières
		 */

		if (this.voiture.getID() == 0) {
			while (true) {
				while (true) {
					this.voiture.avancer(-1, 0);
					// sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != 0)
						break;
				}

				this.voiture.avancer(0, -1);
				// sleep(1);
				this.voiture.avancer(0, -1);

				while (true) {
					this.voiture.avancer(1, 0);
					// sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, -1);
				// sleep(1);
				this.voiture.avancer(0, -1);

			}

		}

		/*
		 * Agent 2 s'occupe de la voiture 1 et parcours sa partie de la carte
		 */

		if (this.voiture.getID() == 1) {
			while (true) {
				while (true) {
					this.voiture.avancer(+1, 0);
					// sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != 0)
						break;
				}

				this.voiture.avancer(0, -1);
				// sleep(1);
				this.voiture.avancer(0, -1);

				while (true) {
					this.voiture.avancer(-1, 0);
					// sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, -1);
				// sleep(1);
				this.voiture.avancer(0, -1);

			}

		}

		/*
		 * Agent 3 s'occupe de la voiture 2 et parcours sa partie de la carte
		 */

		if (this.voiture.getID() == 2) {
			while (true) {
				while (true) {
					this.voiture.avancer(-1, 0);
					// sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != case1.getY() * 2 - 1)
						break;
				}

				this.voiture.avancer(0, +1);
				// sleep(1);
				this.voiture.avancer(0, +1);

				while (true) {
					this.voiture.avancer(1, 0);
					// sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}

				this.voiture.avancer(0, +1);
				// sleep(1);
				this.voiture.avancer(0, +1);
			}
		}

		/*
		 * Agent 4 s'occupe de la voiture 3 et parcours sa partie de la carte
		 */

		if (this.voiture.getID() == 3) {
			while (true) {
				while (true) {
					this.voiture.avancer(1, 0);
					// sleep(1);
					voisins2 = this.voiture.getVoisinage();
					if (voisins2.size() < 24 && this.voiture.getYCourant() != case1.getY() * 2 - 1)
						break;
				}

				this.voiture.avancer(0, 1);
				// sleep(1);
				this.voiture.avancer(0, 1);

				while (true) {
					this.voiture.avancer(-1, 0);
					// sleep(1);
					if (this.voiture.getXCourant() == case1.getX())
						break;
				}
				this.voiture.avancer(0, 1);
				this.voiture.avancer(0, 1);
				// sleep(1);

			}

		}

	}

	/**
	 * 
	 * TEST****************************************************************************************
	 * 
	 */

	public void sedeplacer5() throws InterruptedException {

		// Récuperer la liste des voisins dans une ArrayList
		ArrayList<Case> voisins = this.voiture.getVoisinage();
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

		int x = (int) (Math.random() * 4);

		int v = this.mailb.getLastMessage();

		if (x == 0) {
			if (v == 0)
				this.mailb.add(1);
			else if (v == 1)
				this.mailb.add(2);
			else if (v == 2)
				this.mailb.add(3);
			else if (v == 3)
				this.mailb.add(0);
		}
		if (x == 1) {
			if (v == 0)
				this.mailb.add(2);
			else if (v == 1)
				this.mailb.add(3);
			else if (v == 2)
				this.mailb.add(0);
			else if (v == 3)
				this.mailb.add(1);
		}
		if (x == 2) {
			if (v == 0)
				this.mailb.add(3);
			else if (v == 1)
				this.mailb.add(1);
			else if (v == 2)
				this.mailb.add(2);
			else if (v == 3)
				this.mailb.add(3);
		}
		if (x == 3) {
			if (v == 0)
				this.mailb.add(0);
			else if (v == 1)
				this.mailb.add(1);
			else if (v == 2)
				this.mailb.add(2);
			else if (v == 3)
				this.mailb.add(3);
		}
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

				}

				else if (voisin.getX() - this.voiture.getXCourant() == -2) {
					this.voiture.avancer(-1, 0);

				} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
					this.voiture.avancer(0, 1);

				} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
					this.voiture.avancer(0, -1);

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
						v = this.mailb.getLastMessage();
						this.mailb.add(v);
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

		sleep(1);
	}

}
