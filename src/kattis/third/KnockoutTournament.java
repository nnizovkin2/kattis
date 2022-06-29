package kattis.third;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//https://open.kattis.com/problems/knockout
public class KnockoutTournament {
    public static void main(String[] args) {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 9);
        int n = parser.getInt();
        int[] a = new int[n];
        while(n-->0) {
            a[n]=parser.getInt();
        }

        System.out.println(count(a));
    }

    static double count(int[] a) {
        if(a.length==2) {
            return (double)a[1]/(a[1]+a[0]);
        }

        int f = a[a.length-1];
        a[a.length-1]=Integer.MIN_VALUE;
        Arrays.sort(a);
        for (int i = 0; i < a.length/2; i++) {
            int t = a[i];
            a[i]=a[a.length-i-1];
            a[a.length-i-1]=t;
        }
        a[a.length-1]=f;
        double[] p = new double[a.length];
        Arrays.fill(p, 1);
        ArrayList<ArrayList<Integer>> l = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
        }
        int msb = msb(a.length);
        int i;

        for (i = 0; i < (p.length-(1<<msb)); i++) {
            ArrayList<Integer> tl = new ArrayList<>();
            tl.add(i*2);
            tl.add(i*2+1);
            p[i*2]=(double)a[i*2]/(a[i*2]+a[i*2+1]);
            p[i*2+1]=(double)a[i*2+1]/(a[i*2]+a[i*2+1]);
            l.add(tl);
        }
        i=i*2;

        for(;i<p.length-1;i++) {
            ArrayList<Integer> tl=new ArrayList<>();
            tl.add(i);
            l.add(tl);
        }

        ArrayList<ArrayList<Integer>> t = new ArrayList<>();
        while (!l.isEmpty()) {
            var pcopy = Arrays.copyOf(p, p.length);
            for (i = 0; i < l.size() / 2; i++) {
                ArrayList<Integer> r = new ArrayList<>();
                for(Integer ind1: l.get(i*2)) {
                    double prob = 0;
                    for(Integer ind2: l.get(i*2+1)) {
                        prob+=pcopy[ind2]*a[ind1]/(a[ind1]+a[ind2]);
                    }
                    p[ind1]*=prob;
                    r.add(ind1);
                }
                for(Integer ind1: l.get(i*2+1)) {
                    double prob = 0;
                    for(Integer ind2: l.get(i*2)) {
                        prob+=pcopy[ind2]*a[ind1]/(a[ind1]+a[ind2]);
                    }
                    p[ind1]*=prob;
                    r.add(ind1);
                }
                t.add(r);
            }
            double prob = 0;
            for(Integer ind: l.get(i*2)) {
                prob+=p[ind]*a[a.length-1]/(a[a.length-1]+a[ind]);
            }
            p[p.length-1]*=prob;
            l.clear();
            var t2 = l;
            l = t;
            t = t2;
        }

        return p[p.length-1];
    }

    public static int msb(int i) {
        int res = -1;

        while(i!=0) {
            i>>>=1;
            res++;
        }

        return res;
    }


    private static class OnlineStreamParser {
        private InputStream s;
        private char[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new char[bs];
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
            char ch;
            try {
                int i = 0;
                while ((ch = (char) s.read()) != ' ' && ch != '\n') {
                    b[i++] = ch;
                }
                return new String(Arrays.copyOf(b, i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
