package it.unibs.pajc;

import java.awt.geom.Rectangle2D;

public class MandelbrotController {
    private MandelbrotModel model;

    public MandelbrotController(MandelbrotModel model) {
        this.model = model;
    }

    public void update(Rectangle2D.Double viewport, int resolution) {
        model.eval(
                new Complex(viewport.getMinX(), viewport.getMinY()),
                new Complex(viewport.getMaxX(), viewport.getMaxY()),
                resolution);


    }

}
