/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploration;

/**
 * Composant de la {@link Carte}, a des coordonnées (x,y) dans une matrice.
 *
 * @author riviere
 */
public class Case {

    // Peut être occupée par un véhicule

    private boolean occupee = false;
    private int idVehicule;

    // Découverte ou encore à explorer
    private boolean decouverte = false;

    // Le nombre de fois où la case a été occupée par un véhicule
    private int nbOccupee = 0;

    // Ses coordonnées dans la matrice
    private final int x;
    private final int y;

    /**
     * Le constructeur prend en paramètres les coordonnées de la case dans la
     * matrice de la classe {@link Carte}
     *
     * @param i abscisse de la case dans la matrice
     * @param j ordonnée de la case dans la matrice
     */
    public Case(int i, int j) {
        idVehicule = -1;
        x = i;
        y = j;
    }

    public synchronized boolean isOccupee() {
        return occupee;
    }

    public synchronized boolean isDecouverte() {
        return decouverte;
    }

    public synchronized void decouvrir() {
        decouverte = true;
    }

    public synchronized void occuper(int id) {
        occupee = true;
        idVehicule = id;
        nbOccupee++;
    }

    public synchronized void quitter() {
        idVehicule = -1;
        occupee = false;
    }

    public synchronized int getVehicule() {
        return idVehicule;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNbOccupee() {
        return nbOccupee;
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ") : " + decouverte + "-" + occupee + ":" + idVehicule;
    }

}
