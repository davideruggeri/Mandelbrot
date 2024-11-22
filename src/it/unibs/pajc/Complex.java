package it.unibs.pajc;

public class Complex {
    double re;
    double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex sum(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex square() {
        return new Complex(re*re - im*im, 2*re*im);
    }

    public double module2(){
        return re*re + im*im;
    }


}

