package it.unibs.pajc;

import javax.swing.event.ChangeEvent;

public class MandelbrotModel extends BaseModel {
    private double[][] data;

    public double [][] getData(){
        return data.clone();
    }

    public void eval(Complex min, Complex max, int res){
        double[][] data = new double[res][res];
        double dre = Math.abs(min.re - max.re) / res;
        double dim = Math.abs(min.im - max.im) / res;

        for(int i=0; i<res; i++){
            for(int j=0; j<res; j++){
                if(Thread.interrupted()) return;

                Complex c = new Complex(min.re + j*dre, min.im + i*dim);
                data[i][j] = fMandelBrot(c);
            }
        }
        synchronized (this) {
            if (!Thread.interrupted()) {
                this.data = data;
                fireValueChanged(new ChangeEvent(this));
            }
        }
    }

    private static double fMandelBrot(Complex c){
        int maxi = 100;
        Complex z = new Complex(c.re, c.im);
        for(int i=0; i<maxi; i++){
            z = z.square().sum(c);
            if(z.module2() > 1e5){
                return (maxi - i)/ (double) maxi;
            }
        }
        return 0;
    }
}