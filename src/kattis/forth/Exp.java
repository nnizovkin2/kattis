package kattis.forth;

import static kattis.third.KnockoutTournament.msb;

public class Exp {
    static double delta = 0.00000000000001;
    static double E = 2.718281828459045;

    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            System.out.println(exp(-i+0.5));
            System.out.println(Math.exp(-i+0.5));
        }
        System.out.println(exp(NewtonMethod.log10));
    }

    static double exp(double x) {
        boolean isPositive = true;
        if(x<0) {
            isPositive=false;
            x=-x;
        }
        double res = 0;
        double d = 1;
        int i = 0;
        long f = 1;
        double mul = 1;
        int bp=(int)x;
        double ap=x-bp;
        while(d>delta&&i<21) {
            d=mul/f;
            res+=d;
            mul*=ap;
            i++;
            f*=i;
        }
        res*=exp(bp);
        if(!isPositive){
            res=1/res;
        }
        return res;
    }

    static double exp(int exp) {
        int msb=msb(exp);
        double res=1;
        while(msb!=-1) {
            res*=res;
            res*=((1<<msb)&exp)!=0?E:1;
            msb--;
        }

        return res;
    }
}
