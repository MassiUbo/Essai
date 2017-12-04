package sma;

import java.util.ArrayList;
import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit le comportement que les agents vont éffectuer sur
 * la carte et cela via les méthodes 
 * reactifV1() : (Agents réactifs version 1) 
 * reactifV2() : (Agents réactifs version 2)
 * cognitif() : (Agents congnétifs) 
 * partageCarte() : (Algorithme globale + Agents réactifs)
 * communication(): (Agents cognitifs + communication)
 * 
 * @author MASSINISSA HADJ-SAID
 *
 */

public class Agent extends Thread {

	private Vehicule voiture;
	// cpt ,vc et MailBox utilisées dans communication ()
	private static int cpt =0;
	private static int vc=0;
	private MailBox mailb = new MailBox();

	/**
	 * Constructeur avec paramètre
	 * @param voiture passé comme paramètre à l'agent
	 * 
	 */
	public Agent(Vehicule voiture) throws InterruptedException {
		this.voiture = voiture;
	}

	/**
	 * Getter pour attribut voiture 
	 * @return voiture
	 */
	
	public Vehicule getVoiture() {
		return voiture;
	}

	/**
	 * Lancement des agents en choisisantt une des méthodes de
	 * comportement, reactifV1(), reactifV2(), cognitif(), partageCarte(),
	 * communication()
	 */

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// reactifV1();        //SMA AGENTS REACTIFS V2
				// reactifV2();        //SMA AGENTS REACTIFS V2
			 	 cognitif();           //SMA AGENTS COGNITIFs
				//partageCarte();      //SMA (ALGORITHME GLOBALE + REACTIFS)
				// communication();    //SMA AGENTS COGNITIFS + COMMUNICATION
			    }   
			    catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	 /* ******************************
	 * VERSION 1 Agents Réactifs
	 ****************************************/

	/**
	 * Version 1 réactifs : 
	 * + les agent sont réactifs doté d'un peu d'intelligence
	 * + Un agent tire un des ces voisin des 4 coté : haut , bas , droit , gauche
	 * + Si le coté ou se situe ce voisin est pas encore découvrt il va l'explorer
	 * + Si se voisin est dejas decouvert , l'agent sedeplace d'une manière aléatoire
	 * 
	 */

	public void reactifV1() throws InterruptedException {

		System.out.println("VERSION 1 Agents Réactifs running ...");

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
		sleep(10);  // pour voir comportement d'agents sur la carte
	}

	/**
	 * Version 2 réactifs : +Deplacement intélligent ( haut, bas , gauche, droite )
	 * + case memoire pour sauvgarder voisins 
	 * + Si le voisin tiré est pas découvert, l'agent déplace le véhicule du coté de de voisin
	 * + Si le voisin tiré est découvert l'agent l'enregistre dans sa mémoire et tire un autre
	 * + Il repete ce processus jusqua avoir traité tous ces voisins
	 * + Si tous voisins traité : deplacement aléatoire
	 * 
	 */

	/* ******************************
	 * VERSION 2 Agents Réactifs V2
	 ****************************************/

	public void reactifV2() throws InterruptedException {

		System.out.println("VERSION 2 Agents Réactifs running ...");
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

				/*
				 * Si non on ajoute le voisin à la mémoire et on tire un autre
				 */

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

	/* ******************************
	 * VERSION 3 Agents Cognitifs
	 *********************/
	/**
	 * Version congitif : 
	 * + Deplacement intelligent (pareil que version 2 réactifs) 
	 * + Deplacement alétoire continue j'usqua atteindre
	 *  une zone ou ya des cases voisines non découverte et reprendre le comportement
	 *  intelligent 
	 *  /  ou attendre bordure de la carte 
	 *  /  ou : se trouver dans une zone ou il ya un voison occupé 
	 *  (pas de risque de deplacement aléatoire dans ce cas)
	 * + Déviser la carte en deux partie, une partie pour les véhicules dont l'ID est impaire
	 *  et l'autre partie est pour le véhicule dont l'ID est paire
	 * 
	 * 
	 */

	public void cognitif() throws InterruptedException {
		System.out.println("VERSION 3 Agents Cognitifs running ...");
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

				/*
				 * parcours ordonné en commancant par voisin 1 au dernier voisin
				 */
				if (voiture.getID() %2 == 0) {
					for (i = 0; i < voisins.size(); i++) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
					/*
					 * parcours ordonné en commancant par dernier voisin au
					 * voisin 1
					 */
				} else if (voiture.getID() %2 == 1) {
					for (i = voisins.size() - 1; i >= 0; i--) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
				}
				memoire.add(voisin);

				/* *
				 * si tous les voisins sont tester, on fait un deplacement
				 * aléatoire continu j'usqua trouver un voisin non decouvert,
				 * frontière ou zone occuper par un autre agent pour eviter
				 * blocage
				 */

				if (memoire.size() == voisins.size()) {

					boolean bool1 = true;

					while (bool1) {

						/* *
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
					//sleep(10);
				}
			}
		}
	    sleep(10);
	}

	/* ******************************
	 * VERSION 4 Algorithme Globale (partage de la carte pour résoudre le problème)
	 ****************************************/

	/**
	 * Version PartageCarte : 
	 * + Se deviser la carte par les 4 vehicule, chaqu'un s'occupe dune partie, 
	 * + Les vehicules vont explorer toute la carte dans un temps record 
	 * + Nb: cette verssion s'adapte a toute les les carte et fonctionne avec 4 vehicule placé au milieu
	 * + La carte sera dévisé en 4, chaque vehicule explore une des 4 parties
	 * 
	 * 
	 */

	public void partageCarte() throws InterruptedException {
		System.out.println("VERSION 4 partage de carte running ...");
		ArrayList<Case> voisins2 = this.voiture.getVoisinage();
		
		//  Récupération de la case courante du vehicule
		 
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
	 * Version communicative :
	 * + Reprise de la version cognitif  + communication
	 * + Communication dans le cas ou tous les agents se trouve dans une case avec tous le voisinage découvert
	 * + Le but et de communiquer de d'éviter de se deplacer dans des zones similaires
	 * 
	 * + Communication via : MailBox / BlockingQueue
	 */

	public void communication() throws InterruptedException {
	
		System.out.println("VERSION 5 Agents Cognitifs + communication runing ...");
		// Ajout ID voiture à la mailBox
		mailb.add(this.voiture.getID());
		/* Même règles que le version 2 réactif */
		ArrayList<Case> voisins = this.voiture.getVoisinage();
		Case voisin = null;
		ArrayList<Case> memoire = new ArrayList<>();
		int i = (int) (Math.random() * voisins.size());
		voisin = voisins.get(i);
		
		int s =  mailb.getLastMessage(); 
		
		if (cpt == 0)
		vc = (int) (Math.random() * 4);

		// deplacement differents des agents chaque  fois qu'on traite les 4 agents
		cpt++;
		if (cpt==4)
			cpt =0;
		
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
				/* se déviser la carte */

				/*
				 * parcours ordonné en commancant par voisin 1 au dernier voisin
				 */
				if (voiture.getID() %2 == 0) {
					for (i = 0; i < voisins.size(); i++) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
					/*
					 * parcours ordonné en commancant par dernier voisin au
					 * voisin 1
					 */
				} else if (voiture.getID() %2 == 1) {
					for (i = voisins.size() - 1; i >= 0; i--) {
						if (!memoire.contains(voisins.get(i))) {
							voisin = voisins.get(i);
							break;
						}

					}
				}
				memoire.add(voisin);

				/*
				 * si tous les voisins sont tester, on fait un deplacement
				 * aléatoire continu j'usqua trouver un voisin non decouvert,
				 * frontière ou zone occuper par un autre agent pour eviter
				 * blocage
				 */

				if (memoire.size() == voisins.size()) {

					boolean bool1 = true;

					while (bool1) {

						/*
						 * Deplacement alétoire continu Le véhicule peut se
						 * deplacer dans toute les positions
						 * 
						 * les agents se deplace vers des zones differentes
				 		 **/
              
					if (s==0)	
					{
                     if (vc == 0)
							this.voiture.avancer(1, 1);
						else if (vc == 1)
							this.voiture.avancer(-1, -1);
						else if (vc == 2)
							this.voiture.avancer(-1, 1);
						else if (vc == 3)
							this.voiture.avancer(1, -1);
					}
					if (s==1)	
					{
                     if (vc == 0)
							this.voiture.avancer(-1, -1);
						else if (vc == 1)
							this.voiture.avancer(-1, 1);
						else if (vc == 2)
							this.voiture.avancer(1, -1);
						else if (vc == 3)
							this.voiture.avancer(1, 1);
					}
					if (s==2)	
					{
                     if (vc == 0)
							this.voiture.avancer(-1, 1);
						else if (vc == 1)
							this.voiture.avancer(1, -1);
						else if (vc == 2)
							this.voiture.avancer(1, 1);
						else if (vc == 3)
							this.voiture.avancer(-1, -1);
					}
					if (s==3)	
					{
                     if (vc == 0)
							this.voiture.avancer(1, -1);
						else if (vc == 1)
							this.voiture.avancer(1, 1);
						else if (vc == 2)
							this.voiture.avancer(-1, -1);
						else if (vc == 3)
							this.voiture.avancer(-1, 1);
					}
					
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
		

	}

}
