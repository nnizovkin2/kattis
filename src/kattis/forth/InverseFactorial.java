package kattis.forth;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.Math.log;

//https://open.kattis.com/problems/inversefactorial
//take from here
// https://github.com/dakoval/Kattis-Solutions/blob/master/inversefactorial.java
public class InverseFactorial {
    private static final double LOG_TWO = log(2.0);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
////        OnlineStreamParser parser = new OnlineStreamParser(System.in, 205024);
//        String s = parser.getToken();
//        int x = s.length();
        double y = 0;
//        double y2 = 0;
        double yPrev = 0.3;
        long f = 1;
        //456574
        double min=0.5;
        int k=0;
        BigInteger bi = new BigInteger("1");
        System.out.println(Math.pow(10, .398616799343));
        Field field = Arrays.stream(BigInteger.class.getDeclaredFields()).filter(fieldF->fieldF.getName().equals("mag")).findAny().get();
        field.setAccessible(true);
        for (int i=2;i<=206000;i++) {
            yPrev=NewtonMethod.log10(i, yPrev);
            y += yPrev;
            bi=bi.multiply(BigInteger.valueOf(i));
            int l=bi.toString().length();
            if(i%1000==0) {
                System.out.println(i);
            }
            if(l!=(int)y+1) {
                System.out.println((int)(y+1) + " " + l);
            }
//            if((int)y+1-y<min&&i>20000) {
//                min = (int)y+1-y;
//                k=i;
//            }


//            if(i<=9) {
//                f*=i;
//                if(Long.toString(f).equals(s)) {
//                    System.out.println(i);
//                    break;
//                }
//            } else if (x==(int)y+1) {
//                System.out.println(i);
//                break;
//            }
        }
    }

    public static int getNumOfD(int n) {
        int res=0;
        while(n!=0) {
            n/=10;
            res++;
        }

        return res;
    }

    public static int msb(int i) {
        int res = -1;

        while(i!=0) {
            i>>>=1;
            res++;
        }

        return res;
    }

    private static class NewtonMethod {
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

    static public class Exp {
        static double delta = 0.00000000000001;
        static double E = 2.718281828459045;

        public static void main(String[] args) {
            for (int i = 0; i < 11; i++) {
                System.out.println(exp(-i+0.5));
                System.out.println(Math.exp(-i+0.5));
            }
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

    private static class OnlineStreamParser {
        private InputStream s;
        private byte[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new byte[bs];
        }

        public int getInt() {
            char ch;
            try {
                ch = (char) s.read();
                boolean isPositive = true;
                if (ch == '-') {
                    isPositive = false;
                    ch = (char) s.read();
                }
                int i = 0;
                while ('0' <= ch && ch <= '9') {
                    i *= 10;
                    i += ch - '0';
                    ch = (char) s.read();
                }
                return isPositive ? i : -i;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getToken() {
            int ch;
            try {
                int i = s.read(b);
//                while ((ch = s.read(b)) != ' ' && ch != '\n' && ch!=-1) {
//                    b[i++] = (char)ch;
//                }
                return new String(Arrays.copyOf(b, i - 1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
