package Algebra;

public final class Complex {
    public static final Complex ZERO = new Complex (0, 0);
    public double real, imag;
    
    public Complex (double real, double image) {
        this.real = real;
        this.imag = imag;
    }
    
    public Complex add (Complex other) {
        return new Complex (this.real + other.real, this.imag + other.imag); 
    }
    
    public Complex negate (Complex other) {
        return new Complex (-this.real, -this.imag);
    }
    
    public Complex subtract (Complex other) {
        return new Complex (this.real - other.real, this.imag - other.imag);
    }
    
    public Complex inverse () {
        double div = len();
        return new Complex (this.real / div, -this.imag / div);
    }
    
    public double len() {
        return real * real + imag * imag;
    }
    
    public Complex multiply (Complex other) {
        return new Complex (this.real * other.real - this.imag * other.imag, this.real * other.imag + other.real * this.imag);
    }
    
    public Complex multiply (double other) {
        return new Complex (this.real * other, this.imag * other);
    }
    
    public Complex divide (Complex other) {
        return this.multiply(other.inverse());
    }
    
    public Complex conjugate () {
        return new Complex (this.real, -this.imag);
    }
}
