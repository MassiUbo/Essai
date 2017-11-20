/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploration;

import ihm.Controleur;
import ihm.Visualiseur;
import sma.Agent;
import sma.Agent2;

import java.util.HashMap;

/**
 * Classe principale qui lance l'application. - Crée une nouvelle {@link Carte}
 * de taille 50*50 par défaut, avec 4 {@link Vehicule} - Lance le
 * {@link Controleur} qui s'occupe de mettre en place le lien entre l'IHM
 * ({@link Visualiseur}) et la carte - Initialise les 4 véhicules sur la carte
 *
 * @author riviere
 */
public class Exploration {

	private final Carte carte;
	private static boolean RANDOM_SIZE = false;
	private final int VEHICULE_NBR = 4;

	private Agent2 agent1;
	private Agent2 agent2;
	private Agent2 agent3;
	private Agent2 agent4;

	/**
	 *
	 * @param xSize
	 *            la taille en x de la carte
	 * @param ySize
	 *            la taille en y de la carte
	 */
	public Exploration(int xSize, int ySize) {

		carte = new Carte(xSize, ySize);

		new Controleur(carte);
		carte.initVehicules(VEHICULE_NBR);
		Vehicule v1 = getVehicules().get(0);
		Vehicule v2 = getVehicules().get(1);
		Vehicule v3 = getVehicules().get(2);
		Vehicule v4 = getVehicules().get(3);

		agent1 = new Agent2(v1);
		agent2 = new Agent2(v2);
		agent3 = new Agent2(v3);
		agent4 = new Agent2(v4);

		agent1.start(); // (rouge)
		agent2.start(); // verte
		agent3.start(); // bleu
		agent4.start(); // jaune
	}

	/**
	 * Récupérer la liste des véhicules présents sur la carte
	 *
	 * @return une hashMap contenant la liste des véhicules classée par
	 *         identifiants (pour 4 véhicules : 0 1 2 3)
	 */
	public HashMap<Integer, Vehicule> getVehicules() {
		return carte.getVehicules();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		int i = 0;
		while (i < args.length) {
			switch (args[i]) {
			case "-random":
				RANDOM_SIZE = true;
				i++;
				break;
			}
		}

		int xsize;
		int ysize;

		if (RANDOM_SIZE) {
			xsize = new Double(Math.random() * 100 + 50).intValue();
			ysize = new Double(Math.random() * 50 + 50).intValue();
		} else {
			xsize = 50;
			ysize = 50;
		}

		new Exploration(xsize, ysize);
	}

}
