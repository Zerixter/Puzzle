package net.xaviersala.puzzle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GPoint;

/**
 * Implementa el tauler que conté les peces.
 *
 * @author xavier
 */
public class Tauler implements MouseListener  {

    /**
     * Llista de les peces del tauler.
     */
    private ArrayList<Pessa> pesses;

    /**
     * Defineix quina és la peça buida.
     */
    private int buida;
    /**
     * Imatge del tauler.
     */
    private GImage imatgePuzzle;
    /**
     * Mida horitzontal de la peça.
     */
    private int midaPessaHoritzontal;
    /**
     * Mida vertical de la peça.
     */
    private int midaPessaVertical;

    /**
     * Crea el tauler amb una imatge sense partir.
     * @param imatge Imatge del tauler
     */
    public Tauler(final GImage imatge)  {
        pesses = new ArrayList<Pessa>();
        imatgePuzzle = imatge;
        midaPessaHoritzontal = (int) imatgePuzzle.getWidth();
        midaPessaVertical = (int) imatgePuzzle.getHeight();
    }

    /**
     * Parteix la imatge en el número de peces que es posa en els paràmetres.
     * @param horitzontal quantitat de peces horitzontals
     * @param vertical quantitat de peces verticals
     */
    public final void partirImatge(final int horitzontal, final int vertical) {
        int j = 0;
        int i = 0;
        midaPessaHoritzontal = (int) (imatgePuzzle.getWidth() / horitzontal);
        midaPessaVertical = (int) (imatgePuzzle.getHeight() / vertical);

        Pessa temp = null;

        for (int fila = 0; fila < horitzontal; fila += 1) {
            i = 0;
            for (int columna = 0; columna < vertical; columna += 1) {
                int posFila = fila * midaPessaHoritzontal;
                int posColumna = columna * midaPessaVertical;

                temp = new Pessa(Ajuda.tallaImatge(imatgePuzzle,
                        posFila, posColumna,
                        midaPessaHoritzontal, midaPessaVertical),
                        posFila + j, posColumna + i);
                i++;
                temp.afegirListener(this);
                pesses.add(temp);
            }
            j++;
        }
        // Buida la darrera peça
        temp.buida();

        // temp.setImatge(null);
       buida = pesses.size() - 1;
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int i = 0;
        for (Pessa pessa : pesses) {
            if (pessa.puntADins(x, y)) {
                mou(i);
                break;
            }
            i++;
        }


    }

    /**
     * Moure la peça que ens demanen només si es pot moure perquè està
     * de costat amb la buida.
     * @param i peça que es vol moure
     */
    private void mou(final int i) {


        // Mira si es pot moure...
        GPoint pessaBuida = pesses.get(buida).getPosicio();
        GPoint pessaPlena = pesses.get(i).getPosicio();

        if (adjacents(pessaBuida, pessaPlena)) {
            pesses.get(i).setPosicio(pessaBuida);
            pesses.get(buida).setPosicio(pessaPlena);
        }

    }

    /**
     * Intercanvia les peces especificades.
     * @param i posició
     * @param j posició
     */
    private void intercanvia(final int i, final int j) {
        GPoint pos = pesses.get(i).getPosicio();

        pesses.get(i).setPosicio(pesses.get(j).getPosicio());
        pesses.get(j).setPosicio(pos);
    }

    /**
     * Intercanvia les peces entre elles per aconseguir un puzzle per fer.
     * @param vegades El número d'intercanvis
     */
    public final void barreja(final int vegades) {
        int max = pesses.size() - 1;
        if (max > 0) {
            Random r = new Random();
            for (int i = 0; i < vegades; i++) {
                intercanvia(r.nextInt(max), r.nextInt(max));
            }
        }
    }

    /**
     * Comprova si dues peces són adjacents.
     * @param pessaBuida Punt d'una peça
     * @param pessaPlena Punt de l'altra peça
     * @return retorna si són adjacents o no
     */
    private boolean adjacents(final GPoint pessaBuida,
            final GPoint pessaPlena) {

        if (pessaBuida.getX() == pessaPlena.getX()) {
            if (Math.abs(pessaBuida.getY() - pessaPlena.getY())
                    <= midaPessaVertical + 1) {
                return true;
            }
        } else if (pessaBuida.getY() == pessaPlena.getY()) {
            if (Math.abs(pessaBuida.getX() - pessaPlena.getX())
                    <= midaPessaHoritzontal + 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    /**
     * Pintar les peces en el objecte que s'ha passat.
     * @param pantalla Lloc on pintar.
     */
    public final void pintarPesses(final Principal pantalla) {
        if (pesses.size() > 0) {
            for (Pessa pessa : pesses) {
                if (pessa.getImatge() != null) {
                    pantalla.add(pessa.getImatge());
                }
            }
        }

    }

}
