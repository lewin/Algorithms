package Algebra;

public class FastFourierTransform {
    private Complex [] fft(Complex [] x) {
        int N = x.length;
        
        if (N == 1) return new Complex [] {x[0]};
        Complex [] arr = new Complex [N >> 1];
        for (int i = 0; i < N / 2; i++)
            arr [i] = x [2 * i];
        Complex [] even = fft (arr);
        
        for (int i = 0; i < N / 2; i++)
            arr [i] = x [2 * i + 1];
        Complex [] odd = fft(arr);
        
        Complex [] ret = new Complex [N];
        for (int i = 0; i < N / 2; i++) {
            double ang = -2 * i * Math.PI / N;
            Complex ai = new Complex(Math.cos(ang), Math.sin(ang)).multiply(odd[i]);
            ret[i] = even[i].add(ai);
            ret[i + N / 2] = even[i].subtract(ai);
        }
        
        return ret;
    }
    
    public Complex [] ifft (Complex [] x) {
        int N = x.length;
        Complex [] ret = new Complex [N];
        for (int i = 0; i < N; i++)
            ret [i] = x[i].conjugate();
        ret = fft(ret);
        for (int i = 0; i < N; i++)
            ret [i] = ret[i].conjugate().multiply(1. / N);
        return ret;
    }
    
    // performs circular convolution
    public Complex[] circonv (Complex [] x, Complex [] y) {
        int N = x.length;
        Complex [] a = fft(x), b = fft(y);
        for (int i = 0; i < N; i++)
            a[i] = a[i].multiply(b[i]);
        return ifft(a);
    }
    
    // performs linear convolution of x,y
    public Complex[] conv (Complex [] x, Complex [] y) {
        int N = x.length;
        Complex [] a = new Complex [2 * N],
                   b = new Complex [2 * N];
        for (int i = 0; i < N; i++) {
            a[i] = x[i];
            a[i + N] = Complex.ZERO;
            
            b[i] = y[i];
            b[i + N] = Complex.ZERO;
        }
        return circonv (a, b);
    }
}
