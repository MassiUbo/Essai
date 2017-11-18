package sma;

import java.util.ArrayList;
import exploration.Case;
import exploration.Vehicule;

/**
 * Classe Agent, elle definit dedant le traitement que les agent font 
 * sur la carte et cela via les méthodes sedeplacer(), sedeplacer2(), sedeplacer3()
 * 
 * @author MASSI
 *
 */

public class Agent extends Thread {

	
	private Vehicule voiture; 
	
	// Constructure
	public Agent(Vehicule voiture) {
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
			sedeplacer();
			//sedeplacer2();
			//sedeplacer3();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Version 1 deplacement agent, 
	 * Déplacement à la fois alétoire et intelligent
	 * @throws InterruptedException
	 */
	public void  sedeplacer() throws InterruptedException {

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
						
						while (bool1){
						this.voiture.avancer(-1, -1);
						sleep(1);
						ArrayList<Case> voisins2 = this.voiture.getVoisinage();
						for (int j = 0; j<voisins2.size();j++)
						{
							if (voisins2.get(j).isDecouverte()== false||voisins2.size()<24)
							{
								bool1 = false;
								break;
								
							}
						}
						}
					}
					else if (v == 1) {
						boolean bool1 = true;
						while (bool1){
							this.voiture.avancer(1, 1);
							sleep(1);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j<voisins2.size();j++)
							{
								if (voisins2.get(j).isDecouverte()== false ||voisins2.size()<24 )
								{
									bool1 = false;
									break;
									
								}
							}
							}
					}
					else if (v == 2) {
						boolean bool1 = true;
						while (bool1){
							this.voiture.avancer(-1, 1);
							sleep(1);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j<voisins2.size();j++)
							{
								if (voisins2.get(j).isDecouverte()== false||voisins2.size()<24)
								{
									bool1 = false;
									break;
									
								}
							}
							}
					}
					else if (v == 3) {
						boolean bool1 = true;
						while (bool1){
							this.voiture.avancer(1, -1);
							sleep(1);
							ArrayList<Case> voisins2 = this.voiture.getVoisinage();
							for (int j = 0; j<voisins2.size();j++)
							{
								if (voisins2.get(j).isDecouverte()== false||voisins2.size()<24)
								{
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

		 sleep(1);
	}

	// Deplacement intelligent selon regle precise
	public void sedeplacer2() throws InterruptedException {

		// rouge vert bleu jaune
		// agent1.start(); //(rouge) 0
		// agent2.start(); // verte 1
		// agent3.start(); // bleu 2
		// agent4.start(); // jaune 3

		if (this.voiture.getID() == 0) {

			while (true) {
				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(-1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, -1);
				 sleep(1);
				this.voiture.avancer(0, -1);

				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, -1);
				 sleep(1);
				this.voiture.avancer(0, -1);

			}

		}

		if (this.voiture.getID() == 1) {

			while (true) {
				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(+1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, -1);
				 sleep(1);
				this.voiture.avancer(0, -1);

				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(-1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, -1);
				 sleep(1);
				this.voiture.avancer(0, -1);
			}

		}

		if (this.voiture.getID() == 2) {

			while (true) {
				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(-1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, +1);
				 sleep(1);
				this.voiture.avancer(0, +1);

				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(+1, 0);
					 sleep(1);
				}

				this.voiture.avancer(0, +1);
				 sleep(1);
				this.voiture.avancer(0, +1);
			}

		}

		if (this.voiture.getID() == 3) {

			while (true) {
				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(+1, 0);
					sleep(1);
				}

				this.voiture.avancer(0, +1);
				 sleep(1);
				this.voiture.avancer(0, +1);

				for (int i = 0; i < 23; i++) {
					this.voiture.avancer(-1, 0);
					sleep(1);
				}

				this.voiture.avancer(0, +1);
				sleep(1);
				this.voiture.avancer(0, +1);
			}

		}

		//sleep(1000);
	}

	public void sedeplacer3() throws InterruptedException {

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
				if (this.voiture.getID() == 0) {
                    	
					if (voisin.getX() - this.voiture.getXCourant() == 2) {
						if (this.voiture.getXCourant()+1<25)
						this.voiture.avancer(1, 0);
						else
						break;
					}

					else if (voisin.getX() - this.voiture.getXCourant() == -2) {
						this.voiture.avancer(-1, 0);
						

					} else if (voisin.getY() - this.voiture.getYCourant() == 2) {
						if (this.voiture.getYCourant()+1<25)
						this.voiture.avancer(0, 1);
						else 
						break;

					} else if (voisin.getY() - this.voiture.getYCourant() == -2) {
						this.voiture.avancer(0, -1);

					}
                    
				}
				
				/*if (this.voiture.getID() == 1) {

					
					// on teste voisinage a ordre 2
					if (voisin.getX()>24 && voisin.getY()<24) {  
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
					}
				}
				
				if (this.voiture.getID() == 2) {

					if (voisin.getX()<24 && voisin.getY()>24) {  
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
					}
				}
				
				if (this.voiture.getID() == 4) {

					if (voisin.getX()>24 && voisin.getY()>24) {  
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
					}
				}  */

			} else {
				i = (int) (Math.random() * voisins.size());
				voisin = voisins.get(i);
				memoire.add(voisin);
				if (memoire.size() > 24) {

					// DEPLACEMENT Aléatoire
					if (v == 0) {
						this.voiture.avancer(1, 0);
					}
					if (v == 1) {
						this.voiture.avancer(-1, 0);
					}
					if (v == 2) {
						this.voiture.avancer(0, 1);
					}
					if (v == 3) {
						this.voiture.avancer(0, -1);
					}
					bool = false;
				}
			}
		}

		 sleep(50);
	}

}
