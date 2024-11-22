package it.unibs.pajc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

public class MandelbrotApp {

    private JFrame frame;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MandelbrotApp window = new MandelbrotApp();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();}}});
    }

    private MandelbrotModel model;
    private MandelbrotController cntrl;
    PnlMandelbrot pnlMandelbrot;

    public MandelbrotApp() {
        model = new MandelbrotModel();
        model.addChangeListener(this::updateModel);
        cntrl = new MandelbrotController(model);

        initialize();
    }

    private void updateModel(ChangeEvent e) {
        if (pnlMandelbrot == null) {
            return;
        }
        double data[][] = model.getData();
        pnlMandelbrot.setData(data);

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pnlMandelbrot = new PnlMandelbrot(cntrl);
        frame.getContentPane().add(pnlMandelbrot);
    }

}
