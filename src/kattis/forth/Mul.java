package kattis.forth;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class Mul {
    double[] cos;
    double[] sin;
    int c = (int) Math.pow(2, 18);

    public Mul() {
        cos = new double[c];
        sin = new double[c];
        for (int i = 0; i < c; i++) {
            cos[i] = Math.cos(2 * Math.PI * i / c);
            sin[i] = Math.sin(2 * Math.PI * i / c);
        }
    }

    int rev (int num, int lg_n) {
        int res = 0;
        for (int i=0; i<lg_n; ++i)
            if ((num & (1<<i))!=0)
                res |= 1<<(lg_n-1-i);
        return res;
    }

    void fft (double[] re, double[] im, boolean invert) {
        int n = re.length;
        int lg_n = 0;
        while ((1 << lg_n) < n)  ++lg_n;

        for (int i=0; i<n; ++i)
            if (i < rev(i,lg_n)) {
                swap(re, i, rev(i, lg_n));
                swap(im, i, rev(i, lg_n));
            }

        for (int len=2; len<=n; len<<=1) {
            int angN=len*(c/n);

            double wlenRe = (invert ? -1 : 1)*cos(angN);
            double wlenIm = (invert ? -1 : 1)*sin(angN);
            for (int i=0; i<n; i+=len) {
                double wRe = 1;
                double wIm = 0;
                for (int j=0; j<len/2; ++j) {
                    double uRe=re[i+j];
                    double uIm=im[i+j];
                    double vRe=re[i+j+len/2]*wRe-im[i+j+len/2]*wIm;
                    double vIm=re[i+j+len/2]*wIm+im[i+j+len/2]*wRe;
                    re[i+j]=uRe+vRe;
                    im[i+j]=uIm+vIm;
                    re[i+j+len/2]=uRe-vRe;
                    im[i+j+len/2]=uIm-vIm;
                    wRe=wRe*wlenRe-wIm*wlenIm;
                    wIm=wIm*wlenRe+wRe*wlenIm;
                }
            }
        }
        if (invert)
            for (int i=0; i<n; ++i){
                re[i]=re[i]/n;
                im[i]=im[i]/n;
            }
    }

    void swap(double[] a, int i, int j) {
        double c = a[i];
        a[i]=a[j];
        a[j]=c;
    }

    int[] multiply (int[] a, int[] b) {
        int n = 1;
        while (n < Math.max(a.length, b.length))  n <<= 1;
        n <<= 1;
        double[] reA=new double[n];
        double[] imA=new double[n];
        double[] reB=new double[n];
        double[] imB=new double[n];

        for (int i = 0; i < a.length; i++) {
            reA[i]=a[i];
        }

        for (int i = 0; i < b.length; i++) {
            reB[i]=b[i];
        }

        fft(reA, imA, false);
        fft(reB, imB, false);
        for (int i=0; i<n; ++i) {
            reA[i] = reA[i]*reB[i]-imA[i]*imB[i];
            imB[i] = reA[i]*imB[i]+imA[i]*reB[i];
        }
        fft(reA, imA, true);

        int[] res= new int[n];
        for (int i=0; i<n; ++i)
            res[i] = (int) (reA[i] + 0.5);
        int carry = 0;
        for (int i=n-1; i>=0; --i) {
            res[i] += carry;
            carry = res[i] / 10;
            res[i] %= 10;
            if(i==0&&carry!=0) {
                System.arraycopy(res, 0, res, 1, res.length-1);
                res[0]=carry;
            }
        }
        return res;
    }
}
