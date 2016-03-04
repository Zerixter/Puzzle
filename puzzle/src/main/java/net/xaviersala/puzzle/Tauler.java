package net.xaviersala.puzzle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GPoint;

/**
 * Implementa el tauler que conté les peces.
 *
 * @author xavier
 */
public class Tauler implements MouseListener  {

    /**
     * Posicionar el marcador.
     */
    private static final int POSICIOMARCADOR  = 50;
    /**
     * Marge per la miniicona.
     */
    private static final double MARGEPETIT = 5;
    /**
     * Escala del quadre petit.
     */
    private static final double ESCALAIMATGEPETITA = 0.25;
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
     * Imatge en miniatura.
     */
    private GImage miniImatge;
    /**
     * Mida horitzontal de la peça.
     */
    private int midaPessaHoritzontal;
    /**
     * Mida vertical de la peça.
     */
    private int midaPessaVertical;

    /**
     * Clics del ratolí.
     */
    private int clics;
    /**
     * Ratoli label.
     */
    private GLabel marcador;
    /**
     * Crea el tauler amb una imatge sense partir.
     * @param imatge Imatge del tauler
     */
    public Tauler(final GImage imatge)  {
        pesses = new ArrayList<Pessa>();
        imatgePuzzle = imatge;
        midaPessaHoritzontal = (int) imatgePuzzle.getWidth();
        midaPessaVertical = (int) imatgePuzzle.getHeight();
        clics = 0;
        marcador = new GLabel("Número de clics",
                imatgePuzzle.getX() + POSICIOMARCADOR,
                imatgePuzzle.getHeight() + POSICIOMARCADOR);
        miniImatge = new GImage(imatgePuzzle.getImage());
        miniImatge.scale(ESCALAIMATGEPETITA);
        miniImatge.setLocation(imatgePuzzle.getWidth() - miniImatge.getWidth(),
                imatgePuzzle.getHeight());
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

       buida = pesses.size() - 1;
       clics = 0;

       miniImatge.setLocation(
               imatgePuzzle.getWidth() - (miniImatge.getWidth() + MARGEPETIT),
               temp.getPosicio().getY() + midaPessaVertical + MARGEPETIT);
    }



    /**
     * Mostra els ID de cada peça per poder debugar.
     * @return ordre dels id en el tauler
     */
    private String debugPeces() {
        String retorna = "";
        for (Pessa p: pesses) {
            retorna = p.getID() + " ";
        }
        return retorna;
    }

    /**
     * Moure la peça que ens demanen només si es pot moure perquè està
     * de costat amb la buida.
     * @param i peça que es vol moure
     */
    private void mou(final int i) {

        if (partidaAcabada()) {
            return;
        }
        // Mira si es pot moure...
        GPoint pessaBuida = pesses.get(buida).getPosicio();
        GPoint pessaPlena = pesses.get(i).getPosicio();

        if (adjacents(pessaBuida, pessaPlena)) {
            // He de canviar les posicions i l'ID
            intercanvia(buida, i);
            clics++;
            pintaMarcador();

            if (partidaAcabada()) {
                pesses.get(buida).emplena();
            }
        }

    }

    /**
     * Intercanvia les peces especificades.
     * @param i posició
     * @param j posició
     * @return peces canviades
     */
    private String intercanvia(final int i, final int j) {

        String debuga = "Canvia " + pesses.get(i).getID() + " <-> "
                                + pesses.get(i).getID();

        GPoint pos = pesses.get(i).getPosicio();
        int idDeI = pesses.get(i).getID();

        pesses.get(i).setPosicio(pesses.get(j).getPosicio());
        pesses.get(i).setID(pesses.get(j).getID());

        pesses.get(j).setPosicio(pos);
        pesses.get(j).setID(idDeI);

        return debuga;
    }

    /**
     * Intercanvia les peces entre elles per aconseguir un puzzle per fer.
     * @param vegades El número d'intercanvis
     */
    public final void barreja(final int vegades) {
        debugPeces();

        int max = pesses.size() - 1;
        if (max > 0) {
            Random r = new Random();
            for (int i = 0; i < vegades; i++) {
                intercanvia(r.nextInt(max), r.nextInt(max));
            }
        }
        clics = 0;
        pintaMarcador();
        debugPeces();
    }

    /**
     * Pinta el marcador.
     */
    private void pintaMarcador() {
        marcador.setLabel("Clics: " + clics);
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


    /**
     * Pintar totes les peces en el objecte que hi ha en el tauler i decidir si
     * s'ha de pintar el marcador o no.
     * @param pantalla Lloc on pintar.
     * @param ambMarcador defineix si volem pintar el marcador o no
     */
    public final void pintarTauler(final Principal pantalla,
            final boolean ambMarcador) {
        if (!pesses.isEmpty()) {
            for (Pessa pessa : pesses) {
                if (pessa.getImatge() != null) {
                    pantalla.add(pessa.getImatge());
                }
            }

            if (ambMarcador) {
                pantalla.add(marcador);
                pantalla.add(miniImatge);
            }
        }

    }

    /**
     * @return S'ha acabat la partida?
     */
    public final boolean partidaAcabada() {
        int i = 0;
        for (Pessa p: pesses) {
            if (i != p.getID()) {
                return false;
            }
            i++;
        }
        return true;
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {


    }

    public void mouseExited(MouseEvent e) {


    }

    public void mouseClicked(MouseEvent e) {
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

      debugPeces();

    }
}
