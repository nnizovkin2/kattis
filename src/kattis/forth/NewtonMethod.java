package kattis.forth;

public class NewtonMethod {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double prev = 0;
        for (int i = 1; i < 50000000; i++) {
//            prev = log10(i, prev);
            Math.log10(i);
//            System.out.println(Exp.exp(ln10*10));
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    static double log10 =2.3025850929940456;
    static double log10(int p, double prevValue) {
        double res=prevValue;
        double d=1;
        int i = 0;
        while((d>Exp.delta||d<-Exp.delta)&&i<10) {
//            double e=Exp.exp(log10*res);
            double e=Math.pow(10, res);
            double div=log10*e;
            double f=e-p;
            d=f/div;
            res-=d;
            i++;
        }

        return res;
    }
}
