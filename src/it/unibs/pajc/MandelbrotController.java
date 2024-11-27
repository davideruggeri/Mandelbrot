package it.unibs.pajc;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotController {
    private MandelbrotModel model;

    public MandelbrotController(MandelbrotModel model) {
        this.model = model;
    }

    private ExecutorService exec = Executors.newFixedThreadPool(6);
    private ArrayList<Future<?>> pendingTasks = new ArrayList<>();

    public void update(Rectangle2D.Double viewport, int resolution) {
        for (Future<?> f : pendingTasks) {
            f.cancel(true);
        }

        int[] resolutions = new int[] {150, 250, 500, 1000, 1500, resolution};

        for (int res : resolutions) {
            pendingTasks.add(exec.submit(() ->
                model.eval(
                        new Complex(viewport.getMinX(), viewport.getMinY()),
                        new Complex(viewport.getMaxX(), viewport.getMaxY()),
                        res)));
            if (res >= resolution)
                break;
        }
    }

}
