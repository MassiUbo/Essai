/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploration;

import java.util.ArrayList;

/**
 * Véhicule qui explore la {@link Carte} en se déplaçant et en découvrant les
 * {@link Case}
 *
 * @author riviere
 */
public class Vehicule {

    private final int id;
    private int xCourant;
    private int yCourant;
    private final Carte carte;

    // Détermine l'ordre du voisinage
    private final int vision = 2;

    /**
     *
     * @param carte la {@link Carte} sur laquelle est positionné le véhicule
     * @param id son identifiant
     * @param x l'abscisse de la {@link Case} qu'il occupe sur la carte
     * @param y l'ordonnée de la {@link Case} qu'il occupe sur la carte
     */
    public Vehicule(Carte carte, int id, int x, int y) {
        this.id = id;
        xCourant = x;
        yCourant = y;
        this.carte = carte;
    }

    public int getID() {
        return id;
    }

    public int getXCourant() {
        return xCourant;
    }

    public int getYCourant() {
        return yCourant;
    }

    public void setxCourant(int xCourant) {
        this.xCourant = xCourant;
    }

    public void setyCourant(int yCourant) {
        this.yCourant = yCourant;
    }

    /**    // Acancement contraire de ballet
     * Méthode à appeler pour faire avancer le véhicule vers une {@link Case}
     * voisine (à 1) dans une des directions suivantes
     * (-1,-1),(-1,0),(-1,1),(0,-1),(0,1),(1,-1),(1,0),(1,1). Fait appel à la
     * méthode {@link Carte#avancer(int, int, int)}.
     *
     * @param xDirection direction selon l'axe des x (-1,0,1)
     * @param yDirection direction selon l'axe des y (-1,0,1)
     */
    public void avancer(int xDirection, int yDirection) {
        carte.avancer(id, xDirection, yDirection);
    }

    /**
     * Retourne la liste des {@link Case} dans le voisinage du véhicule. Le
     * voisinage du véhicule est défini par l'attribut {@link #vision}. Fait
     * appel à la méthode {@link Carte#getVoisinage(int, int, int)}.
     *
     * @return la liste des {@link Case} dans le voisinage du véhicule
     */
    public ArrayList<Case> getVoisinage() {
        return carte.getVoisinage(xCourant, yCourant, vision);
    }

}
