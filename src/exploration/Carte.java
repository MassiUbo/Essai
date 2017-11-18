/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exploration;

import ihm.CarteListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.EventListenerList;

/**
 * Environnement dans lequel seront placés les {@link Vehicule}. La Carte
 * contient une matrice de {@link Case} de taille X*Y, au départ inconnues, que
 * les véhicules peuvent découvrir ({@link #decouvrir(int, int)}) dans leur
 * voisinage et occuper ({@link #occuper(int, int, int)}) en se déplaçant.
 *
 * @author riviere
 */
public class Carte {

    private Case cases[][];
    //une hashMap contenant la liste des véhicules classée par identifiants (e.g. pour 4 véhicules : 0 1 2 3)
    private final HashMap<Integer, Vehicule> vehicules;
    private final int X;
    private final int Y;
    private int cptDecouverte = 0;

    // Liste des classes implémentant l'interface {@link CarteListener} à l'écoute de la Carte
    private final EventListenerList listeners;

    /**
     * Initialise la liste des véhicules et construit la matrice de {@link Case}
     *
     * @param x la largeur réelle de la Carte
     * @param y la hauteur réelle de la Carte
     */
    public Carte(int x, int y) {
        this.X = x;
        this.Y = y;
        vehicules = new HashMap<>();
        initCases();
        listeners = new EventListenerList();
    }

    /**
     * Construction de la matrice de {@link Case}
     */
    private void initCases() {
        cases = new Case[X][Y];
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                cases[i][j] = new Case(i, j);
            }
        }
    }

    /**
     * Initialise et place nbr {@link Vehicule} sur les {@link Case} au centre
     * de la Carte
     *
     * @param nbr le nombre de véhicules à créer
     */
    public void initVehicules(int nbr) {
        int xDep;
        int yDep;
        if ((X - 1) % 2 == 0) {
            xDep = X / 2;
        } else {
            xDep = (X - 1) / 2;
        }
        if ((Y - 1) % 2 == 0) {
            yDep = Y / 2;
        } else {
            yDep = (Y - 1) / 2;
        }

        int xv = xDep;
        int yv = yDep;

        for (int i = 0; i < nbr; i++) {
            Vehicule v = new Vehicule(this, i, xv, yv);
            placer(v, xv, yv);
            vehicules.put(i, v);

            if (i == (nbr - 1) / 2) {
                xv = xDep;
                yv = yv + 1;
            } else {
                xv = xv + 1;
            }
        }
        updateAll();
    }

    /**
     * place le {@link Vehicule} v sur la {@link Case} aux coordonnées (xv,yv)
     *
     * @param v le Véhicule à placer
     * @param xv abscisse de la Case
     * @param yv ordonnée de la Case
     */
    private synchronized void placer(Vehicule v, int xv, int yv) {
        occuper(v.getID(), xv, yv);
        v.setxCourant(xv);
        v.setyCourant(yv);
        ArrayList<Case> voisins = getVoisinage(xv, yv, 1);
        for (Case c : voisins) {
            if (!c.isDecouverte()) {
                decouvrir(c.getX(), c.getY());
            }
        }
    }

    /**
     * Action d'occuper une {@link Case} par un {@link Vehicule}. Découvre la
     * Case si elle n'a pas été découverte avant.
     *
     * @param id identifiant du véhicule
     * @param xc abscisse de la Case
     * @param yc ordonnée de la Case
     */
    private synchronized void occuper(int id, int xc, int yc) {
        Case c = cases[xc][yc];
        c.occuper(id);
        if (!c.isDecouverte()) {
            decouvrir(xc, yc);
        }
    }

    /**
     * Renvoit la liste des {@link Case} qui font partie du voisinage du point
     * (xc,yc), à ordre (1,2 ...) de distance. Par exemple, retourne toutes les
     * cases de coordonnées (xc +- ordre, yc +- ordre).
     *
     * @param xc abscisse du centre
     * @param yc ordonnée du centre
     * @param ordre le rayon du voisinage
     * @return la liste des {@link Case} du voisinage de (xc,yc)
     */
    public synchronized ArrayList<Case> getVoisinage(int xc, int yc, int ordre) {   // ordre c'est toujours 2 
        ArrayList<Case> voisins = new ArrayList<>();
        for (int i = -ordre; i <= ordre; i++) {
            for (int j = -ordre; j <= ordre; j++) {
                if (((i != 0) || (j != 0)) && dansLesLimites(xc + i, yc + j)) {
                    voisins.add(cases[xc + i][yc + j]);
                }
            }
        }
        return voisins;
    }

    /**
     * Fonctionne de la même manière que la méthode {@link #getVoisinage(int, int, int)
     * }, mais retourne une matrice de {@link Case}, rendant ainsi l'accès aux
     * cases plus rapide.
     *
     * @param xc abscisse du centre
     * @param yc ordonnée du centre
     * @param ordre le rayon du voisinage
     * @return la matrice des {@link Case} du voisinage de (xc,yc)
     */
    private synchronized Case[][] getQuickVoisinage(int xc, int yc, int ordre) {
        Case[][] voisins = new Case[2 * ordre + 1][2 * ordre + 1];

        for (int i = -ordre; i <= ordre; i++) {
            for (int j = -ordre; j <= ordre; j++) {
                if (dansLesLimites(xc + i, yc + j)) {
                    voisins[i + 1][j + 1] = cases[xc + i][yc + j];
                }
            }
        }
        return voisins;
    }

    /**
     * Teste si le point de coordonnées (a,b) est dans les limites de la carte,
     * soit >0 et infèrieure à sa taille (X,Y)
     *
     * @param a
     * @param b
     * @return vrai si le point de coordonnées (a,b) est dans les limites de la
     * carte
     */
    private boolean dansLesLimites(int a, int b) {
        return ((a >= 0) && (b >= 0) && (a < X) && (b < Y));  // pour ne pas heurter les murs
    }

    /**
     * Passe l'attribut {@link Case#decouverte} à vrai
     *
     * @param xc la position en x de la {@link Case} dans la matrice
     * @param yc la position en y de la {@link Case} dans la matrice
     */
    private synchronized void decouvrir(int xc, int yc) {
        cases[xc][yc].decouvrir();
        cptDecouverte++;
        if (cptDecouverte >= X * Y) {
            for (final CarteListener listen : listeners.getListeners(CarteListener.class)) {
                listen.success(calculEfficacite());
            }
        }
    }

    /**
     * Méthode utilisée par un {@link Vehicule} pour avancer d'une {@link Case}
     * sur la Carte
     *
     * @param idVehicule identifiant du véhicule
     * @param xDirection direction selon l'axe des x (-1,0,1)
     * @param yDirection direction selon l'axe des y (-1,0,1)
     */
    public synchronized void avancer(int idVehicule, int xDirection, int yDirection) {
        Vehicule v = vehicules.get(idVehicule);
        if (v != null) {
            int xv = v.getXCourant();
            int yv = v.getYCourant();

            int newX = xv + xDirection;
            int newY = yv + yDirection;
            if (dansLesLimites(newX, newY)) {
                if (!cases[newX][newY].isOccupee()) {
                    cases[xv][yv].quitter();
                    placer(v, newX, newY);
                    updateVoisinage(getQuickVoisinage(newX, newY, 1), 3);
                }
            }
        }
    }

     /**
     * Renvoit l'efficacité, c'est-à-dire le nombre de cases, divisé par la
     * somme du nombre de fois qu'un Véhicule est passé sur une Case.
     *
     * @return l'efficacité en %
     */
    private Double calculEfficacite() {
        int somme = 0;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                int nb = cases[i][j].getNbOccupee();
                if (nb <= 1) {
                    nb = 1;
                }
                somme = somme + nb;
            }
        }
        Double efficacite = (X * Y * 100.) / somme;
        return efficacite;
    }

    public HashMap<Integer, Vehicule> getVehicules() {
        return vehicules;
    }

    /**
     * Ajoute une classe implémentant l'interface {@link CarteListener} pour la
     * mettre à l'écoute des changements de la Carte
     *
     * @param l une classe implémentant l'interface {@link CarteListener}
     */
    public void addListener(CarteListener l) {
        listeners.add(CarteListener.class, l);
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    /**
     * Prévient les classes à l'écoute d'un changement dans l'ensemble des
     * {@link Case} de la carte
     */
    private void updateAll() {
        for (final CarteListener listen : listeners.getListeners(CarteListener.class)) {
            listen.update(cases, X, Y);
        }
    }

    /**
     * Prévient les classes à l'écoute d'un changement dans la matrice de
     * {@link Case} voisines, de dimension taille * taille
     *
     * @param voisines les cases dont l'état a changé
     * @param taille la dimension de la matrice
     */
    private void updateVoisinage(Case[][] voisines, int taille) {
        for (final CarteListener listen : listeners.getListeners(CarteListener.class)) {
            listen.update(voisines, taille, taille);
        }
    }

}
