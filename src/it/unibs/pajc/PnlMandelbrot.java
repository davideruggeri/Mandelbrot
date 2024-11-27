package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PnlMandelbrot extends JPanel implements MouseListener, MouseMotionListener{

    protected Rectangle2D.Double viewport = new Rectangle2D.Double(-2., -1., 3, 2);
    private Image mandelbrotImage = null;

    public void setData(double data[][]) {
        mandelbrotImage = createImageFromData(data);
        repaint();
    }

    public Image createImageFromData(double[][] data) {
        if (data == null) {
            return null;
        }

        int xsize = data.length;
        int ysize = data[0].length;

        Image img = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();

        for (int i = 0; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                double v = data [i][j];
                int c = (int)(v*255);
                g2.setColor(new Color(c, c, c));
                g2.fillRect(j, i,1,1);
            }
        }

        return img;
    }

    public MandelbrotController cntrl;

    public PnlMandelbrot(MandelbrotController cntrl) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.cntrl = cntrl;
        this.cntrl.update(viewport, 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        if (mandelbrotImage == null)
            return;
        g2.drawImage(mandelbrotImage, 0, 0, w, h, Color.WHITE, null);
    }
    /*--------------------------------------------------------------------------------------
     * GESTIONE VIEWPORT
     *-------------------------------------------------------------------------------------*/

    public void setViewport(Rectangle2D.Double newViewport) {
        this.viewport = newViewport;
        cntrl.update(newViewport, 1500);

    }

    public void zoom(Point2D.Double p, double zoom) {
        double fineWidth = viewport.getWidth() / zoom;
        double fineHeight = viewport.getHeight() / zoom;

        double rx = (p.x - viewport.getMinX()) / viewport.getWidth();
        double ry = (p.y - viewport.getMinY()) / viewport.getHeight();

        Rectangle2D.Double newViewport = new Rectangle2D.Double(
                p.x - fineWidth * rx,
                p.y - fineHeight * ry,
                fineWidth,
                fineHeight);
        setViewport(newViewport);
    }

    private Point panStartMousePosition = null;
    private Rectangle2D.Double panStartViewport = null;

    @Override
    public void mouseDragged(MouseEvent e) {
        double dx = (e.getX() - panStartMousePosition.x) * panStartViewport.getWidth() / getWidth();
        double dy = (e.getY() - panStartMousePosition.y) * panStartViewport.getHeight() / getHeight();

        Rectangle2D.Double newViewport = new Rectangle2D.Double(panStartViewport.x - dx, panStartViewport.y - dy, panStartViewport.width, panStartViewport.height);

        setViewport(newViewport);
    }

    public Point2D.Double getViewportPoint(Point pixel) {
        double x = viewport.getWidth() / getWidth() * pixel.x + viewport.getMinX();
        double y = viewport.getHeight() / getHeight() * pixel.y + viewport.getMinY();

        return new Point2D.Double(x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D.Double p_world = getViewportPoint(e.getPoint());
        if (e.isShiftDown()) {
            zoom(p_world, 1./1.1);
        } else
            zoom(p_world, 1.1);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (panStartMousePosition == null) {
            panStartMousePosition = e.getPoint();
            panStartViewport = viewport;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        panStartMousePosition = null;
        panStartViewport = null;
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
