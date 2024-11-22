package it.unibs.pajc;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MandelbrotController {
    private MandelbrotModel model;

    public MandelbrotController(MandelbrotModel model) {
        this.model = model;
    }

    private ExecutorService exec = Executors.newFixedThreadPool(6);

    public void update(Rectangle2D.Double viewport, int resolution) {
        int[] resolutions = new int[] {150, 250, 500, 1000, 1500, resolution};

        for (int res : resolutions) {
            exec.submit(() ->
                model.eval(
                        new Complex(viewport.getMinX(), viewport.getMinY()),
                        new Complex(viewport.getMaxX(), viewport.getMaxY()),
                        res));
            if (res >= resolution)
                break;
        }
    }

}
