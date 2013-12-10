package net.xaviersala.puzzle;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

/**
 * Programa que simula el comportament d'un tauler.
 * @author xavier
 *
 */
public class Principal extends GraphicsProgram {

    /**
     * Mida pantalla amplada.
     */
    private static final int MIDAPANTALLAW = 500;
    /**
     * Mida pantalla altura.
     */
    private static final int MIDAPANTALLAH = 500;
    /**
     * Pausa.
     */
    private static final int ESPERA = 3000;
    /**
     * Peces horitzontals.
     */
    private static final int PECESHORITZONTALS = 8;
    /**
     * Peces verticals.
     */
    private static final int PECESVERTICALS = 8;
    /**
     * Peces que s'intercanvien.
     */
    private static final int INTERCANVIS = 30;
    /**
     * espai pel marcador.
     */
    private static final int ESPAIMARCADOR = 100;
    /**
     * ID del applet.
     */
    private static final long serialVersionUID = 8897575366933937291L;

    /**
     * Executa el programa.
     */
    public final void run() {
        GImage tot = new GImage("oolong.jpg");
        setSize((int) tot.getWidth(), (int) tot.getHeight() + ESPAIMARCADOR);
        Tauler t = new Tauler(tot);
        t.partirImatge(PECESHORITZONTALS, PECESVERTICALS);
        t.pintarTauler(this, true);
        pause(ESPERA);
        t.barreja(INTERCANVIS);
    }

    /**
     * Inicia el tauler.
     */
    public final void init() {
        setSize(MIDAPANTALLAW, MIDAPANTALLAH);
    }
}
