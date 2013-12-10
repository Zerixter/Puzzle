package net.xaviersala.puzzle;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import acm.graphics.GImage;

/**
 * Classe estàtica on hi ha funcions 'd'ajuda'.
 * @author xavier
 *
 */
public final class Ajuda {

    /**
     * Constructor inútil.
     */
    private Ajuda() {
    }

        /**
         * Funció que rep com a paràmetre un GImage que té la imatge sencera i
         * retorna una subimatge definida per les coordenades x, y, w i h.
         * @param src Imatge que es vol tallar
         * @param x coordenada x del tall
         * @param y coordenada y del tall
         * @param w amplada del tall
         * @param h altura del tall
         * @return Imatge escapçada
         *
         *  .--------------------------------.
         *  |   x,y                         |
         *  |     *--------------*           |
         *  |     |               |           |
         *  |     *--------------*           |
         *  |                                 |
         *  ---------------------------------.
         */
        public static GImage tallaImatge(final GImage src,
                final int x, final int y, final int w, final int h) {
            Image imatge = src.getImage();

            imatge = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(imatge.getSource(),
                    new CropImageFilter(x, y, w, h)));
            return new GImage(imatge);
        }



}
