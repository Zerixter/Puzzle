package net.xaviersala.puzzle;

import acm.graphics.GImage;
import acm.graphics.GPoint;

/**
 * Implementa una peça del puzzle.
 * @author xavier
 *
 */
public class Pessa  {

    /**
     * Per identificar les peces.
     */
    private static int newid = 0;
    /**
     * Identificador de peça (no el faig servir).
     */
    private int id;

    /**
     * Imatge que conté la peça.
     */
    private GImage imatge = null;

    /**
     *  Constructor de la peça.
     * @param img Imatge a posar
     * @param x posició x
     * @param y posició y
     */
    public Pessa(final GImage img,  final int x, final int y) {
        id = newid;
        newid++;
        if (img != null) {
            imatge = img;
            imatge.setLocation(x,  y);
        }
    }

    /**
     * @return retorna l'ID.
     */
    public final int getID() {
        return id;
    }

    /**
     * Amaga la imatge. Ho faig servir per la peça buida.
     */
    public final void buida() {
        imatge.setVisible(false);
    }

    /**
     * Defineix quin és el listener que capturarà els clics del ratolí.
     * @param t objecte que rebrà els clics
     */
    public final void afegirListener(final Tauler t) {
        if (t != null)  {
            imatge.addMouseListener(t);
        }
    }

    /**
     * Canvia la posició de la imatge.
     * @param lloc on volem posar la imatge
     */
    public final void setPosicio(final GPoint lloc) {
        imatge.setLocation(lloc);
    }

    /**
     * @return retorna la posició de la imatge
     */
    public final GPoint getPosicio() {
        return imatge.getLocation();
    }

    /**
     * @return retorna la imatge de la peça
     */
    public final GImage getImatge() {
        return imatge;
    }

    /**
     * Defineix una imatge nova per la peça.
     * @param g Imatge
     */
    public final void setImatge(final GImage g) {
        imatge = g;
    }

    /**
     * Comprova si la imatge té dins la posició x,y.
     * @param x posició x
     * @param y posició y
     * @return Si hem clicat a dins
     */
    public final boolean puntADins(final int x, final int y) {
        return imatge.contains(new GPoint(x, y));
    }

}
